package com.akon.skrage.listener.packet;

import com.akon.skrage.SkRage;
import com.akon.skrage.event.CreativeItemReceiveEvent;
import com.akon.skrage.event.ItemStackShowEvent;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.stream.Collectors;

public class ItemPacketListener extends PacketAdapter {

    public ItemPacketListener() {
        super(SkRage.getInstance(), PacketType.Play.Server.SET_SLOT, PacketType.Play.Server.WINDOW_ITEMS, PacketType.Play.Client.SET_CREATIVE_SLOT);
    }

    @Override
    public void onPacketSending(PacketEvent e) {
        if (e.getPacketType() == PacketType.Play.Server.SET_SLOT) {
            e.getPacket().getItemModifier().write(0, this.callShowEvent(e.getPlayer(), e.getPacket().getItemModifier().read(0)));
        } else if (e.getPacketType() == PacketType.Play.Server.WINDOW_ITEMS) {
            e.getPacket().getItemListModifier().write(0, e.getPacket().getItemListModifier().read(0).stream().map(item -> this.callShowEvent(e.getPlayer(), item)).collect(Collectors.toList()));
        }
    }

    @Override
    public void onPacketReceiving(PacketEvent e) {
        if (e.getPacketType() == PacketType.Play.Client.SET_CREATIVE_SLOT) {
            e.getPacket().getItemModifier().write(0, this.callReceiveEvent(e.getPlayer(), e.getPacket().getItemModifier().read(0)));
        }
    }

    private ItemStack callShowEvent(Player player, ItemStack item) {
        ItemStackShowEvent event = new ItemStackShowEvent(player, item);
        Bukkit.getPluginManager().callEvent(event);
        return event.getItemStack();
    }

    private ItemStack callReceiveEvent(Player player, ItemStack item) {
        CreativeItemReceiveEvent event = new CreativeItemReceiveEvent(player, item);
        Bukkit.getPluginManager().callEvent(event);
        return event.getItemStack();
    }

}
