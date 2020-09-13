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
import com.comphenix.protocol.wrappers.BlockPosition;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.InvocationTargetException;

@Description({"ブロックにひびをつけます", "idを同一にすれば後からひびの入り具合を変更できます", "ひびの入り具合は0 ~ 9"})
public class EffDestroyStage extends Effect {

    static {
        if (Bukkit.getPluginManager().isPluginEnabled("ProtocolLib")) {
            Skript.registerEffect(EffDestroyStage.class, "set destroy stage with id %string% of %block% to %number%");
        }
    }

    private Expression<String> id;
    private Expression<Block> block;
    private Expression<Number> stage;

    @Override
    protected void execute(Event e) {
        if (this.block != null && this.id != null && this.stage != null) {
            PacketContainer breakAnimation = new PacketContainer(PacketType.Play.Server.BLOCK_BREAK_ANIMATION);
            breakAnimation.getIntegers().write(0, this.id.getSingle(e).hashCode());
            breakAnimation.getBlockPositionModifier().write(0, new BlockPosition(this.block.getSingle(e).getLocation().toVector()));
            breakAnimation.getIntegers().write(1, this.stage.getSingle(e).intValue());
            Bukkit.getOnlinePlayers().stream().filter(player -> player.getWorld() == this.block.getSingle(e).getWorld()).forEach(player -> {
                try {
                    ProtocolLibrary.getProtocolManager().sendServerPacket(player, breakAnimation);
                } catch (InvocationTargetException ex) {
                    ex.printStackTrace();
                }
            });
        }
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        this.id = (Expression<String>)exprs[0];
        this.block = (Expression<Block>)exprs[1];
        this.stage = (Expression<Number>)exprs[2];
        return true;
    }
}
