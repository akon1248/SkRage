package com.akon.skrage.utils.freeze;

import com.akon.skrage.SkRage;
import com.akon.skrage.utils.LogUtil;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.google.common.collect.Maps;
import net.minecraft.server.v1_12_R1.PacketPlayOutAbilities;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

public class FreezeManager {

	private static final HashMap<UUID, Byte> FROZEN_PLAYERS = Maps.newHashMap();

	public static boolean freeze(Player player) {
		boolean flag = !FROZEN_PLAYERS.containsKey(player.getUniqueId());
		if (flag) {
			byte bitMask = (byte)((player.getAllowFlight() ? 1 : 0) | ((player.isFlying() ? 1 : 0) << 1));
			player.setAllowFlight(true);
			player.teleport(player.getLocation().add(0, 0.01, 0));
			player.setFlying(true);
			PacketContainer packet = PacketContainer.fromPacket(new PacketPlayOutAbilities(((CraftPlayer)player).getHandle().abilities));
			packet.getFloat().write(0, 0.0F);
			try {
				ProtocolLibrary.getProtocolManager().sendServerPacket(player, packet);
			} catch (InvocationTargetException ex) {
				LogUtil.logThrowable(ex);
			}
			player.setMetadata("Freeze_BoundLocation", new FixedMetadataValue(SkRage.getInstance(), player.getLocation()));
			FROZEN_PLAYERS.put(player.getUniqueId(), bitMask);
		}
		return flag;
	}

	public static boolean unfreeze(Player player) {
		Byte flyFlags = FROZEN_PLAYERS.remove(player.getUniqueId());
		if (flyFlags != null) {
			boolean flight = (flyFlags & 1) > 0;
			boolean flying = (flyFlags >> 1 & 1) > 0;
			player.setAllowFlight(flight);
			player.setFlying(flying);
			player.setFlySpeed(player.getFlySpeed());
			player.removeMetadata("Freeze_BoundLocation", SkRage.getInstance());
		}
		return flyFlags != null;
	}

	public static boolean isFrozen(Player player) {
		return FROZEN_PLAYERS.containsKey(player.getUniqueId());
	}

	public static boolean wasAllowFlight(Player player) {
		return (Optional.ofNullable(FROZEN_PLAYERS.get(player.getUniqueId())).orElse((byte)0) & 1) > 0;
	}

	public static boolean wasFlying(Player player) {
		return (Optional.ofNullable(FROZEN_PLAYERS.get(player.getUniqueId())).orElse((byte)0) >> 1 & 1) > 0;
	}

}
