package com.akon.skrage.skript.syntaxes.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.akon.skrage.utils.customtarget.CustomTargetManager;
import com.w00tmast3r.skquery.elements.expressions.ExprInput;
import com.w00tmast3r.skquery.skript.LambdaCondition;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

@Description({"エンティティが敵対するmobをカスタマイズします"})
@Examples({"on spawn of iron golem:", "    set custom target of event-entity to [entity input is a player]"})
public class EffSetCustomTarget extends Effect {

	static {
		if (Bukkit.getPluginManager().isPluginEnabled("SkQuery")) {
			Skript.registerEffect(EffSetCustomTarget.class, "set custom target of %livingentity% to %predicate%");
		}
	}

	private Expression<LivingEntity> entity;
	private Expression<LambdaCondition> predicate;

	@Override
	protected void execute(Event e) {
		Optional.ofNullable(this.entity).map(expr -> expr.getSingle(e)).ifPresent(ent -> CustomTargetManager.setCustomTarget(ent, living -> Optional.ofNullable(this.predicate).map(expr -> expr.getSingle(e)).map(predicate -> {
			ExprInput.setInput(e, living);
			boolean result = predicate.check(e);
			ExprInput.removeInput(e);
			return result;
		}).orElse(false)));
	}

	@Override
	public String toString(@Nullable Event e, boolean debug) {
		return "";
	}

	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
		this.entity = (Expression<LivingEntity>)exprs[0];
		this.predicate = (Expression<LambdaCondition>)exprs[1];
		return true;
	}
}
