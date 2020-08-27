package com.akon.skrage.syntaxes.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.expressions.base.EventValueExpression;
import ch.njol.skript.lang.ExpressionType;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageEvent.DamageModifier;

import org.jetbrains.annotations.Nullable;

public class ExprDamageModifier extends EventValueExpression<DamageModifier> {

	static {
		Skript.registerExpression(ExprDamageModifier.class, DamageModifier.class, ExpressionType.SIMPLE, "[the] damage modifier");
	}

	@Override
	public Class<? extends DamageModifier> getReturnType() {
		return DamageModifier.class;
	}

	public ExprDamageModifier() {
		super(DamageModifier.class);
	}
	
	@Override
	public String toString(@Nullable Event e, boolean b) {
		return "[the] damage modifier";
	}

}
