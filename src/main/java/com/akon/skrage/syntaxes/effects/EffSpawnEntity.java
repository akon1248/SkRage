package com.akon.skrage.syntaxes.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.akon.skrage.utils.NMSUtil;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;

@Description({"new entity構文で作成したEntityをスポーンさせることができます","また、一度死亡したEntityをリスポーンさせることも可能です"})
public class EffSpawnEntity extends Effect {

	static {
		Skript.registerEffect(EffSpawnEntity.class, "[re]spawn [(a|an)] %entity% at %location%");
	}

	private Expression<Entity> entity;
	private Expression<Location> loc;

	@Override
	protected void execute(Event e) {
		if (this.entity != null && this.loc != null) {
			NMSUtil.respawnEntity(this.entity.getSingle(e), this.loc.getSingle(e));
		}
	}

	@Override
	public String toString(Event e, boolean debug) {
		return "";
	}

	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
		this.entity = (Expression<Entity>)exprs[0];
		this.loc = (Expression<Location>)exprs[1];
		return true;
	}

}
