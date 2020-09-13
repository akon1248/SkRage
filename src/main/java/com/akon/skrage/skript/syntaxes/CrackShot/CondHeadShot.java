package com.akon.skrage.skript.syntaxes.CrackShot;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import com.shampaggon.crackshot.events.WeaponDamageEntityEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;

@Description({"ヘッドショットかどうかを判定します"})
public class CondHeadShot extends Condition {

	static {
		if (Bukkit.getPluginManager().isPluginEnabled("CrackShot")) Skript.registerCondition(CondHeadShot.class, "event was [a] head[ ]shot");
	}

	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayd, ParseResult parseResult) {
		if (ScriptLoader.isCurrentEvent(WeaponDamageEntityEvent.class)) {
			return true;
		}
		Skript.error("CrackShot Head Shot conditionはCrackShot Damageイベントでのみ使用可能です");
		return false;
	}

	@Override
	public String toString(Event e, boolean debug) {
		return "";
	}

	@Override
	public boolean check(Event e) {
		if (e instanceof WeaponDamageEntityEvent) {
			return ((WeaponDamageEntityEvent)e).isHeadshot();
		}
		return false;
	}

}
