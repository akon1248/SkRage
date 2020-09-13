package com.akon.skrage.utils.signeditor;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;

public class SignEditorOpenEvent extends SignEditorEvent implements Cancellable {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    @Getter
    @Setter
    private boolean cancelled;

    public SignEditorOpenEvent(Player who, SignEditor signEditor) {
        super(who, signEditor);
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }
}
