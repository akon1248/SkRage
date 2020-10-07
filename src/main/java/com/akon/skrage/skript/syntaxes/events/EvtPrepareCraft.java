package com.akon.skrage.skript.syntaxes.events;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser;
import org.bukkit.event.Event;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.jetbrains.annotations.Nullable;

public class EvtPrepareCraft extends SkriptEvent {

	static {
		Skript.registerEvent("prepare craft", EvtPrepareCraft.class, PrepareItemCraftEvent.class, "pre[pare] craft[ing]");
	}

	@Override
	public boolean init(Literal<?>[] args, int matchedPattern, SkriptParser.ParseResult parseResult) {
		return true;
	}

	@Override
	public boolean check(Event e) {
		return e instanceof PrepareItemCraftEvent;
	}

	@Override
	public String toString(@Nullable Event e, boolean debug) {
		return "";
	}
}
