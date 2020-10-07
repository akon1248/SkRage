package com.akon.skrage.skript.syntaxes.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

public class ExprAttribute extends SimpleExpression<AttributeInstance> {

	static {
		Skript.registerExpression(ExprAttribute.class, AttributeInstance.class, ExpressionType.COMBINED, "attribute (0¦max health|1¦follow range|2¦knockback resistance|3¦movement speed|4¦flying speed|5¦attack damage|6¦attack speed|7¦armor|8¦armor toughness|9¦luck|10¦horse jump strength|11¦zombie spawn reinforcements) (from|of) %livingentity%");
	}

	private Expression<LivingEntity> entity;
	private Attribute attribute;

	@Nullable
	@Override
	protected AttributeInstance[] get(Event e) {
		if (this.entity != null) {
			return new AttributeInstance[]{this.entity.getSingle(e).getAttribute(this.attribute)};
		}
		return null;
	}

	@Override
	public boolean isSingle() {
		return true;
	}

	@Override
	public Class<? extends AttributeInstance> getReturnType() {
		return AttributeInstance.class;
	}

	@Override
	public String toString(@Nullable Event e, boolean debug) {
		return "";
	}

	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
		this.entity = (Expression<LivingEntity>)exprs[0];
		this.attribute = Attribute.values()[parseResult.mark];
		return true;
	}
}
