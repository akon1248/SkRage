package com.akon.skrage.skript.syntaxes.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.skript.util.Color;
import ch.njol.util.Kleenean;
import com.akon.skrage.utils.ColorConverter;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class ExprNewColor extends SimpleExpression<Color> {

	static {
		Skript.registerExpression(ExprNewColor.class, Color.class, ExpressionType.COMBINED, "colo[u]r from (0¦[hex] colo[u]r[ ]code %-string%|1¦rgb %-number%[, %-number%, %-number%])");
	}

	private Expression<String> hexColorCode;
	private Expression<Number> red;
	private Expression<Number> green;
	private Expression<Number> blue;
	private boolean hex;

	@Nullable
	@Override
	protected Color[] get(Event e) {
		org.bukkit.Color color = org.bukkit.Color.BLACK;
		if (this.hex) {
			color = org.bukkit.Color.fromRGB(Optional.ofNullable(this.hexColorCode)
				.map(expr -> expr.getSingle(e))
				.filter(str -> str.matches("(?i)(#|0x)?[0-9a-f]{6}"))
				.map(code -> Integer.parseInt(code.substring(code.length()-6), 16))
				.orElse(0));
		} else if (this.red != null) {
			if (this.green == null) {
				color = org.bukkit.Color.fromRGB(this.red.getSingle(e).intValue());
			} else {
				color = org.bukkit.Color.fromRGB(this.red.getSingle(e).intValue(), this.green.getSingle(e).intValue(), this.blue.getSingle(e).intValue());
			}
		}
		return new Color[]{ColorConverter.toSkriptColor(color)};
	}

	@Override
	public boolean isSingle() {
		return true;
	}

	@Override
	public Class<? extends Color> getReturnType() {
		return Color.class;
	}

	@Override
	public String toString(@Nullable Event e, boolean debug) {
		return "";
	}

	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
		this.hex = parseResult.mark == 0;
		this.hexColorCode = (Expression<String>)exprs[0];
		this.red = (Expression<Number>)exprs[1];
		this.green = (Expression<Number>)exprs[2];
		this.blue = (Expression<Number>)exprs[3];
		return true;
	}
}
