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

@Description({"新しいランダムオブジェクトを生成します"})
public class ExprNewRandom extends SimpleExpression<Random> {

	static {
		Skript.registerExpression(ExprNewRandom.class, Random.class, ExpressionType.COMBINED, "new random [with seed %-number%]");
	}

	private Expression<Number> seed;

	@Nullable
	@Override
	protected Random[] get(Event e) {
		return new Random[]{Optional.ofNullable(this.seed).map(expr -> expr.getSingle(e)).map(num -> {
			long seed;
			if (num instanceof Float) {
				num = num.doubleValue();
			}
			if (num instanceof Double) {
				seed = Double.doubleToLongBits((Double)num);
			} else {
				seed = num.longValue();
			}
			return new Random(seed);
		}).orElse(new Random())};
	}

	@Override
	public boolean isSingle() {
		return true;
	}

	@Override
	public Class<? extends Random> getReturnType() {
		return Random.class;
	}

	@Override
	public String toString(@Nullable Event e, boolean debug) {
		return "";
	}

	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
		this.seed = (Expression<Number>)exprs[0];
		return true;
	}
}
