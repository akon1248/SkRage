package com.akon.skrage.skript.syntaxes.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.akon.skrage.utils.customtarget.CustomTargetManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

@Description({"エンティティの敵対するmobをデフォルトに戻します"})
public class EffResetCustomTarget extends Effect {

	static {
		if (Bukkit.getPluginManager().isPluginEnabled("SkQuery")) {
			Skript.registerEffect(EffResetCustomTarget.class, "reset custom target of %livingentity%");
		}
	}

	private Expression<LivingEntity> entity;

	@Override
	protected void execute(Event e) {
		Optional.ofNullable(this.entity).map(expr -> expr.getSingle(e)).ifPresent(CustomTargetManager::resetCustomTarget);
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
}
