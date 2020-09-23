package com.akon.skrage.skript.syntaxes.ProtocolLib.PluginMessage;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser;
import com.akon.skrage.event.PluginMessageEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;

@Description({"プラグインメッセージをサーバーが送信するときに呼び出されます"})
public class EvtPluginMessageSending extends SkriptEvent {

	static {
		Skript.registerEvent("plugin message sending", EvtPluginMessageSending.class, PluginMessageEvent.class, "plugin message send[ing]");
	}

	@Override
	public boolean init(Literal<?>[] args, int matchedPattern, SkriptParser.ParseResult parseResult) {
		return true;
	}

	@Override
	public boolean check(Event e) {
		return e instanceof PluginMessageEvent && ((PluginMessageEvent)e).isSending();
	}

	@Override
	public String toString(Event e, boolean debug) {
		return "";
	}

}
