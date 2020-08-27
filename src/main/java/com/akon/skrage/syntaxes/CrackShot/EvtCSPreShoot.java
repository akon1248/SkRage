package com.akon.skrage.syntaxes.CrackShot;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import com.shampaggon.crackshot.events.WeaponPreShootEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

public class EvtCSPreShoot extends SkriptEvent {

	static {
		if (Bukkit.getPluginManager().isPluginEnabled("CrackShot")) {
			Skript.registerEvent("cs pre shoot", EvtCSPreShoot.class, WeaponPreShootEvent.class, "(crackshot|cs) pre shoot")
				.description("CrackShotの武器が発射される直前");
			EventValues.registerEventValue(WeaponPreShootEvent.class, Player.class, new Getter<Player, WeaponPreShootEvent>() {

				@Override
				@Nullable
				public Player get(WeaponPreShootEvent arg) {
					return arg.getPlayer();
				}

			}, 0);
			EventValues.registerEventValue(WeaponPreShootEvent.class, String.class, new Getter<String, WeaponPreShootEvent>() {

				@Override
				@Nullable
				public String get(WeaponPreShootEvent arg) {
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
		return e instanceof WeaponPreShootEvent;
	}

	@Override
	public boolean init(final Literal<?>[] args, int matchedPattern, ParseResult parseResult) {
		return true;
	}

}
