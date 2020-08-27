package com.akon.skrage.syntaxes.ProtocolLib;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.util.Color;
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

@Description({"ポーションが割れた時のエフェクトを発生させます"})
public class
EffPotionBreakEffect extends Effect {

    private Expression<Location> location;
    private Expression<Color> color;
    private Expression<Number> colorParam1;
    private Expression<Number> colorParam2;
    private Expression<Number> colorParam3;
    private Expression<Player> player;
    private boolean isSplash;

    static {
        if (Bukkit.getPluginManager().isPluginEnabled("ProtocolLib")) Skript.registerEffect(EffPotionBreakEffect.class, "show (1¦splash|) potion break[ing] effect at %location% with [color] %color% [(for|to) %players%]", "show (1¦splash|) potion break[ing] effect at %location% with [color] %number%[, %-number%(,| and) %-number%] [(for|to) %players%]");
    }

    @Override
    protected void execute(Event e) {
        if (this.location != null && (this.color != null || this.colorParam1 != null)) {
            Player[] players;
            if (this.player == null) {
                players = Bukkit.getOnlinePlayers().toArray(new Player[0]);
            } else {
                players = this.player.getAll(e);
            }
            PacketContainer packet = new PacketContainer(PacketType.Play.Server.WORLD_EVENT);
            packet.getModifier().writeDefaults();
            packet.getIntegers().write(0, this.isSplash ? 2007 : 2002);
            int x = this.location.getSingle(e).getBlockX();
            int y = this.location.getSingle(e).getBlockY();
            int z = this.location.getSingle(e).getBlockZ();
            int color = this.color != null ? this.color.getSingle(e).asBukkitColor().asRGB() : (this.colorParam1 != null && this.colorParam2 != null && this.colorParam3 != null) ? this.colorParam1.getSingle(e).intValue() * 65536 + this.colorParam2.getSingle(e).intValue() * 256 + this.colorParam3.getSingle(e).intValue() : this.colorParam1.getSingle(e).intValue();
            packet.getBlockPositionModifier().write(0, new BlockPosition(x, y, z));
            packet.getIntegers().write(1, color);
            for (Player player : players) {
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
        this.location = (Expression<Location>)exprs[0];
        if (matchedPattern == 0) {
            this.color = (Expression<Color>)exprs[1];
            if (exprs[2] != null) this.player = (Expression<Player>) exprs[2];
        } else if (matchedPattern == 1) {
            this.colorParam1 = (Expression<Number>) exprs[1];
            if (exprs[2] != null && exprs[2].getReturnType() == Player.class) {
                this.player = (Expression<Player>) exprs[2];
            } else if (exprs[2] != null && exprs[3] != null && Number.class.isAssignableFrom(exprs[2].getReturnType()) && Number.class.isAssignableFrom(exprs[3].getReturnType())) {
                this.colorParam2 = (Expression<Number>) exprs[2];
                this.colorParam3 = (Expression<Number>) exprs[3];
            }
        }
        this.isSplash = parseResult.mark == 1;
        return true;
    }
}
