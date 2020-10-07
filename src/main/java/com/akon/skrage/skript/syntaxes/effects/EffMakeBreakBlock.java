package com.akon.skrage.skript.syntaxes.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import net.minecraft.server.v1_12_R1.BlockPosition;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

@Description({"プレイヤーにブロックを破壊させます"})
public class EffMakeBreakBlock extends Effect {

	static {
		Skript.registerEffect(EffMakeBreakBlock.class, "make %player% (break|destroy) [block] %block%");
	}

	private Expression<Player> player;
	private Expression<Block> block;

	@Override
	protected void execute(Event e) {
		if (this.player != null) {
			Optional.ofNullable(this.block).map(expr -> expr.getSingle(e)).ifPresent(block -> ((CraftPlayer)this.player.getSingle(e)).getHandle().playerInteractManager.breakBlock(new BlockPosition(block.getX(), block.getY(), block.getZ())));
		}
	}

	@Override
	public String toString(@Nullable Event e, boolean debug) {
		return "";
	}

	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
		this.player = (Expression<Player>)exprs[0];
		this.block = (Expression<Block>)exprs[1];
		return true;
	}

}
