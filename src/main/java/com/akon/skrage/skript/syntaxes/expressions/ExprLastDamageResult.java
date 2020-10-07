package com.akon.skrage.skript.syntaxes.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.akon.skrage.skript.syntaxes.effects.EffDamageSource;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

public class ExprLastDamageResult extends SimpleExpression<Boolean> {

	static {
		Skript.registerExpression(ExprLastDamageResult.class, Boolean.class, ExpressionType.SIMPLE, "このアドオンのdamage構文を使用してダメージを与えられたかどうかの結果を取得します");
	}

	@Nullable
	@Override
	protected Boolean[] get(Event e) {
		return new Boolean[]{EffDamageSource.getLastResult()};
	}

	@Override
	public boolean isSingle() {
		return true;
	}

	@Override
	public Class<? extends Boolean> getReturnType() {
		return Boolean.class;
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
