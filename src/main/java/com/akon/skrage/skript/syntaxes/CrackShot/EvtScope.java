package com.akon.skrage.skript.syntaxes.CrackShot;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import com.shampaggon.crackshot.events.WeaponScopeEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

public class EvtScope extends SkriptEvent {

	static {
		if (Bukkit.getPluginManager().isPluginEnabled("CrackShot")) {
			Skript.registerEvent("scope", EvtScope.class, WeaponScopeEvent.class, "[(crackshot|cs)] scope")
				.description("CrackShotの武器のスコープを覗いたとき");
			EventValues.registerEventValue(WeaponScopeEvent.class, String.class, new Getter<String, WeaponScopeEvent>() {

				@Override
				@Nullable
				public String get(WeaponScopeEvent arg) {
					return arg.getWeaponTitle();
				}

			}, 0);
			EventValues.registerEventValue(WeaponScopeEvent.class, Player.class, new Getter<Player, WeaponScopeEvent>() {

				@Override
				@Nullable
				public Player get(WeaponScopeEvent arg) {
					return arg.getPlayer();
				}

			}, 0);
		}
	}
	
	@Override
	public String toString(Event e, boolean debug) {
		return "";
	}

	@Override
	public boolean check(Event e) {
		return e instanceof WeaponScopeEvent && ((WeaponScopeEvent)e).isZoomIn();
	}

	@Override
	public boolean init(final Literal<?>[] args, int matchedPattern, ParseResult parseResult) {
		return true;
	}

}
