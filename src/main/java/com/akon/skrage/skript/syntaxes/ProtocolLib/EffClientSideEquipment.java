package com.akon.skrage.skript.syntaxes.ProtocolLib;

import ch.njol.skript.Skript;
import ch.njol.skript.aliases.ItemType;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.EnumWrappers;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class EffClientSideEquipment extends Effect {

    static {
        Skript.registerEffect(EffClientSideEquipment.class, "make %entities% (0¦[main]hand|1¦offhand|2¦(boots|feet)|3¦(le[ggin]gs)|4¦chest[plate]|5¦he(lmet|ad)) %itemtype% for %players%");
    }

    private Expression<Entity> entities;
    private Expression<ItemType> itemType;
    private Expression<Player> players;
    private EnumWrappers.ItemSlot itemSlot;

    @Override
    protected void execute(Event e) {
        if (this.entities != null && this.itemType != null && this.players != null) {
            ItemStack item = this.itemType.getSingle(e).getRandom();
            Arrays.stream(this.entities.getAll(e)).map(entity -> {
                PacketContainer packet = new PacketContainer(PacketType.Play.Server.ENTITY_EQUIPMENT);
                packet.getIntegers().write(0, entity.getEntityId());
                packet.getItemSlots().write(0, this.itemSlot);
                packet.getItemModifier().write(0, item);
                return packet;
            }).forEach(packet -> Arrays.stream(this.players.getAll(e)).forEach(player -> {
                try {
                    ProtocolLibrary.getProtocolManager().sendServerPacket(player, packet);
                } catch (InvocationTargetException ex) {
                    ex.printStackTrace();
                }
            }));
        }
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        this.entities = (Expression<Entity>)exprs[0];
        this.itemType = (Expression<ItemType>)exprs[1];
        this.players = (Expression<Player>)exprs[2];
        this.itemSlot = EnumWrappers.ItemSlot.values()[parseResult.mark];
        return false;
    }
}
