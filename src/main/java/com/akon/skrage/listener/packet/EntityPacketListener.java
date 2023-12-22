package com.akon.skrage.listener.packet;

import com.akon.skrage.SkRage;
import com.akon.skrage.event.EntityAddTrackingPlayerEvent;
import com.akon.skrage.event.EntityRemoveTrackingPlayerEvent;
import com.akon.skrage.event.TrackingEvent;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

public class EntityPacketListener extends PacketAdapter {

    public EntityPacketListener() {
        super(SkRage.getInstance(), PacketType.Play.Server.NAMED_ENTITY_SPAWN, PacketType.Play.Server.SPAWN_ENTITY, PacketType.Play.Server.SPAWN_ENTITY_EXPERIENCE_ORB, PacketType.Play.Server.SPAWN_ENTITY_LIVING, PacketType.Play.Server.SPAWN_ENTITY_PAINTING, PacketType.Play.Server.SPAWN_ENTITY_WEATHER, PacketType.Play.Server.ENTITY_DESTROY);
    }

    @Override
    public void onPacketSending(PacketEvent e) {
        if (e.getPacketType() == PacketType.Play.Server.ENTITY_DESTROY) {
            Arrays.stream(e.getPacket().getIntegerArrays().read(0))
                .mapToObj(id -> Optional.ofNullable(((CraftPlayer)e.getPlayer()).getHandle().getWorld().getEntity(id))
                    .map(net.minecraft.server.v1_12_R1.Entity::getBukkitEntity)
                    .orElse(null)
                )
                .filter(Objects::nonNull)
                .forEach(entity -> callTrackingEvent(e, new EntityRemoveTrackingPlayerEvent(entity, e.getPlayer()))
                );
        } else {
            Optional.ofNullable(e.getPacket().getEntityModifier(e).read(0)).ifPresent(entity ->
                callTrackingEvent(e, new EntityAddTrackingPlayerEvent(entity, e.getPlayer()))
            );
        }
    }

    private static void callTrackingEvent(PacketEvent packetEvent, TrackingEvent event) {
        Bukkit.getPluginManager().callEvent(event);
        packetEvent.setCancelled(event.isCancelled());
    }
}
