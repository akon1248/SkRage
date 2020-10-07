package com.akon.skrage.listener.packet;

import com.akon.skrage.SkRage;
import com.akon.skrage.event.EntityAddTrackingPlayerEvent;
import com.akon.skrage.event.EntityRemoveTrackingPlayerEvent;
import com.akon.skrage.event.TrackingEvent;
import com.akon.skrage.utils.entityvisibility.EntityVisibilityManager;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Entity;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;

public class EntityPacketListener extends PacketAdapter {

    public EntityPacketListener() {
        super(SkRage.getInstance(), PacketType.Play.Server.NAMED_ENTITY_SPAWN, PacketType.Play.Server.SPAWN_ENTITY, PacketType.Play.Server.SPAWN_ENTITY_EXPERIENCE_ORB, PacketType.Play.Server.SPAWN_ENTITY_LIVING, PacketType.Play.Server.SPAWN_ENTITY_PAINTING, PacketType.Play.Server.SPAWN_ENTITY_WEATHER, PacketType.Play.Server.ENTITY_DESTROY);
    }

    @Override
    public void onPacketSending(PacketEvent e) {
        Consumer<Entity> entityConsumer = entity -> {
            TrackingEvent event = null;
            if (e.getPacketType() == PacketType.Play.Server.ENTITY_DESTROY) {
                event = new EntityRemoveTrackingPlayerEvent(entity, e.getPlayer());
            } else if (EntityVisibilityManager.canSee(e.getPlayer(), entity)) {
                event = new EntityAddTrackingPlayerEvent(entity, e.getPlayer());
            }
            if (event != null) {
                Bukkit.getPluginManager().callEvent(event);
                e.setCancelled(event.isCancelled());
            }
        };
        if (e.getPacketType() == PacketType.Play.Server.ENTITY_DESTROY) {
            Arrays.stream(e.getPacket().getIntegerArrays().read(0)).mapToObj(id -> Optional.ofNullable(((CraftPlayer)e.getPlayer()).getHandle().getWorld().getEntity(id)).map(net.minecraft.server.v1_12_R1.Entity::getBukkitEntity).orElse(null)).filter(Objects::nonNull).forEach(entityConsumer);
        } else {
            Optional.ofNullable(e.getPacket().getEntityModifier(e).read(0)).ifPresent(entityConsumer);
        }
    }
}
