package com.akon.skrage.utils.anvilgui;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class AnvilGUICloseEvent extends AnvilGUIEvent {

	private static final HandlerList HANDLER_LIST = new HandlerList();

	public AnvilGUICloseEvent(@NotNull Player who, AnvilGUI anvilGUI) {
		super(who, anvilGUI);
	}

	@Override
	public @NotNull
	HandlerList getHandlers() {
		return HANDLER_LIST;
	}

	public static HandlerList getHandlerList() {
		return HANDLER_LIST;
	}

}
