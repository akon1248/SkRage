package com.akon.skrage.skript.syntaxes.ProtocolLib.AnvilGUI;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.akon.skrage.utils.anvilgui.AnvilGUI;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;

import java.util.Optional;

@Description({"最後に開かれたAnvilGUI"})
public class ExprLastOpenedAnvilGUI extends SimpleExpression<AnvilGUI> {

	static {
		Skript.registerExpression(ExprLastOpenedAnvilGUI.class, AnvilGUI.class, ExpressionType.SIMPLE, "last opened anvil[ ]gui");
	}

	@Override
	protected AnvilGUI[] get(Event e) {
		return Optional.ofNullable(EffOpenAnvilGUI.getLastOpen()).map(anvilGUI -> new AnvilGUI[]{anvilGUI}).orElse(null);
	}

	@Override
	public boolean isSingle() {
		return true;
	}

	@Override
	public Class<? extends AnvilGUI> getReturnType() {
		return AnvilGUI.class;
	}

	@Override
	public String toString(Event e, boolean debug) {
		return "";
	}

	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
		return true;
	}

}
