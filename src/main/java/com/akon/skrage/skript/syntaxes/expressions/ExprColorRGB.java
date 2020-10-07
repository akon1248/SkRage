package com.akon.skrage.skript.syntaxes.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.skript.util.Color;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class ExprColorRGB extends SimpleExpression<Number> {

	static {
		Skript.registerExpression(ExprColorRGB.class, Number.class, ExpressionType.COMBINED, "(0¦red|1¦green|2¦blue) (from|of) %color%");
	}

	private Expression<Color> color;
	private int rgb;

	@Nullable
	@Override
	protected Number[] get(Event e) {
		return Optional.ofNullable(this.color).map(expr -> expr.getSingle(e)).map(Color::asBukkitColor).map(col -> {
			switch (this.rgb) {
				case 0:
					return new Number[]{col.getRed()};
				case 1:
					return new Number[]{col.getGreen()};
				case 2:
					return new Number[]{col.getBlue()};
			}
			return null;
		}).orElse(null);
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
		this.color = (Expression<Color>)exprs[0];
		this.rgb = parseResult.mark;
		return true;
	}
}
