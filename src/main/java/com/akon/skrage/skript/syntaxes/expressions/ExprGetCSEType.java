package com.akon.skrage.skript.syntaxes.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.akon.skrage.utils.customstatuseffect.CSEManager;
import com.akon.skrage.utils.customstatuseffect.CSEType;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

public class ExprGetCSEType extends SimpleExpression<CSEType> {

	static {
		Skript.registerExpression(ExprGetCSEType.class, CSEType.class, ExpressionType.COMBINED, "(cse|custom[ ]status[ ]effect) type from [id] %string%");
	}

	private Expression<String> id;

	@Nullable
	@Override
	protected CSEType[] get(Event e) {
		if (this.id != null) {
			return new CSEType[]{CSEManager.get(this.id.getSingle(e))};
		}
		return null;
	}

	@Override
	public boolean isSingle() {
		return true;
	}

	@Override
	public Class<? extends CSEType> getReturnType() {
		return CSEType.class;
	}

	@Override
	public String toString(@Nullable Event e, boolean debug) {
		return "";
	}

	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
		this.id = (Expression<String>)exprs[0];
		return true;
	}
}
