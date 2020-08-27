package com.akon.skrage.syntaxes.LibsDisguises;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.akon.skrage.utils.skin.Skin;
import me.libraryaddict.disguise.disguisetypes.Disguise;
import me.libraryaddict.disguise.disguisetypes.PlayerDisguise;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;

import java.util.UUID;

@Description({"スキンからプレイヤーのDisguiseを取得します", "Skellettが導入されていないとLibsDisguiseの機能を利用することはできません"})
public class ExprCustomPlayerDisguise extends SimpleExpression<Disguise> {

	static {
		if (Bukkit.getPluginManager().isPluginEnabled("LibsDisguises")) Skript.registerExpression(ExprCustomPlayerDisguise.class, Disguise.class, ExpressionType.COMBINED, "[a] [new] player disguise named %string% with skin %skin%");
	}

	private Expression<String> name;
	private Expression<Skin> skin;

	@Override
	protected Disguise[] get(Event e) {
		if (this.name != null && this.skin != null) {
			return new Disguise[]{new PlayerDisguise(this.skin.getSingle(e).toGameProfile(UUID.randomUUID(), this.name.getSingle(e)))};
		} else {
			return null;
		}
	}

	@Override
	public boolean isSingle() {
		return true;
	}

	@Override
	public Class<? extends Disguise> getReturnType() {
		return Disguise.class;
	}

	@Override
	public String toString(Event e, boolean debug) {
		return "";
	}

	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
		this.name = (Expression<String>)exprs[0];
		this.skin = (Expression<Skin>)exprs[1];
		return true;
	}

}
