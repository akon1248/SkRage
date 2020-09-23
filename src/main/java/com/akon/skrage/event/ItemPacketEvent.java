package com.akon.skrage.event;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.inventory.ItemStack;

public abstract class ItemPacketEvent extends PlayerEvent {

    @Getter
    @Setter
    private ItemStack itemStack;

    public ItemPacketEvent(Player who, ItemStack itemStack) {
        super(who);
        this.itemStack = itemStack;
    }

}
