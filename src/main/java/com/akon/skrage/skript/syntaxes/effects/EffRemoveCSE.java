package com.akon.skrage.skript.syntaxes.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.akon.skrage.utils.customstatuseffect.CSEManager;
import com.akon.skrage.utils.customstatuseffect.CSEType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

@Description({"CustomStatusEffectをエンティティから削除します"})
public class EffRemoveCSE extends Effect {

	static {
		Skript.registerEffect(EffRemoveCSE.class, "(clear|remove) (cse|custom[ ]status[ ]effect) %customstatuseffecttypes% from %entities%", "(clear|remove) [all] [active] (cse|custom[ ]status[ ]effect)[s] from %entities%");
	}

	private Expression<CSEType> type;
	private Expression<LivingEntity> entity;
	private boolean removeAll;

	@Override
	protected void execute(Event e) {
		Optional.ofNullable(this.entity).map(expr -> expr.getSingle(e)).ifPresent(entity -> {
			if (this.removeAll) {
				CSEManager.getActiveCSEs(entity).keySet().forEach(type -> CSEManager.remove(entity, type));
			} else if (this.type != null) {
				Stream<CSEType> stream = Arrays.stream(this.type.getAll(e));
				Arrays.stream(this.entity.getAll(e)).forEach(ent -> stream.forEach(cseType -> CSEManager.remove(ent, cseType)));
			}
		});
	}

	@Override
	public String toString(@Nullable Event e, boolean debug) {
		return "";
	}

	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
		if (matchedPattern == 0) {
			this.type = (Expression<CSEType>)exprs[0];
			this.entity = (Expression<LivingEntity>)exprs[1];
		} else {
			this.entity = (Expression<LivingEntity>)exprs[0];
			this.removeAll = true;
		}
		return true;
	}
}
