package com.akon.skrage.skript.syntaxes.ProtocolLib.PluginMessage;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser;
import com.akon.skrage.event.PluginMessageEvent;
import org.bukkit.event.Event;

public class EvtPluginMessageReceiving extends SkriptEvent {

	static {
		Skript.registerEvent("plugin message receiving", EvtPluginMessageReceiving.class, PluginMessageEvent.class, "plugin message receiv(e|ing)").description("プラグインメッセージをクライアントから受け取ったときに呼び出されます");
	}

	@Override
	public boolean init(Literal<?>[] args, int matchedPattern, SkriptParser.ParseResult parseResult) {
		return true;
	}

	@Override
	public boolean check(Event e) {
		return e instanceof PluginMessageEvent && !((PluginMessageEvent)e).isSending();
	}

	@Override
	public String toString(Event e, boolean debug) {
		return "";
	}

}
