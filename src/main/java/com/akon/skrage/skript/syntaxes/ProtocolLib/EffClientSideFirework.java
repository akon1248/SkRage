package com.akon.skrage.skript.syntaxes.ProtocolLib;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.akon.skrage.utils.exceptionsafe.ExceptionSafe;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import net.minecraft.server.v1_12_R1.EntityFireworks;
import net.minecraft.server.v1_12_R1.PacketPlayOutSpawnEntity;
import org.bukkit.Bukkit;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

@Description({"クライアントサイドな花火のエフェクトを表示させます"})
public class EffClientSideFirework extends Effect {

	static {
		Skript.registerEffect(EffClientSideFirework.class, "show firework[s] [effect] %fireworkeffects% at %location% [for %players%]");
	}

	private Expression<FireworkEffect> effect;
	private Expression<Location> location;
	private Expression<Player> player;

	@Override
	protected void execute(Event e) {
		if (this.effect != null) {
			Optional.ofNullable(this.location).map(expr -> expr.getSingle(e)).ifPresent(loc -> {
				ItemStack item = new ItemStack(Material.FIREWORK);
				FireworkMeta meta = (FireworkMeta)item.getItemMeta();
				meta.addEffects(this.effect.getAll(e));
				item.setItemMeta(meta);
				EntityFireworks fireworks = new EntityFireworks(((CraftWorld)loc.getWorld()).getHandle(), loc.getX(), loc.getY(), loc.getZ(), CraftItemStack.asNMSCopy(item));
				PacketContainer spawnEntity = PacketContainer.fromPacket(new PacketPlayOutSpawnEntity(fireworks, 76));
				PacketContainer metadata = new PacketContainer(PacketType.Play.Server.ENTITY_METADATA);
				metadata.getIntegers().write(0, fireworks.getId());
				metadata.getWatchableCollectionModifier().write(0, WrappedDataWatcher.getEntityWatcher(fireworks.getBukkitEntity()).getWatchableObjects());
				PacketContainer explode = new PacketContainer(PacketType.Play.Server.ENTITY_STATUS);
				explode.getIntegers().write(0, fireworks.getId());
				explode.getBytes().write(0, (byte)17);
				PacketContainer destroy = new PacketContainer(PacketType.Play.Server.ENTITY_DESTROY);
				destroy.getIntegerArrays().write(0, new int[]{fireworks.getId()});
				Collection<? extends Player> players = this.player != null ? Arrays.asList(this.player.getAll(e)) : Bukkit.getOnlinePlayers();
				players.forEach(ExceptionSafe.<Player, InvocationTargetException>consumer(p -> {
					ProtocolLibrary.getProtocolManager().sendServerPacket(p, spawnEntity);
					ProtocolLibrary.getProtocolManager().sendServerPacket(p, metadata);
					ProtocolLibrary.getProtocolManager().sendServerPacket(p, explode);
					ProtocolLibrary.getProtocolManager().sendServerPacket(p, destroy);
				}).caught(ExceptionSafe.PRINT_STACK_TRACE));
			});
		}
	}

	@Override
	public String toString(@Nullable Event e, boolean debug) {
		return "";
	}

	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
		this.effect = (Expression<FireworkEffect>)exprs[0];
		this.location = (Expression<Location>)exprs[1];
		if (exprs.length == 3) {
			this.player = (Expression<Player>)exprs[2];
		}
		return true;
	}
}
