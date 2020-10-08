package com.akon.skrage.skript.syntaxes.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.akon.skrage.utils.customstatuseffect.CSEManager;
import com.akon.skrage.utils.customstatuseffect.CSEType;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

@Description({"登録されているすべてのCustomStatusEffect"})
public class ExprAllCSETypes extends SimpleExpression<CSEType> {

	static {
		Skript.registerExpression(ExprAllCSETypes.class, CSEType.class, ExpressionType.SIMPLE, "all (cse|custom[ ]status[ ]effect) types");
	}

	@Nullable
	@Override
	protected CSEType[] get(Event e) {
		return CSEManager.getAll().toArray(new CSEType[0]);
	}

	@Override
	public boolean isSingle() {
		return false;
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
		return true;
	}
}
