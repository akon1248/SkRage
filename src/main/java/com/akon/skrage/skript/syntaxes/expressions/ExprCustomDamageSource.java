package com.akon.skrage.skript.syntaxes.expressions;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.akon.skrage.utils.damagesource.DamageSourceBuilder;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.jetbrains.annotations.Nullable;

@Description({"このアドオンのDamageSourceによるダメージだった場合、そのDamageSourceを取得します"})
public class ExprCustomDamageSource extends SimpleExpression<DamageSourceBuilder> {

	static {
		Skript.registerExpression(ExprCustomDamageSource.class, DamageSourceBuilder.class, ExpressionType.SIMPLE, "custom damage source");
	}

	@Nullable
	@Override
	protected DamageSourceBuilder[] get(Event e) {
		return new DamageSourceBuilder[]{DamageSourceBuilder.currentDamage};
	}

	@Override
	public boolean isSingle() {
		return true;
	}

	@Override
	public Class<? extends DamageSourceBuilder> getReturnType() {
		return DamageSourceBuilder.class;
	}

	@Override
	public String toString(@Nullable Event e, boolean debug) {
		return "";
	}

	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
		if (!ScriptLoader.isCurrentEvent(EntityDamageEvent.class) && !ScriptLoader.isCurrentEvent(EntityDeathEvent.class)) {
			Skript.error("この構文はon damageイベントもしくはon deathイベントでのみ使用可能です");
			return false;
		}
		return true;
	}
}
