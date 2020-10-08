package com.akon.skrage.skript.syntaxes.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import org.bukkit.block.Block;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

@Description({"ブロックを更新します"})
public class EffUpdateBlock extends Effect {

	static {
		Skript.registerEffect(EffUpdateBlock.class, "update block %block%");
	}

	private Expression<Block> block;

	@Override
	protected void execute(Event e) {
		Optional.ofNullable(this.block).map(expr -> expr.getSingle(e)).map(Block::getState).ifPresent(state -> state.update(true, false));
	}

	@Override
	public String toString(@Nullable Event e, boolean debug) {
		return "";
	}

	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
		this.block = (Expression<Block>)exprs[0];
		return true;
	}
}
