package com.akon.skrage.skript.syntaxes.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;

import java.util.Arrays;

@Description({"エンティティをデスポーンさせます"})
public class EffDespawn extends Effect {

	static {
		Skript.registerEffect(EffDespawn.class, "(despawn|remove) %entities%");
	}

	private Expression<Entity> entity;

	@Override
	protected void execute(Event e) {
		if (this.entity != null) {
			Arrays.stream(this.entity.getAll(e)).forEach(Entity::remove);
		}
	}

	@Override
	public String toString(Event e, boolean debug) {
		return "";
	}

	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
		this.entity = (Expression<Entity>)exprs[0];
		return true;
	}

}
