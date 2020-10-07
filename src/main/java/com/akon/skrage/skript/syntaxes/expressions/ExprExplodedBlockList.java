package com.akon.skrage.skript.syntaxes.expressions;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import org.bukkit.block.Block;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Description("on explodeイベントで爆発によって壊されたブロック")
public class ExprExplodedBlockList extends SimpleExpression<Block> {

	static {
		Skript.registerExpression(ExprExplodedBlockList.class, Block.class, ExpressionType.SIMPLE, "explode[d] block list");
	}

	@Nullable
	@Override
	protected Block[] get(Event e) {
		return ((EntityExplodeEvent)e).blockList().toArray(new Block[0]);
	}

	@Override
	public boolean isSingle() {
		return false;
	}

	@Override
	public Class<? extends Block> getReturnType() {
		return Block.class;
	}

	@Override
	public String toString(@Nullable Event e, boolean debug) {
		return "";
	}

	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
		if (!ScriptLoader.isCurrentEvent(EntityExplodeEvent.class)) {
			Skript.error("exploded block listはon explodeイベントでのみ使用可能です");
			return false;
		}
		return true;
	}

	@Nullable
	@Override
	public Class<?>[] acceptChange(Changer.ChangeMode mode) {
		return mode != Changer.ChangeMode.RESET ? CollectionUtils.array(Block.class) : null;
	}

	@Override
	public void change(Event e, @Nullable Object[] delta, Changer.ChangeMode mode) {
		List<Block> blockList = ((EntityExplodeEvent)e).blockList();
		switch (mode) {
			case SET:
				blockList.clear();
			case ADD:
				Optional.ofNullable(delta).ifPresent(arr -> Arrays.stream(arr).map(Block.class::cast).forEach(blockList::add));
				break;
			case DELETE:
				blockList.clear();
				break;
			case REMOVE:
				Optional.ofNullable(delta).ifPresent(arr -> Arrays.stream(arr).forEach(blockList::remove));
				break;
			case REMOVE_ALL:
				Optional.ofNullable(delta).map(Arrays::asList).ifPresent(list -> blockList.removeIf(list::contains));
		}
	}
}
