package com.akon.skrage.syntaxes.CrackShot;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import com.shampaggon.crackshot.events.WeaponPrepareShootEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

public class EvtCSPrepareShoot extends SkriptEvent {

	static {
		if (Bukkit.getPluginManager().isPluginEnabled("CrackShot")) {
			Skript.registerEvent("cs prepare shoot", EvtCSPrepareShoot.class, WeaponPrepareShootEvent.class, "(crackshot|cs) prepare shoot")
				.description("CrackShotの武器が発射されようとしたとき");
			EventValues.registerEventValue(WeaponPrepareShootEvent.class, Player.class, new Getter<Player, WeaponPrepareShootEvent>() {

				@Override
				@Nullable
				public Player get(WeaponPrepareShootEvent arg) {
					return arg.getPlayer();
				}

			}, 0);
			EventValues.registerEventValue(WeaponPrepareShootEvent.class, String.class, new Getter<String, WeaponPrepareShootEvent>() {

				@Override
				@Nullable
				public String get(WeaponPrepareShootEvent arg) {
					return arg.getWeaponTitle();
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
		return e instanceof WeaponPrepareShootEvent;
	}

	@Override
	public boolean init(final Literal<?>[] args, int matchedPattern, ParseResult parseResult) {
		return true;
	}

}
