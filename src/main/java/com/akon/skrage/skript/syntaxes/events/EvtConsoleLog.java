package com.akon.skrage.skript.syntaxes.events;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import com.akon.skrage.event.ConsoleLogEvent;
import org.apache.logging.log4j.spi.StandardLevel;
import org.bukkit.event.Event;

public class EvtConsoleLog extends SkriptEvent {

	static {
		Skript.registerEvent("console log", EvtConsoleLog.class, ConsoleLogEvent.class, "console log")
			.description("コンソールにログが出力されたとき");
		EventValues.registerEventValue(ConsoleLogEvent.class, String.class, new Getter<String, ConsoleLogEvent>() {

			@Override
			public String get(ConsoleLogEvent arg) {
				return arg.getMessage();
			}

		}, 0);
		EventValues.registerEventValue(ConsoleLogEvent.class, StandardLevel.class, new Getter<StandardLevel, ConsoleLogEvent>() {

			@Override
			public StandardLevel get(ConsoleLogEvent arg) {
				return arg.getLevel();
			}

		}, 0);
	}

	@Override
	public boolean init(Literal<?>[] args, int matchedPattern, SkriptParser.ParseResult parseResult) {
		return true;
	}

	@Override
	public boolean check(Event e) {
		return e instanceof ConsoleLogEvent;
	}

	@Override
	public String toString(Event e, boolean debug) {
		return "";
	}

}
