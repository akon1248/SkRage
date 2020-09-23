package com.akon.skrage.skript.syntaxes.ProtocolLib.AnvilGUI;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.akon.skrage.utils.anvilgui.AnvilGUI;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;

@Description({"金床のGUIを閉じます"})
public class EffCloseAnvilGUI extends Effect {

	static {
		Skript.registerEffect(EffCloseAnvilGUI.class, "close anvil[ ]gui %anvilinv%");
	}

	private Expression<AnvilGUI> anvilGUI;

	@Override
	protected void execute(Event e) {
		if (this.anvilGUI != null && this.anvilGUI.getSingle(e).getViewer() != null) {
			this.anvilGUI.getSingle(e).close();
		}
	}

	@Override
	public String toString(Event e, boolean debug) {
		return "";
	}

	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
		this.anvilGUI = (Expression<AnvilGUI>)exprs[0];
		return true;
	}

}
