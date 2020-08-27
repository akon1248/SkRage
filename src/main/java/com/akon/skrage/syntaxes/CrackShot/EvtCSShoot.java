package com.akon.skrage.syntaxes.CrackShot;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import com.shampaggon.crackshot.events.WeaponShootEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

public class EvtCSShoot extends SkriptEvent {

	static {
		if (Bukkit.getPluginManager().isPluginEnabled("CrackShot")) {
			Skript.registerEvent("cs shoot", EvtCSShoot.class, WeaponShootEvent.class, "(crackshot|cs) shoot")
				.description("CrackShotの武器が発射されるとき");
			EventValues.registerEventValue(WeaponShootEvent.class, Player.class, new Getter<Player, WeaponShootEvent>() {

				@Override
				@Nullable
				public Player get(WeaponShootEvent arg) {
					return arg.getPlayer();
				}

			}, 0);
			EventValues.registerEventValue(WeaponShootEvent.class, String.class, new Getter<String, WeaponShootEvent>() {

				@Override
				@Nullable
				public String get(WeaponShootEvent arg) {
					return arg.getWeaponTitle();
				}

			}, 0);
			EventValues.registerEventValue(WeaponShootEvent.class, Entity.class, new Getter<Entity, WeaponShootEvent>() {

				@Override
				@Nullable
				public Entity get(WeaponShootEvent arg) {
					return arg.getProjectile();
				}

			}, 0);
			EventValues.registerEventValue(WeaponShootEvent.class, Projectile.class, new Getter<Projectile, WeaponShootEvent>() {

				@Override
				@Nullable
				public Projectile get(WeaponShootEvent arg) {
					return arg.getProjectile() instanceof Projectile ? (Projectile)arg.getProjectile() : null;
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
		return e instanceof WeaponShootEvent;
	}

	@Override
	public boolean init(final Literal<?>[] args, int matchedPattern, ParseResult parseResult) {
		return true;
	}

}
