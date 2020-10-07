package com.akon.skrage.skript.syntaxes.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.util.Timespan;
import ch.njol.util.Kleenean;
import com.akon.skrage.utils.customstatuseffect.CSEManager;
import com.akon.skrage.utils.customstatuseffect.CSEType;
import com.akon.skrage.utils.customstatuseffect.CustomStatusEffect;
import com.akon.skrage.utils.exceptionsafe.ExceptionSafe;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Optional;

@Description({"CustomStatusEffectをエンティティに付与します"})
public class EffApplyCSE extends Effect {

	static {
		Skript.registerEffect(EffApplyCSE.class, "(add|apply) (cse|custom[ ]status[ ]effect) %customstatuseffecttype% [[[of] tier] %-number%] to %livingentities% [for %-timespan%] [ambient %-boolean%] [hide particle[s] %-boolean%] [mode (0¦default|1¦force|2¦vanilla)]");
	}

	private Expression<CSEType> type;
	private Expression<Number> tier;
	private Expression<LivingEntity> entity;
	private Expression<Timespan> duration;
	private Expression<Boolean> ambient;
	private Expression<Boolean> particle;
	private CSEManager.ApplyCondition mode;

	@Override
	protected void execute(Event e) {
		if (this.type != null && this.entity != null) {
			CustomStatusEffect effect = new CustomStatusEffect(this.type.getSingle(e), Optional.ofNullable(this.tier).map(expr -> expr.getSingle(e)).map(Number::intValue).orElse(0), Optional.ofNullable(this.duration).map(expr -> expr.getSingle(e)).map(Timespan::getTicks_i).map(Long::intValue).orElse(600), Optional.ofNullable(this.ambient).map(expr -> expr.getSingle(e)).orElse(false), !Optional.ofNullable(this.particle).map(expr -> expr.getSingle(e)).orElse(true));
			Arrays.stream(this.entity.getAll(e)).forEach(ExceptionSafe.consumer(entity -> CSEManager.apply(entity, effect, this.mode)));
		}
	}

	@Override
	public String toString(@Nullable Event e, boolean debug) {
		return "";
	}

	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
		this.type = (Expression<CSEType>) exprs[0];
		this.tier = (Expression<Number>)exprs[1];
		this.entity = (Expression<LivingEntity>)exprs[2];
		this.duration = (Expression<Timespan>)exprs[3];
		this.ambient = (Expression<Boolean>)exprs[4];
		this.particle = (Expression<Boolean>)exprs[5];
		this.mode = CSEManager.ApplyCondition.values()[parseResult.mark];
		return true;
	}
}
