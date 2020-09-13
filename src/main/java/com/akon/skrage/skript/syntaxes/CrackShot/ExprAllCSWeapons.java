package com.akon.skrage.skript.syntaxes.CrackShot;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.shampaggon.crackshot.CSDirector;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;

public class ExprAllCSWeapons extends SimpleExpression<String> {

	static {
		if (Bukkit.getPluginManager().isPluginEnabled("CrackShot")) Skript.registerExpression(ExprAllCSWeapons.class, String.class, ExpressionType.SIMPLE, "all (crackshot|cs) weapons");
	}

	@Override
	protected String[] get(Event e) {
		return ((CSDirector)Bukkit.getPluginManager().getPlugin("CrackShot")).parentlist.keySet().toArray(new String[0]);
	}

	@Override
	public boolean isSingle() {
		return false;
	}

	@Override
	public Class<? extends String> getReturnType() {
		return String.class;
	}

	@Override
	public String toString(Event e, boolean debug) {
		return "";
	}

	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
		return true;
	}

}
