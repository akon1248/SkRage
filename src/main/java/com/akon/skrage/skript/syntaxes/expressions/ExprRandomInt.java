package com.akon.skrage.skript.syntaxes.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.Random;

@Description({"ランダムな整数(32bitの範囲)を生成します", "boundを指定した場合0 ~ bound-1の範囲のランダムな整数が生成されます"})
public class ExprRandomInt extends SimpleExpression<Number> {

	static {
		Skript.registerExpression(ExprRandomInt.class, Number.class, ExpressionType.COMBINED, "random int[eger] from %random% [with bound %-number%]");
	}

	private Expression<Random> random;
	private Expression<Number> bound;

	@Nullable
	@Override
	protected Number[] get(Event e) {
		return Optional.ofNullable(this.random)
			.map(expr -> expr.getSingle(e))
			.map(random -> Optional.ofNullable(this.bound)
				.map(expr -> expr.getSingle(e))
				.map(Number::intValue)
				.map(random::nextInt)
				.orElseGet(random::nextInt)
			)
			.map(i -> new Number[]{i})
			.orElse(null);
	}

	@Override
	public boolean isSingle() {
		return true;
	}

	@Override
	public Class<? extends Number> getReturnType() {
		return Number.class;
	}

	@Override
	public String toString(@Nullable Event e, boolean debug) {
		return "";
	}

	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
		this.random = (Expression<Random>)exprs[0];
		this.bound = (Expression<Number>)exprs[1];
		return true;
	}
}
