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
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;
import org.bukkit.event.entity.AreaEffectCloudApplyEvent;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Description({"AoE Cloud EffectイベントでArea Effect Cloudをポーション効果を受けたエンティティのリスト"})
public class ExprAffectedEntities extends SimpleExpression<LivingEntity>  {

	static {
		Skript.registerExpression(ExprAffectedEntities.class, LivingEntity.class, ExpressionType.SIMPLE, "affected entities");
	}

	@Nullable
	@Override
	protected LivingEntity[] get(Event e) {
		return ((AreaEffectCloudApplyEvent)e).getAffectedEntities().toArray(new LivingEntity[0]);
	}

	@Override
	public boolean isSingle() {
		return false;
	}

	@Override
	public Class<? extends LivingEntity> getReturnType() {
		return LivingEntity.class;
	}

	@Override
	public String toString(@Nullable Event e, boolean debug) {
		return "";
	}

	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
		if (!ScriptLoader.isCurrentEvent(AreaEffectCloudApplyEvent.class)) {
			Skript.error("Affected EntitiesはAoE Cloud Effectイベントでのみ使用可能です");
			return false;
		}
		return true;
	}

	@Nullable
	@Override
	public Class<?>[] acceptChange(Changer.ChangeMode mode) {
		switch (mode) {
			case ADD:
			case SET:
			case REMOVE:
			case REMOVE_ALL:
			case DELETE:
				return CollectionUtils.array(LivingEntity.class);
			default:
				return null;
		}
	}

	@Override
	public void change(Event e, @Nullable Object[] delta, Changer.ChangeMode mode) {
		List<LivingEntity> affectedEntities = ((AreaEffectCloudApplyEvent)e).getAffectedEntities();
		switch (mode) {
			case SET:
				affectedEntities.clear();
			case ADD:
				Optional.ofNullable(delta).ifPresent(arr -> Arrays.stream(arr).filter(LivingEntity.class::isInstance).map(LivingEntity.class::cast).forEach(affectedEntities::add));
				break;
			case REMOVE:
			case REMOVE_ALL:
				Optional.ofNullable(delta).map(Arrays::asList).ifPresent(list -> affectedEntities.removeIf(list::contains));
				break;
			case DELETE:
				affectedEntities.clear();
				break;
		}
	}
}
