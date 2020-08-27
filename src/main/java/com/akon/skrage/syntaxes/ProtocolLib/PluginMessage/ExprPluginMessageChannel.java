package com.akon.skrage.syntaxes.ProtocolLib.PluginMessage;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.akon.skrage.event.PluginMessageEvent;
import org.bukkit.event.Event;

@Description({"プラグインメッセージのチャンネルを取得します"})
public class ExprPluginMessageChannel extends SimpleExpression<String> {

	static {
		Skript.registerExpression(ExprPluginMessageChannel.class, String.class, ExpressionType.COMBINED, "plugin message channel");
	}

	@Override
	protected String[] get(Event e) {
		return new String[]{((PluginMessageEvent)e).getChannel()};
	}

	@Override
	public boolean isSingle() {
		return true;
	}

	@Override
	public Class<? extends String> getReturnType() {
		return String.class;
	}

	@Override
	public String toString(Event e, boolean debug) {
		return "";
	}

	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
		if (ScriptLoader.isCurrentEvent(PluginMessageEvent.class)) {
			return true;
		}
		Skript.error("Plugin Message ChannelはPlugin Messageイベントでのみ使用可能です");
		return false;
	}

}
