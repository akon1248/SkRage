package com.akon.skrage.utils.skin;

import com.akon.skrage.SkRage;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.PlayerInfoData;
import com.comphenix.protocol.wrappers.WrappedGameProfile;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

public class SkinListener extends PacketAdapter {

	public SkinListener() {
		super(SkRage.getInstance(), PacketType.Play.Server.PLAYER_INFO);
	}

	@Override
	public void onPacketSending(PacketEvent e) {
		if (e.getPacketType() == PacketType.Play.Server.PLAYER_INFO) {
			List<PlayerInfoData> list = e.getPacket().getPlayerInfoDataLists().read(0);
			List<PlayerInfoData> newPlayerInfo = list.stream().map(playerInfoData -> {
				WrappedGameProfile profile = playerInfoData.getProfile();
				Player p = Bukkit.getPlayer(profile.getUUID());
				if (p != null) {
					return new PlayerInfoData(SkinManager.getDisplayedSkin(p).toGameProfile(profile.getUUID(), profile.getName()), playerInfoData.getLatency(), playerInfoData.getGameMode(), playerInfoData.getDisplayName());
				}
				return playerInfoData;
			}).collect(Collectors.toList());
			e.getPacket().getPlayerInfoDataLists().write(0, newPlayerInfo);
		}
	}

}
