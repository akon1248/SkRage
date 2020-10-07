package com.akon.skrage.utils.customstatuseffect;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.HandlerList;

public class CSETickEvent extends CSEEvent {

	private static final HandlerList HANDLER_LIST = new HandlerList();

	public CSETickEvent(LivingEntity what, CustomStatusEffect effect) {
		super(what, effect);
	}

	@Override
	public HandlerList getHandlers() {
		return HANDLER_LIST;
	}

	public static HandlerList getHandlerList() {
		return HANDLER_LIST;
	}

}
