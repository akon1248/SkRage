package com.akon.skrage.utils.anvilgui;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;

public abstract class AnvilGUIEvent extends PlayerEvent {

	@Getter
	private AnvilGUI anvilGUI;

	public AnvilGUIEvent(@NotNull Player who, AnvilGUI anvilGUI) {
		super(who);
		this.anvilGUI = anvilGUI;
	}

}
