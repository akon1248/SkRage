package com.akon.skrage.skript.syntaxes.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.akon.skrage.utils.customstatuseffect.CSEManager;
import com.akon.skrage.utils.customstatuseffect.CSEType;
import com.akon.skrage.utils.customstatuseffect.CustomStatusEffect;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

public class ExprCustomStatusEffect extends SimpleExpression<CustomStatusEffect> {

	static {
		Skript.registerExpression(ExprCustomStatusEffect.class, CustomStatusEffect.class, ExpressionType.COMBINED, "(cse|custom[ ]status[ ]effect) %customstatuseffecttype% (from|of) %livingentity%");
	}

	private Expression<CSEType> type;
	private Expression<LivingEntity> entity;

	@Nullable
	@Override
	protected CustomStatusEffect[] get(Event e) {
		if (this.type != null && this.entity != null) {
			return new CustomStatusEffect[]{CSEManager.getActiveCSEs(this.entity.getSingle(e)).get(this.type.getSingle(e))};
		}
		return null;
	}

	@Override
	public boolean isSingle() {
		return true;
	}

	@Override
	public Class<? extends CustomStatusEffect> getReturnType() {
		return CustomStatusEffect.class;
	}

	@Override
	public String toString(@Nullable Event e, boolean debug) {
		return "";
	}

	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
		this.type = (Expression<CSEType>)exprs[0];
		this.entity = (Expression<LivingEntity>)exprs[1];
		return true;
	}
}
