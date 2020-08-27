package com.akon.skrage.utils.skin;

import com.akon.skrage.SkRage;
import com.akon.skrage.utils.NMSUtil;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.PlayerInfoData;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.comphenix.protocol.wrappers.WrappedGameProfile;
import com.google.common.collect.Maps;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.Collectors;

public class SkinManager {

	private static final HashMap<UUID, Skin> PLAYER_SKIN_MAP = Maps.newHashMap();

	public static Skin getDisplayedSkin(Player p) {
		return Optional.ofNullable(PLAYER_SKIN_MAP.get(p.getUniqueId())).orElse(Skin.fromGameProfile(WrappedGameProfile.fromPlayer(p)));
	}

	public static void changeSkin(Player p, @Nullable Skin skin) {
		PLAYER_SKIN_MAP.put(p.getUniqueId(), Optional.ofNullable(skin).orElse(Skin.EMPTY));
		updateGameProfile(p);
	}

	public static void resetSkin(Player p) {
		PLAYER_SKIN_MAP.remove(p.getUniqueId());
		updateGameProfile(p);
	}

	public static void updateGameProfile(Player p) {
		for (Player player: Bukkit.getOnlinePlayers()) {
			if (player != p && player.canSee(p)) {
				player.hidePlayer(SkRage.getInstance(), p);
				player.showPlayer(SkRage.getInstance(), p);
			}
		}
		if (p.getVehicle() != null) {
			p.getVehicle().removePassenger(p);
		}
		Entity vehicle = p.getVehicle();
		Bukkit.getScheduler().runTask(SkRage.getInstance(), () -> {
			if (vehicle != null && Bukkit.getEntity(vehicle.getUniqueId()) != null) {
				vehicle.addPassenger(p);
			}
		});
		PlayerInfoData playerInfoData = new PlayerInfoData(PLAYER_SKIN_MAP.get(p.getUniqueId()).toGameProfile(p.getUniqueId(), p.getName()), 0, EnumWrappers.NativeGameMode.fromBukkit(p.getGameMode()), WrappedChatComponent.fromText(p.getPlayerListName()));
		PacketContainer removeInfo = new PacketContainer(PacketType.Play.Server.PLAYER_INFO);
		removeInfo.getPlayerInfoAction().write(0, EnumWrappers.PlayerInfoAction.REMOVE_PLAYER);
		removeInfo.getPlayerInfoDataLists().write(0, Collections.singletonList(playerInfoData));
		PacketContainer addInfo = new PacketContainer(PacketType.Play.Server.PLAYER_INFO);
		addInfo.getPlayerInfoAction().write(0, EnumWrappers.PlayerInfoAction.ADD_PLAYER);
		addInfo.getPlayerInfoDataLists().write(0, Collections.singletonList(playerInfoData));
		PacketContainer respawn = new PacketContainer(PacketType.Play.Server.RESPAWN);
		respawn.getIntegers().write(0, p.getWorld().getEnvironment().getId());
		respawn.getDifficulties().write(0, EnumWrappers.getDifficultyConverter().getSpecific(p.getWorld().getDifficulty()));
		respawn.getGameModes().write(0, EnumWrappers.NativeGameMode.fromBukkit(p.getGameMode()));
		respawn.getWorldTypeModifier().write(0, p.getWorld().getWorldType());
		PacketContainer position = new PacketContainer(PacketType.Play.Server.POSITION);
		position.getModifier().writeDefaults();
		position.getDoubles().write(0, p.getLocation().getX());
		position.getDoubles().write(1, p.getLocation().getY());
		position.getDoubles().write(2, p.getLocation().getZ());
		position.getFloat().write(0, p.getLocation().getYaw());
		position.getFloat().write(1, p.getLocation().getPitch());
		PacketContainer op = new PacketContainer(PacketType.Play.Server.ENTITY_STATUS);
		op.getIntegers().write(0, p.getEntityId());
		op.getBytes().write(0, (byte)(24 + NMSUtil.getOperatorLevel(p)));
		List<PacketContainer> effects = p.getActivePotionEffects().stream().map(effect -> {
			PacketContainer packet = new PacketContainer(PacketType.Play.Server.ENTITY_EFFECT);
			packet.getIntegers().write(0, p.getEntityId());
			packet.getBytes().write(0, (byte)(effect.getType().getId() & 255));
			packet.getBytes().write(1, (byte)(effect.getAmplifier() & 255));
			packet.getIntegers().write(1, effect.getDuration());
			byte particle = 0;
			if (effect.isAmbient()) {
				particle = (byte)(particle | 1);
			}
			if (effect.hasParticles()) {
				particle = (byte)(particle | 2);
			}
			packet.getBytes().write(2, particle);
			return packet;
		}).collect(Collectors.toList());
		try {
			ProtocolLibrary.getProtocolManager().sendServerPacket(p, removeInfo);
			ProtocolLibrary.getProtocolManager().sendServerPacket(p, addInfo);
			ProtocolLibrary.getProtocolManager().sendServerPacket(p, respawn);
			ProtocolLibrary.getProtocolManager().sendServerPacket(p, position);
			ProtocolLibrary.getProtocolManager().sendServerPacket(p, op);
			for (PacketContainer packet: effects) {
				ProtocolLibrary.getProtocolManager().sendServerPacket(p, packet);
			}
		} catch (ReflectiveOperationException ex) {
			ex.printStackTrace();
		}
		p.setHealth(p.getHealth());
		p.updateInventory();
		p.getInventory().setHeldItemSlot(p.getInventory().getHeldItemSlot());
		p.setExp(p.getExp());
		p.setTotalExperience(p.getTotalExperience());
		p.setItemInHand(p.getItemInHand());
		p.getInventory().setItemInOffHand(p.getInventory().getItemInOffHand());
		p.setFlySpeed(p.getFlySpeed());
		p.setWalkSpeed(p.getWalkSpeed());
		p.setScoreboard(p.getScoreboard());
	}

}
