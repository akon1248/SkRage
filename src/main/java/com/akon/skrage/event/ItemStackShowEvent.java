package com.akon.skrage.event;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ItemStackShowEvent extends ItemPacketEvent {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    public ItemStackShowEvent(@NotNull Player who, ItemStack itemStack) {
        super(who, itemStack);
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }

}
