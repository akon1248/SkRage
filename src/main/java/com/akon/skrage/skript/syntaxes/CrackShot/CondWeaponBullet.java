package com.akon.skrage.skript.syntaxes.CrackShot;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.TNTPrimed;
import org.bukkit.event.Event;

@Description({"EntityがCrackShotの武器によって放たれたものかどうかを判定します"})
public class CondWeaponBullet extends Condition {

	private Expression<Entity> bullet;
	
	static {
		if (Bukkit.getPluginManager().isPluginEnabled("CrackShot")) Skript.registerCondition(CondWeaponBullet.class, "%entity% is [(crackshot|cs)] weapon bullet");
	}

	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayd, ParseResult parseResult) {
		this.bullet = (Expression<Entity>)exprs[0];
		return true;
	}

	@Override
	public String toString(Event e, boolean debug) {
		return "";
	}

	@Override
	public boolean check(Event e) {
		if (this.bullet != null) {
			if (this.bullet.getSingle(e) instanceof Projectile) {
				return this.bullet.getSingle(e).hasMetadata("projParentNode");
			}
			if (this.bullet.getSingle(e) instanceof TNTPrimed)
				return this.bullet.getSingle(e).hasMetadata("CS_potex");
		}
		return false;
	}

}
