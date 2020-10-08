package com.akon.skrage.utils.customstatuseffect;

import ch.njol.util.Validate;
import com.akon.skrage.SkRage;
import com.akon.skrage.utils.exceptionsafe.ExceptionSafe;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.potion.PotionEffect;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Function;

public class CSEManager {

	private static final HashMap<String, CSEType> REGISTRY = Maps.newHashMap();

	public static void register(CSEType type) {
		Validate.notNull(type, "CustomStatusEffectType");
		Validate.isFalse(REGISTRY.containsKey(type.getId()), type.getId() + "というIDのCustomStatusEffectTypeは既に登録されています");
		REGISTRY.put(type.getId(), type);
	}

	public static void unregister(String id) {
		Validate.isTrue(REGISTRY.containsKey(id), "指定されたIDのCustomStatusEffectTypeは登録されていません");
		Optional.ofNullable(REGISTRY.remove(id)).ifPresent(type -> Bukkit.getWorlds().stream().flatMap(world -> world.getLivingEntities().stream()).forEach(entity -> remove0(entity, type)));
	}

	private static void checkRegistered(CSEType type) {
		Validate.isTrue(REGISTRY.containsValue(type), type.toString() + "は登録されていません");
	}

	@Nullable
	public static CSEType get(String id) {
		return REGISTRY.get(id);
	}

	public static Collection<CSEType> getAll() {
		return Collections.unmodifiableCollection(REGISTRY.values());
	}

	@Nullable
	private static HashMap<CSEType, CustomStatusEffect> getActiveCSEs0(LivingEntity entity) {
		return (HashMap<CSEType, CustomStatusEffect>)entity.getMetadata("CustomStatusEffects")
			.stream()
			.filter(metadata -> metadata.getOwningPlugin() == SkRage.getInstance())
			.findAny()
			.map(MetadataValue::value)
			.orElse(null);
	}

	public static Map<CSEType, CustomStatusEffect> getActiveCSEs(LivingEntity entity) {
		return Optional.ofNullable(getActiveCSEs0(entity)).map((Function<HashMap<CSEType, CustomStatusEffect>, Map<CSEType, CustomStatusEffect>>)map -> ImmutableMap.<CSEType, CustomStatusEffect>builder().putAll(map).build()).orElse(Collections.emptyMap());
	}

	private static boolean apply0(LivingEntity entity, CustomStatusEffect effect, ApplyCondition condition) {
		if (!entity.isDead() && condition.test(entity, effect)) {
			Optional.ofNullable(Optional.ofNullable(getActiveCSEs0(entity)).orElseGet(() -> {
				HashMap<CSEType, CustomStatusEffect> map = Maps.newHashMap();
				entity.setMetadata("CustomStatusEffects", new FixedMetadataValue(SkRage.getInstance(), map));
				return map;
			}).put(effect.getType(), effect)).ifPresent(cse -> cse.getType().onRemove(cse, entity));
			effect.getType().onApply(effect, entity);
			updatePotionParticle(entity);
			return true;
		}
		return false;
	}

	public static boolean apply(LivingEntity entity, CustomStatusEffect effect, ApplyCondition condition) {
		checkRegistered(effect.getType());
		return apply0(entity, effect, condition);
	}

	private static void remove0(LivingEntity entity, CSEType type) {
		Optional.ofNullable(getActiveCSEs0(entity)).map(map -> map.remove(type)).ifPresent(effect -> {
			effect.getType().onRemove(effect, entity);
			updatePotionParticle(entity);
		});
	}

	public static void remove(LivingEntity entity, CSEType type) {
		checkRegistered(type);
		remove0(entity, type);
	}

	private static void updatePotionParticle(LivingEntity entity) {
		PacketContainer packet = new PacketContainer(PacketType.Play.Server.ENTITY_METADATA);
		packet.getIntegers().write(0, entity.getEntityId());
		WrappedDataWatcher dataWatcher = new WrappedDataWatcher();
		PotionParticle particle = getPotionParticle(entity);
		dataWatcher.setObject(new WrappedDataWatcher.WrappedDataWatcherObject(8, WrappedDataWatcher.Registry.get(Integer.class)), particle.getColor().asRGB());
		dataWatcher.setObject(new WrappedDataWatcher.WrappedDataWatcherObject(9, WrappedDataWatcher.Registry.get(Boolean.class)), particle.isAmbient());
		packet.getWatchableCollectionModifier().write(0, dataWatcher.getWatchableObjects());
		Bukkit.getOnlinePlayers().forEach(ExceptionSafe.<Player, InvocationTargetException>consumer(player -> ProtocolLibrary.getProtocolManager().sendServerPacket(player, packet)).caught(ExceptionSafe.PRINT_STACK_TRACE));
	}

	static PotionParticle getPotionParticle(LivingEntity entity) {
		Collection<PotionEffect> potionEffects = entity.getActivePotionEffects();
		Collection<CustomStatusEffect> effects = CSEManager.getActiveCSEs(entity).values();
		if (potionEffects.isEmpty() && effects.isEmpty()) {
			return PotionParticle.NONE;
		}
		Boolean ambient = null;
		float r = 0;
		float g = 0;
		float b = 0;
		int i = 0;
		for (PotionEffect effect: potionEffects) {
			if (effect.hasParticles()) {
				if (ambient == null) {
					ambient = effect.isAmbient();
				} else {
					ambient = ambient && effect.isAmbient();
				}
				int color = effect.getType().getColor().asRGB();
				int tier = effect.getAmplifier()+1;
				r += (float)(tier*(color >> 16 & 255))/255.0F;
				g += (float)(tier*(color >> 8 & 255))/255.0F;
				b += (float)(tier*(color >> 0 & 255))/255.0F;
				i += tier;
			}
		}
		for (CustomStatusEffect effect: effects) {
			if (effect.hasParticles()) {
				if (ambient == null) {
					ambient = effect.isAmbient();
				} else {
					ambient = ambient && effect.isAmbient();
				}
				int color = effect.getType().getColor().asRGB();
				int tier = effect.getAmplifier()+1;
				r += (float)(tier*(color >> 16 & 255))/255.0F;
				g += (float)(tier*(color >> 8 & 255))/255.0F;
				b += (float)(tier*(color >> 0 & 255))/255.0F;
				i += tier;
			}
		}
		if (i == 0) {
			return PotionParticle.NONE;
		} else {
			r = r/(float)i*255.0F;
			g = g/(float)i*255.0F;
			b = b/(float)i*255.0F;
			return new PotionParticle(Color.fromRGB((int)r, (int)g, (int)b), ambient);
		}
	}

	@AllArgsConstructor
	public enum ApplyCondition implements BiPredicate<LivingEntity, CustomStatusEffect> {

		DEFAULT((entity, effect) -> !getActiveCSEs(entity).containsKey(effect.getType())),
		FORCE((entity, effect) -> true),
		VANILLA((entity, effect) -> {
			CustomStatusEffect cse = getActiveCSEs(entity).get(effect.getType());
			return cse == null || cse.getAmplifier() < effect.getAmplifier() || (cse.getAmplifier() == effect.getAmplifier() && cse.getDuration() < effect.getDuration());
		});

		private final BiPredicate<LivingEntity, CustomStatusEffect> handle;

		@Override
		public boolean test(LivingEntity entity, CustomStatusEffect effect) {
			return this.handle.test(entity, effect);
		}
	}

}
