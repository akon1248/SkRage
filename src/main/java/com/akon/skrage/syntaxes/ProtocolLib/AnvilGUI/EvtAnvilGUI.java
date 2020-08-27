package com.akon.skrage.syntaxes.ProtocolLib.AnvilGUI;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import com.akon.skrage.utils.anvilgui.*;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

public class EvtAnvilGUI extends SkriptEvent {

	static {
		Skript.registerEvent("anvil gui close", EvtAnvilGUI.class, AnvilGUICloseEvent.class, "anvil[ ]gui close")
			.description("金床のGUIが閉じられたとき");
		Skript.registerEvent("anvil gui done", EvtAnvilGUI.class, AnvilGUIDoneEvent.class, "anvil[ ]gui done")
			.description("金床のGUIにプレイヤーがテキストを入力し終わったとき");
		Skript.registerEvent("anvil gui open", EvtAnvilGUI.class, AnvilGUIOpenEvent.class, "anvil[ ]gui open")
			.description("金床のGUIが開かれるとき");
		EventValues.registerEventValue(AnvilGUIEvent.class, AnvilGUI.class, new Getter<AnvilGUI, AnvilGUIEvent>() {

			@Nullable
			@Override
			public AnvilGUI get(AnvilGUIEvent arg) {
				return arg.getAnvilGUI();
			}

		}, 0);
		EventValues.registerEventValue(AnvilGUIDoneEvent.class, String.class, new Getter<String, AnvilGUIDoneEvent>() {

			@Nullable
			@Override
			public String get(AnvilGUIDoneEvent arg) {
				return arg.getText();
			}

		}, 0);
	}

	@Override
	public boolean init(Literal<?>[] args, int matchedPattern, SkriptParser.ParseResult parseResult) {
		return true;
	}

	@Override
	public boolean check(Event e) {
		return true;
	}

	@Override
	public String toString(Event e, boolean debug) {
		return "";
	}

}
