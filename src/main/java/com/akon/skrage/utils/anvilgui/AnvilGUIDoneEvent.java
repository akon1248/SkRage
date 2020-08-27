package com.akon.skrage.utils.anvilgui;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class AnvilGUIDoneEvent extends AnvilGUIEvent {

	private static final HandlerList HANDLER_LIST = new HandlerList();
	@Getter
	private String text;

	public AnvilGUIDoneEvent(@NotNull Player who, String text, AnvilGUI anvilGUI) {
		super(who, anvilGUI);
		this.text = text;
	}

	@Override
	public HandlerList getHandlers() {
		return HANDLER_LIST;
	}

	public static HandlerList getHandlerList() {
		return HANDLER_LIST;
	}

}
