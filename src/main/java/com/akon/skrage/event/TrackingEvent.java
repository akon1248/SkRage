package com.akon.skrage.event;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.entity.EntityEvent;

public abstract class TrackingEvent extends EntityEvent implements Cancellable {

    @Getter
    private final Player player;
    @Getter
    @Setter
    private boolean cancelled;

    public TrackingEvent(Entity what, Player player) {
        super(what);
        this.player = player;
    }
}
