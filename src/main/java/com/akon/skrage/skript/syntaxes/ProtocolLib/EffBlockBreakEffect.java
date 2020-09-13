package com.akon.skrage.skript.syntaxes.ProtocolLib;

import ch.njol.skript.Skript;
import ch.njol.skript.aliases.ItemType;
import ch.njol.skript.doc.Description;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.BlockPosition;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import java.lang.reflect.InvocationTargetException;

@Description({"ブロックが壊れた時のエフェクトを発生させます"})
public class EffBlockBreakEffect extends Effect {

    private Expression<ItemType> itemType;
    private Expression<Location> location;
    private Expression<Player> player;

    static {
        if (Bukkit.getPluginManager().isPluginEnabled("ProtocolLib")) Skript.registerEffect(EffBlockBreakEffect.class, "show %itemtype% (break[ing]|destroy) effect at %location% [for %-players%]");
    }

    @Override
    protected void execute(Event e) {
        if (this.itemType != null && this.location != null && this.itemType.getSingle(e).getRandom().getType().isBlock()) {
            Player[] players;
            if (this.player == null) {
                players = Bukkit.getOnlinePlayers().toArray(new Player[0]);
            } else {
                players = this.player.getAll(e);
            }
            PacketContainer packet = new PacketContainer(PacketType.Play.Server.WORLD_EVENT);
            packet.getModifier().writeDefaults();
            packet.getIntegers().write(0, 2001);
            int x = this.location.getSingle(e).getBlockX();
            int y = this.location.getSingle(e).getBlockY();
            int z = this.location.getSingle(e).getBlockZ();
            packet.getBlockPositionModifier().write(0, new BlockPosition(x, y, z));
            packet.getIntegers().write(1, this.itemType.getSingle(e).getRandom().getTypeId() + 4096 * this.itemType.getSingle(e).getRandom().getDurability());
            for (Player player: players) {
                if (player.getWorld() == this.location.getSingle(e).getWorld()) {
                    try {
                        ProtocolLibrary.getProtocolManager().sendServerPacket(player, packet);
                    } catch (InvocationTargetException ignored) {}
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
        this.itemType = (Expression<ItemType>)exprs[0];
        this.location = (Expression<Location>)exprs[1];
        if (exprs.length == 3) this.player = (Expression<Player>)exprs[2];
        return true;
    }
}
