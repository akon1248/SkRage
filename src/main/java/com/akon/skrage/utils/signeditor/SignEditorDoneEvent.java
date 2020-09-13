package com.akon.skrage.utils.signeditor;

import org.apache.commons.lang.StringUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

public class SignEditorDoneEvent extends SignEditorEvent {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    private final String[] lines = new String[4];

    public SignEditorDoneEvent(Player who, SignEditor signEditor, String line1, String line2, String line3, String line4) {
        super(who, signEditor);
        this.lines[0] = line1 == null ? StringUtils.EMPTY : line1;
        this.lines[1] = line2 == null ? StringUtils.EMPTY : line2;
        this.lines[2] = line3 == null ? StringUtils.EMPTY : line3;
        this.lines[3] = line4 == null ? StringUtils.EMPTY : line4;
    }

    public String[] getLines() {
        String[] arr = new String[4];
        System.arraycopy(this.lines, 0, arr, 0, 4);
        return arr;
    }

    public String getLine(int line) {
        return this.lines[line];
    }

    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }
}
