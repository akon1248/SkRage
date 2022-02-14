package com.akon.skrage.utils.entityvisibility;

import com.akon.skrage.SkRage;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;

import java.util.Optional;

public class EntityVisibilityListener extends PacketAdapter {

    public static final EntityVisibilityListener INSTANCE = new EntityVisibilityListener();

    private EntityVisibilityListener() {
        super(SkRage.getInstance(), PacketType.Play.Server.NAMED_ENTITY_SPAWN, PacketType.Play.Server.SPAWN_ENTITY, PacketType.Play.Server.SPAWN_ENTITY_EXPERIENCE_ORB, PacketType.Play.Server.SPAWN_ENTITY_LIVING, PacketType.Play.Server.SPAWN_ENTITY_PAINTING, PacketType.Play.Server.SPAWN_ENTITY_WEATHER);
    }

    @Override
    public void onPacketSending(PacketEvent e) {
        Optional.ofNullable(e.getPacket().getEntityModifier(e).read(0)).filter(entity -> !EntityVisibilityManager.canSee(e.getPlayer(), entity)).ifPresent(entity -> e.setCancelled(true));
    }
}
