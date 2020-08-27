package com.akon.skrage.syntaxes.events;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Zombie;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityCombustByBlockEvent;
import org.bukkit.event.entity.EntityCombustByEntityEvent;
import org.bukkit.event.entity.EntityCombustEvent;

public class EvtCombustBySunlight extends SkriptEvent {

	static {
		Skript.registerEvent("combust by sunlight", EvtCombustBySunlight.class, EntityCombustEvent.class, "combust[ing] by sunlight")
			.description("ゾンビやスケルトンなどが日光によって燃えたとき");
	}

	@Override
	public boolean init(Literal<?>[] args, int matchedPattern, SkriptParser.ParseResult parseResult) {
		return true;
	}

	@Override
	public boolean check(Event e) {
		return !(e instanceof EntityCombustByBlockEvent) && !(e instanceof EntityCombustByEntityEvent) && (((EntityCombustEvent)e).getEntity() instanceof Zombie || ((EntityCombustEvent)e).getEntity() instanceof Skeleton);
	}

	@Override
	public String toString(Event e, boolean debug) {
		return "";
	}

}
