package com.akon.skrage.skript.syntaxes.ProtocolLib.AnvilGUI;

import ch.njol.skript.Skript;
import ch.njol.skript.aliases.ItemType;
import ch.njol.skript.doc.Description;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.akon.skrage.utils.anvilgui.AnvilGUI;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

@Description({"プレイヤーにAnvilGUIを表示します"})
public class EffOpenAnvilGUI extends Effect {

	static {
		Skript.registerEffect(EffOpenAnvilGUI.class, "open anvil[ ]gui named %string% with icon %itemtype% text %string% (to|for) %player%");
	}

	@Getter
	private static AnvilGUI lastOpen;

	private Expression<String> name;
	private Expression<ItemType> item;
	private Expression<String> text;
	private Expression<Player> player;

	@Override
	protected void execute(Event e) {
		if (this.name != null && this.item != null && this.text != null && this.player != null) {
			lastOpen = null;
			AnvilGUI anvilGUI = new AnvilGUI(this.name.getSingle(e), this.item.getSingle(e).getRandom(), this.text.getSingle(e));
			if (anvilGUI.open(this.player.getSingle(e))) {
				lastOpen = anvilGUI;
			}
		}
	}

	@Override
	public String toString(Event e, boolean debug) {
		return "";
	}

	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
		this.name = (Expression<String>)exprs[0];
		this.item = (Expression<ItemType>)exprs[1];
		this.text = (Expression<String>)exprs[2];
		this.player = (Expression<Player>)exprs[3];
		return true;
	}

}
