package com.akon.skrage.skript.syntaxes.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import com.akon.skrage.utils.customstatuseffect.CSEManager;
import com.akon.skrage.utils.customstatuseffect.CSEType;
import com.akon.skrage.utils.customstatuseffect.CustomStatusEffect;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Optional;

@Description({"エンティティに付与されているすべてのCustomStatusEffect"})
public class ExprActiveCSEs extends SimpleExpression<CustomStatusEffect> {

	static {
		Skript.registerExpression(ExprActiveCSEs.class, CustomStatusEffect.class, ExpressionType.COMBINED, "[all] [active] (cse|custom[ ]status[ ]effect)[s] of %livingentity%");
	}

	private Expression<LivingEntity> entity;

	@Nullable
	@Override
	protected CustomStatusEffect[] get(Event e) {
		return Optional.ofNullable(this.entity).map(expr -> expr.getSingle(e)).map(CSEManager::getActiveCSEs).map(Map::values).map(values -> values.toArray(new CustomStatusEffect[0])).orElse(null);
	}

	@Override
	public boolean isSingle() {
		return false;
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
		this.entity = (Expression<LivingEntity>)exprs[0];
		return true;
	}

	@Nullable
	@Override
	public Class<?>[] acceptChange(Changer.ChangeMode mode) {
		return mode == Changer.ChangeMode.DELETE || mode == Changer.ChangeMode.REMOVE ? CollectionUtils.array(CSEType.class) : null;
	}

	@Override
	public void change(Event e, @Nullable Object[] delta, Changer.ChangeMode mode) {
		Optional.ofNullable(this.entity).map(expr -> expr.getSingle(e)).ifPresent(ent -> {
			if (mode == Changer.ChangeMode.DELETE) {
				CSEManager.getActiveCSEs(ent).keySet().forEach(type -> CSEManager.remove(ent, type));
			} else if (mode == Changer.ChangeMode.REMOVE) {
				Optional.ofNullable(delta).filter(arr -> arr[0] instanceof CSEType).ifPresent(arr -> CSEManager.remove(ent, (CSEType)arr[0]));
			}
		});
	}
}
