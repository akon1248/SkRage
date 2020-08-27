package com.akon.skrage.syntaxes.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.akon.skrage.utils.NMSUtil;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;

public class EffBroadcastDeathMessage extends Effect {

	static {
		Skript.registerEffect(EffBroadcastDeathMessage.class, "broadcast death message of %livingentity%");
	}

	private Expression<LivingEntity> entity;

	@Override
	protected void execute(Event e) {
		if (this.entity != null) {
			NMSUtil.broadcastDeathMessage(this.entity.getSingle(e));
		}
	}

	@Override
	public String toString(Event e, boolean debug) {
		return "";
	}

	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
		this.entity = (Expression<LivingEntity>)exprs[0];
		return true;
	}

}
