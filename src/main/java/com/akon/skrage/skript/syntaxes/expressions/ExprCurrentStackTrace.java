package com.akon.skrage.skript.syntaxes.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

public class ExprCurrentStackTrace extends SimpleExpression<StackTraceElement> {

	static {
		Skript.registerExpression(ExprCurrentStackTrace.class, StackTraceElement.class, ExpressionType.SIMPLE, "current stack[ ]trace");
	}

	@Nullable
	@Override
	protected StackTraceElement[] get(Event e) {
		return new Throwable().getStackTrace();
	}

	@Override
	public boolean isSingle() {
		return false;
	}

	@Override
	public Class<? extends StackTraceElement> getReturnType() {
		return StackTraceElement.class;
	}

	@Override
	public String toString(@Nullable Event e, boolean debug) {
		return "";
	}

	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
		return true;
	}
}
