package com.akon.skrage.skript.syntaxes.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

@Description({"プレイヤーの時間を変更します"})
public class EffSetPlayerTime extends Effect {

	static {
		Skript.registerEffect(EffSetPlayerTime.class, "set (client|player) time of %player% to %number% relative %boolean%", "set %player%'s (client|player) time to %number% relative %boolean%");
	}

	private Expression<Player> player;
	private Expression<Number> num;
	private Expression<Boolean> relative;

	@Override
	protected void execute(Event e) {
		if (this.player != null && this.num != null && this.relative != null) {
			this.player.getSingle(e).setPlayerTime(this.num.getSingle(e).longValue(), this.relative.getSingle(e));
		}
	}

	@Override
	public String toString(@Nullable Event e, boolean debug) {
		return "";
	}

	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
		this.player = (Expression<Player>)exprs[0];
		this.num = (Expression<Number>)exprs[1];
		this.relative = (Expression<Boolean>)exprs[2];
		return true;
	}
}
