package com.akon.skrage.utils.spectator;

import com.akon.skrage.SkRage;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.reflect.StructureModifier;
import com.comphenix.protocol.wrappers.EnumWrappers;
import com.comphenix.protocol.wrappers.PlayerInfoData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerGameModeChangeEvent;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SpectatorListener extends PacketAdapter implements Listener {

    public static final SpectatorListener INSTANCE = new SpectatorListener();

    private SpectatorListener() {
        super(SkRage.getInstance(), PacketType.Play.Server.GAME_STATE_CHANGE, PacketType.Play.Server.NAMED_ENTITY_SPAWN, PacketType.Play.Server.PLAYER_INFO);
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onGameModeChange(PlayerGameModeChangeEvent e) {
        if (SpectatorManager.isSpectator(e.getPlayer())) {
            SpectatorManager.setSpectator(e.getPlayer(), false);
        }
    }

    @Override
    public void onPacketSending(PacketEvent event) {
        Player player = event.getPlayer();
        PacketContainer packet = event.getPacket();
        if (event.getPacketType() == PacketType.Play.Server.GAME_STATE_CHANGE) {
            if (SpectatorManager.isSpectator(player) && packet.getIntegers().read(0) == 3) {
                packet.getFloat().write(0, 2.0F);
            }
        } else if (event.getPacketType() == PacketType.Play.Server.NAMED_ENTITY_SPAWN) {
            Entity entity = packet.getEntityModifier(event).read(0);
            if (entity instanceof Player && SpectatorManager.isSpectator((Player)entity) && !SpectatorManager.isSpectator(player) ) {
                event.setCancelled(true);
            }

        } else if (event.getPacketType() == PacketType.Play.Server.PLAYER_INFO) {
            StructureModifier<List<PlayerInfoData>> modifier = packet.getPlayerInfoDataLists();
            modifier.write(0, modifier.read(0)
                .stream()
                .map(playerInfoData -> {
                    if (Optional.of(playerInfoData.getProfile().getUUID()).map(Bukkit::getPlayer).filter(SpectatorManager::isSpectator).isPresent()) {
                        return new PlayerInfoData(playerInfoData.getProfile(), playerInfoData.getLatency(), EnumWrappers.NativeGameMode.ADVENTURE, playerInfoData.getDisplayName());
                    } else {
                        return playerInfoData;
                    }
                })
                .collect(Collectors.toList())
            );
        }
    }
}
