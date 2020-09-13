package com.akon.skrage.utils.signeditor;

import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerEvent;

public abstract class SignEditorEvent extends PlayerEvent {

    @Getter
    private final SignEditor signEditor;

    public SignEditorEvent(Player who, SignEditor signEditor) {
        super(who);
        this.signEditor = signEditor;
    }

}
