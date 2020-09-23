package com.akon.skrage.event;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

public class EntityAddTrackingPlayerEvent extends TrackingEvent {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    public EntityAddTrackingPlayerEvent(Entity what, Player player) {
        super(what, player);
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

}
