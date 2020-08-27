package com.akon.skrage.syntaxes.CrackShot;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import com.shampaggon.crackshot.events.WeaponScopeEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;

public class EvtUnscope extends SkriptEvent {

	static {
		if (Bukkit.getPluginManager().isPluginEnabled("CrackShot")) Skript.registerEvent("unscope", EvtUnscope.class, WeaponScopeEvent.class, "[(crackshot|cs)] unscope")
			.description("CrackShotの武器でスコープ状態を解除したとき");
	}
	
	@Override
	public String toString(Event e, boolean debug) {
		return "";
	}

	@Override
	public boolean check(Event e) {
		return e instanceof WeaponScopeEvent && !((WeaponScopeEvent)e).isZoomIn();
	}

	@Override
	public boolean init(final Literal<?>[] args, int matchedPattern, ParseResult parseResult) {
		return true;
	}

}
