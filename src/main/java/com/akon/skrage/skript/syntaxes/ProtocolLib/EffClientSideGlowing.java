package com.akon.skrage.skript.syntaxes.ProtocolLib;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import com.google.common.collect.Sets;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;

@Description({"特定のプレイヤーに対してEntityが発光しているように見せます"})
public class EffClientSideGlowing extends Effect {

    private Expression<Entity> entities;
    private Expression<Player> players;
    private int matchedPattern;

    static {
        if (Bukkit.getPluginManager().isPluginEnabled("ProtocolLib")) {
            Skript.registerEffect(EffClientSideGlowing.class, "make %entities% glow[ing] for %players%", "make %entities% unglow[ing] for %players%");
        }
    }

    @Override
    protected void execute(@NotNull Event e) {
        if (this.entities != null && this.players != null) {
            HashSet<PacketContainer> packets = Sets.newHashSet();
            for (Entity entity: this.entities.getAll(e)) {
                PacketContainer packet = new PacketContainer(PacketType.Play.Server.ENTITY_METADATA);
                packet.getIntegers().write(0, entity.getEntityId());
                WrappedDataWatcher dataWatcher = new WrappedDataWatcher();
                WrappedDataWatcher.Serializer serializer = WrappedDataWatcher.Registry.get(Byte.class);
                int bitMask = new WrappedDataWatcher(entity).getByte(0);
                if (matchedPattern == 0) {
                    bitMask = bitMask | 0b01000000;
                } else {
                    bitMask = bitMask & 0b10111111;
                }
                dataWatcher.setObject(new WrappedDataWatcher.WrappedDataWatcherObject(0, serializer), (byte)bitMask);
                packet.getWatchableCollectionModifier().write(0, dataWatcher.getWatchableObjects());
                packets.add(packet);
            }
            for (Player player: this.players.getAll(e)) {
                for (PacketContainer packet : packets) {
                    try {
                        ProtocolLibrary.getProtocolManager().sendServerPacket(player, packet);
                    } catch (InvocationTargetException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        this.entities = (Expression<Entity>)exprs[0];
        this.players = (Expression<Player>)exprs[1];
        this.matchedPattern = matchedPattern;
        return true;
    }

}
