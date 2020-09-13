package com.akon.skrage.skript.syntaxes.expressions;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageEvent;

import org.jetbrains.annotations.Nullable;

@Description({"Damage Modifierによって変化するダメージの量を取得します"})
public class ExprDamageModifierDamage extends SimpleExpression<Number> {
	
	static {
	    Skript.registerExpression(ExprDamageModifierDamage.class, Number.class, ExpressionType.COMBINED, "damage modifier %damagemodifier%");
	}
	
	private Expression<EntityDamageEvent.DamageModifier> damageModifier;

	@Override
	public Class<? extends Number> getReturnType() {
		return Number.class;
	}

	@Override
	public boolean isSingle() {
		return true;
	}

	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
		if (ScriptLoader.isCurrentEvent(EntityDamageEvent.class)) {
			this.damageModifier = (Expression<EntityDamageEvent.DamageModifier>)exprs[0];
			return true;
		}
		Skript.error("Damage ModifierはDamageイベントでのみ使用可能です");
		return false;
	}

	@Override
	public String toString(Event e, boolean debug) {
		return "damage modifier %damagemodifier%";
	}

	@Override
	@Nullable
	protected Number[] get(Event e) {
		if (this.damageModifier != null) {
			return new Number[]{((EntityDamageEvent)e).getDamage(this.damageModifier.getSingle(e))};
		}
		return null;
	}
	
	public void change(Event e, Object[] delta, Changer.ChangeMode mode) {
		if (this.damageModifier != null) {
			Number num1 = (Number)delta[0];
			Number num2 = ((EntityDamageEvent)e).getDamage(this.damageModifier.getSingle(e));
			if (mode == Changer.ChangeMode.SET) {
				((EntityDamageEvent)e).setDamage(this.damageModifier.getSingle(e), num1.intValue());
			} else if (mode == Changer.ChangeMode.ADD) {
				((EntityDamageEvent)e).setDamage(this.damageModifier.getSingle(e), num2.intValue()+num1.intValue());
			} else if (mode == Changer.ChangeMode.REMOVE) {
				((EntityDamageEvent)e).setDamage(this.damageModifier.getSingle(e), num2.intValue()-num1.intValue());
			}
		}
	}
	
	public Class<?>[] acceptChange(Changer.ChangeMode mode) {
		if (mode == Changer.ChangeMode.SET || mode == Changer.ChangeMode.ADD || mode == Changer.ChangeMode.REMOVE) {
			return CollectionUtils.array(Number.class);
		}
		return null;
	}

}
