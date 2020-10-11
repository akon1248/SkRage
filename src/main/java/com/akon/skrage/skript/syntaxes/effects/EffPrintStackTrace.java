package com.akon.skrage.skript.syntaxes.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

@Description({"エラーのスタックトーレスを出力します"})
public class EffPrintStackTrace extends Effect {

	static {
		Skript.registerEffect(EffPrintStackTrace.class, "print stack[ ]trace of %throwable%");
	}

	private Expression<Throwable> throwable;

	@Override
	protected void execute(Event e) {
		Optional.ofNullable(this.throwable).map(expr -> expr.getSingle(e)).ifPresent(Throwable::printStackTrace);
	}

	@Override
	public String toString(@Nullable Event e, boolean debug) {
		return "";
	}

	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
		this.throwable = (Expression<Throwable>)exprs[0];
		return true;
	}
}
