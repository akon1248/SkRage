package com.akon.skrage.utils.entityvisibility;

import com.akon.skrage.SkRage;
import com.akon.skrage.utils.exceptionsafe.ExceptionSafe;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.google.common.collect.Maps;
import net.minecraft.server.v1_12_R1.EntityPlayer;
import net.minecraft.server.v1_12_R1.WorldServer;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.*;

public class EntityVisibilityManager {

    private static final HashMap<UUID, Set<Entity>> HIDDEN_ENTITY_MAP = Maps.newHashMap();

    public static void hideEntity(Player player, Entity entity) {
        if (entity instanceof Player) {
            player.hidePlayer(SkRage.getInstance(), (Player)entity);
        } else {
            Set<Entity> entitySet;
            if ((entitySet = HIDDEN_ENTITY_MAP.get(player.getUniqueId())) == null) {
                HIDDEN_ENTITY_MAP.put(player.getUniqueId(), (entitySet = Collections.newSetFromMap(new WeakHashMap<>())));
            }
            entitySet.add(entity);
            PacketContainer destroy = new PacketContainer(PacketType.Play.Server.ENTITY_DESTROY);
            destroy.getIntegerArrays().write(0, new int[]{entity.getEntityId()});
            ExceptionSafe.runnable(() -> ProtocolLibrary.getProtocolManager().sendServerPacket(player, destroy)).onCatch(ExceptionSafe.PRINT_STACK_TRACE).run();
        }
    }

    public static void showEntity(Player player, Entity entity) {
        if (entity instanceof Player) {
            player.showPlayer(SkRage.getInstance(), (Player)entity);
        } else {
            Set<Entity> entitySet;
            if ((entitySet = HIDDEN_ENTITY_MAP.get(player.getUniqueId())) != null && entitySet.remove(entity)) {
                if (entitySet.isEmpty()) {
                    HIDDEN_ENTITY_MAP.remove(player.getUniqueId());
                }
                EntityPlayer nmsPlayer = ((CraftPlayer)player).getHandle();
                Optional.ofNullable(((WorldServer) nmsPlayer.world).tracker.trackedEntities.get(entity.getEntityId())).ifPresent(entityTrackerEntry -> {
                    entityTrackerEntry.updatePlayer(nmsPlayer);
                });
            }
        }
    }

    public static boolean canSee(Player player, Entity entity) {
        if (entity instanceof Player) {
            return player.canSee((Player)entity);
        } else {
            return Optional.ofNullable(HIDDEN_ENTITY_MAP.get(player.getUniqueId())).map(entitySet -> !entitySet.contains(entity)).orElse(true);
        }
    }

}
