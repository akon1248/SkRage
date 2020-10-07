package com.akon.skrage.utils.customstatuseffect;

import com.akon.skrage.SkRage;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.PluginDisableEvent;

import java.util.Optional;

public class CSEListener extends PacketAdapter implements Listener {

	public static final CSEListener INSTANCE = new CSEListener();

	private CSEListener() {
		super(SkRage.getInstance(), PacketType.Play.Server.ENTITY_METADATA);
	}

	@Override
	public void onPacketSending(PacketEvent e) {
		if (e.getPacketType() == PacketType.Play.Server.ENTITY_METADATA) {
			Optional.ofNullable(e.getPacket().getEntityModifier(e).read(0)).filter(LivingEntity.class::isInstance).map(LivingEntity.class::cast).ifPresent(entity -> {
				WrappedDataWatcher dataWatcher = new WrappedDataWatcher(e.getPacket().getWatchableCollectionModifier().read(0));
				if (dataWatcher.hasIndex(8) || dataWatcher.hasIndex(9)) {
					PotionParticle particle = CSEManager.getPotionParticle(entity);
					if (dataWatcher.hasIndex(8)) {
						dataWatcher.setObject(new WrappedDataWatcher.WrappedDataWatcherObject(8, WrappedDataWatcher.Registry.get(Integer.class)), particle.getColor().asRGB());
					}
					if (dataWatcher.hasIndex(9)) {
						dataWatcher.setObject(new WrappedDataWatcher.WrappedDataWatcherObject(9, WrappedDataWatcher.Registry.get(Boolean.class)), particle.isAmbient());
					}
				}
				e.getPacket().getWatchableCollectionModifier().write(0, dataWatcher.getWatchableObjects());
			});
		}
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		CSEManager.getActiveCSEs(e.getPlayer()).forEach((type, effect) -> type.onApply(effect, e.getPlayer()));
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		CSEManager.getActiveCSEs(e.getPlayer()).forEach((type, effect) -> type.onRemove(effect, e.getPlayer()));
	}

	@EventHandler
	public void onDeath(EntityDeathEvent e) {
		removeAllCSEs(e.getEntity());
	}

	@EventHandler
	public void onDisable(PluginDisableEvent e) {
		if (e.getPlugin().equals(SkRage.getInstance())) {
			Bukkit.getWorlds().stream().flatMap(world -> world.getLivingEntities().stream()).forEach(CSEListener::removeAllCSEs);
		}
	}

	private static void removeAllCSEs(LivingEntity entity) {
		CSEManager.getActiveCSEs(entity).keySet().forEach(type -> CSEManager.remove(entity, type));
	}

}
