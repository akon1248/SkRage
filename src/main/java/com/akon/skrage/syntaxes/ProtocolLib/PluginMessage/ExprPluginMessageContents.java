package com.akon.skrage.syntaxes.ProtocolLib.PluginMessage;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import com.akon.skrage.event.PluginMessageEvent;
import com.google.common.collect.Lists;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.event.Event;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Description({"プラグインメッセージの内容を取得します"})
public class ExprPluginMessageContents extends SimpleExpression<Number> {

	static {
		Skript.registerExpression(ExprPluginMessageContents.class, Number.class, ExpressionType.COMBINED, "plugin message contents");
	}

	@Override
	protected Number[] get(Event e) {
		return ArrayUtils.toObject(((PluginMessageEvent)e).getContents());
	}

	@Override
	public boolean isSingle() {
		return false;
	}

	@Override
	public Class<? extends Number> getReturnType() {
		return Number.class;
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
		Skript.error("Plugin Message ContentsはPlugin Messageイベントでのみ使用可能です");
		return false;
	}

	@Override
	public Class<?>[] acceptChange(Changer.ChangeMode mode) {
		if (mode == Changer.ChangeMode.ADD || mode == Changer.ChangeMode.DELETE || mode == Changer.ChangeMode.REMOVE || mode == Changer.ChangeMode.REMOVE_ALL || mode == Changer.ChangeMode.SET) {
			return CollectionUtils.array(Number.class);
		}
		return null;
	}

	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode) {
		List<Byte> byteList = Lists.newArrayList(Arrays.asList(ArrayUtils.toObject(((PluginMessageEvent)e).getContents())));
		List<Byte> deltaList = Arrays.stream(delta).map(obj -> ((Number)obj).byteValue()).collect(Collectors.toList());
		switch (mode) {
			case ADD:
				byteList.addAll(deltaList);
				break;
			case DELETE:
			case REMOVE_ALL:
				byteList.clear();
			case REMOVE:
				deltaList.forEach(byteList::remove);
			case SET:
				byteList = deltaList;
		}
		((PluginMessageEvent)e).setContents(ArrayUtils.toPrimitive(byteList.toArray(new Byte[0])));
	}

}
