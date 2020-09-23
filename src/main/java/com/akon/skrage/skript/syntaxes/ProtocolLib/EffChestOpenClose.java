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
import java.util.Optional;

@Description({"チェストやシュルカーボックスなどに開く/閉じるの動作をさせます"})
public class EffChestOpenClose extends Effect {

    static {
        Skript.registerEffect(EffChestOpenClose.class, "make open %block%", "make close %block%");
    }

    private Expression<Block> block;
    private boolean open;

    @Override
    protected void execute(Event e) {
        Optional.ofNullable(this.block).map(expr -> expr.getSingle(e)).filter(block -> {
            switch (block.getType()) {
                case CHEST:
                case TRAPPED_CHEST:
                case ENDER_CHEST:
                case RED_SHULKER_BOX:
                case BLUE_SHULKER_BOX:
                case CYAN_SHULKER_BOX:
                case GRAY_SHULKER_BOX:
                case LIME_SHULKER_BOX:
                case PINK_SHULKER_BOX:
                case BLACK_SHULKER_BOX:
                case BROWN_SHULKER_BOX:
                case GREEN_SHULKER_BOX:
                case WHITE_SHULKER_BOX:
                case ORANGE_SHULKER_BOX:
                case PURPLE_SHULKER_BOX:
                case SILVER_SHULKER_BOX:
                case YELLOW_SHULKER_BOX:
                case MAGENTA_SHULKER_BOX:
                case LIGHT_BLUE_SHULKER_BOX:
                    return true;
                default:
                    return false;
            }
        }).ifPresent(block -> {
            PacketContainer blockAction = new PacketContainer(PacketType.Play.Server.BLOCK_ACTION);
            blockAction.getBlockPositionModifier().write(0, new BlockPosition(block.getLocation().toVector()));
            blockAction.getIntegers().write(0, 1);
            blockAction.getIntegers().write(1, this.open ? 1 : 0);
            blockAction.getBlocks().write(0, block.getType());
            Bukkit.getOnlinePlayers().stream().filter(player -> player.getWorld() == block.getWorld()).forEach(player -> {
                try {
                    ProtocolLibrary.getProtocolManager().sendServerPacket(player, blockAction);
                } catch (InvocationTargetException ex) {
                    ex.printStackTrace();
                }
            });
        });
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        this.block = (Expression<Block>)exprs[0];
        this.open = matchedPattern == 0;
        return true;
    }
}
