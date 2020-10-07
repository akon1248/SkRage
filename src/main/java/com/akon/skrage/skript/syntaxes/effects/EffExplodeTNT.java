package com.akon.skrage.skript.syntaxes.effects;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

public class EffExplodeTNT extends Effect {
	@Override
	protected void execute(Event e) {

	}

	@Override
	public String toString(@Nullable Event e, boolean debug) {
		return null;
	}

	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
		return false;
	}
}
