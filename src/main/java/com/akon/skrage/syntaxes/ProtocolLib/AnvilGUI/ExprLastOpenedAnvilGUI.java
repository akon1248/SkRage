package com.akon.skrage.syntaxes.ProtocolLib.AnvilGUI;

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

@Description({"最後に開かれた金床のGUI"})
public class ExprLastOpenedAnvilGUI extends SimpleExpression<AnvilGUI> {

	static {
		if (Bukkit.getPluginManager().isPluginEnabled("ProtocolLib")) Skript.registerExpression(ExprLastOpenedAnvilGUI.class, AnvilGUI.class, ExpressionType.SIMPLE, "last opened anvil[ ]gui");
	}

	@Override
	protected AnvilGUI[] get(Event e) {
		AnvilGUI anvilGUI = EffOpenAnvilGUI.getLastOpen();
		return anvilGUI == null ? null : new AnvilGUI[]{anvilGUI};
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
