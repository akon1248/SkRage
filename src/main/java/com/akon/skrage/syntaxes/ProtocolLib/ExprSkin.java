package com.akon.skrage.syntaxes.ProtocolLib;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.akon.skrage.utils.skin.Skin;
import com.comphenix.protocol.wrappers.WrappedGameProfile;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

public class ExprSkin extends SimpleExpression<Skin> {

	private Expression<Player> player;

	static {
		if (Bukkit.getPluginManager().isPluginEnabled("ProtocolLib")) Skript.registerExpression(ExprSkin.class, Skin.class, ExpressionType.COMBINED, "%player%'s skin", "skin of %player%");
	}

	@Override
	protected Skin[] get(Event e) {
		if (this.player != null) {
			return new Skin[]{Skin.fromGameProfile(WrappedGameProfile.fromPlayer(this.player.getSingle(e)))};
		}
		return null;
	}

	@Override
	public boolean isSingle() {
		return true;
	}

	@Override
	public Class<? extends Skin> getReturnType() {
		return Skin.class;
	}

	@Override
	public String toString(Event e, boolean debug) {
		return "";
	}

	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
		this.player = (Expression<Player>)exprs[0];
		return true;
	}

}
