package com.akon.skrage.skript.syntaxes.CrackShot;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import com.shampaggon.crackshot.events.WeaponReloadCompleteEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

public class EvtReloadComplete extends SkriptEvent {

	static {
		if (Bukkit.getPluginManager().isPluginEnabled("CrackShot")) {
			Skript.registerEvent("reload complete", EvtReloadComplete.class, WeaponReloadCompleteEvent.class, "[(crackshot|cs)] reload complete")
				.description("CrackShotの武器のリロードが完了したとき");
			EventValues.registerEventValue(WeaponReloadCompleteEvent.class, String.class, new Getter<String, WeaponReloadCompleteEvent>() {

				@Override
				@Nullable
				public String get(WeaponReloadCompleteEvent arg) {
					return arg.getWeaponTitle();
				}

			}, 0);
			EventValues.registerEventValue(WeaponReloadCompleteEvent.class, Player.class, new Getter<Player, WeaponReloadCompleteEvent>() {

				@Override
				@Nullable
				public Player get(WeaponReloadCompleteEvent arg) {
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
		return e instanceof WeaponReloadCompleteEvent;
	}

	@Override
	public boolean init(final Literal<?>[] args, int matchedPattern, ParseResult parseResult) {
		return true;
	}

}
