package com.akon.skrage.event;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

@Getter
public class PluginMessageEvent extends PlayerEvent implements Cancellable {

	@Setter
	private boolean cancelled;
	private String channel;
	@Setter
	private byte[] contents;
	private boolean sending;
	private static HandlerList HANDLER_LIST = new HandlerList();

	public PluginMessageEvent(Player who, String channel, byte[] contents, boolean sending) {
		super(who);
		this.channel = channel;
		this.contents = contents;
		this.sending = sending;
	}

	@Override
	public HandlerList getHandlers() {
		return HANDLER_LIST;
	}

	public static HandlerList getHandlerList() {
		return HANDLER_LIST;
	}

}
