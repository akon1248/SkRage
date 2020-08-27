package com.akon.skrage.utils.anvilgui;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class AnvilGUIOpenEvent extends AnvilGUIEvent implements Cancellable {

	private static final HandlerList HANDLER_LIST = new HandlerList();
	@Getter
	@Setter
	private boolean cancelled;

	public AnvilGUIOpenEvent(@NotNull Player who, AnvilGUI anvilGUI) {
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
