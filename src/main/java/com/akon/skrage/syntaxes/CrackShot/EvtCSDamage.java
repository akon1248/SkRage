package com.akon.skrage.syntaxes.CrackShot;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import com.shampaggon.crackshot.events.WeaponDamageEntityEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

public class EvtCSDamage extends SkriptEvent {

	static {
		if (Bukkit.getPluginManager().isPluginEnabled("CrackShot")) {
			Skript.registerEvent("cs damage", EvtCSDamage.class, WeaponDamageEntityEvent.class, "(crackshot|cs) damage")
				.description("CrackShotの武器によってEntityがダメージを受けたとき");
			EventValues.registerEventValue(WeaponDamageEntityEvent.class, String.class, new Getter<String, WeaponDamageEntityEvent>() {

				@Override
				@Nullable
				public String get(WeaponDamageEntityEvent arg) {
					return arg.getWeaponTitle();
				}

			}, 0);
			EventValues.registerEventValue(WeaponDamageEntityEvent.class, Player.class, new Getter<Player, WeaponDamageEntityEvent>() {

				@Override
				@Nullable
				public Player get(WeaponDamageEntityEvent arg) {
					return arg.getPlayer();
				}

			}, 0);
			EventValues.registerEventValue(WeaponDamageEntityEvent.class, Entity.class, new Getter<Entity, WeaponDamageEntityEvent>() {

				@Override
				@Nullable
				public Entity get(WeaponDamageEntityEvent arg) {
					return arg.getVictim();
				}

			}, 0);
			EventValues.registerEventValue(WeaponDamageEntityEvent.class, Projectile.class, new Getter<Projectile, WeaponDamageEntityEvent>() {

				@Override
				@Nullable
				public Projectile get(WeaponDamageEntityEvent arg) {
					return arg.getDamager() instanceof Projectile ? (Projectile) arg.getDamager() : null;
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
		return e instanceof WeaponDamageEntityEvent;
	}

	@Override
	public boolean init(final Literal<?>[] args, int matchedPattern, ParseResult parseResult) {
		return true;
	}

}
