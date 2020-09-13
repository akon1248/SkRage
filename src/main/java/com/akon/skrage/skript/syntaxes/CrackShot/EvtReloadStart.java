package com.akon.skrage.skript.syntaxes.CrackShot;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import com.shampaggon.crackshot.events.WeaponReloadEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

public class EvtReloadStart extends SkriptEvent {

	static {
		if (Bukkit.getPluginManager().isPluginEnabled("CrackShot")) {
			Skript.registerEvent("reload start", EvtReloadStart.class, WeaponReloadEvent.class, "[(crackshot|cs)] reload [start]")
				.description("CrackShotの武器のリロードを開始したとき");
			EventValues.registerEventValue(WeaponReloadEvent.class, String.class, new Getter<String, WeaponReloadEvent>() {

				@Override
				@Nullable
				public String get(WeaponReloadEvent arg) {
					return arg.getWeaponTitle();
				}

			}, 0);
			EventValues.registerEventValue(WeaponReloadEvent.class, Player.class, new Getter<Player, WeaponReloadEvent>() {

				@Override
				@Nullable
				public Player get(WeaponReloadEvent arg) {
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
		return e instanceof WeaponReloadEvent;
	}

	@Override
	public boolean init(final Literal<?>[] args, int matchedPattern, ParseResult parseResult) {
		return true;
	}

}
