package com.akon.skrage.syntaxes.ProtocolLib.PluginMessage;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import com.akon.skrage.event.PluginMessageEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

import org.jetbrains.annotations.Nullable;

@Description({"プラグインメッセージをクライアントから受け取ったときに呼び出されます"})
public class EvtPluginMessageReceiving extends SkriptEvent {

	static {
		if (Bukkit.getPluginManager().isPluginEnabled("ProtocolLib")) Skript.registerEvent("plugin message receiving", EvtPluginMessageReceiving.class, PluginMessageEvent.class, "plugin message receiv(e|ing)");
		EventValues.registerEventValue(PluginMessageEvent.class, Player.class, new Getter<Player, PluginMessageEvent>() {

			@Override
			@Nullable
			public Player get(PluginMessageEvent arg) {
				return arg.getPlayer();
			}

		}, 0);
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
