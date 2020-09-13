package com.akon.skrage.utils.anvilgui;

import com.akon.skrage.SkRage;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.google.common.collect.Maps;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

/**
 * プレイヤーにテキストを入力させるための仮想GUI
 */
@Getter
public class AnvilGUI {

	private static final HashMap<UUID, AnvilGUI> ANVIL_GUI_MAP = Maps.newHashMap();

	private final int windowId;
	private final String name;
	private final ItemStack item;
	private final String initText;
	private Player viewer;

	public AnvilGUI(String name, ItemStack item, String initText) {
		this.windowId = -new Random().nextInt(127) - 1;
		this.name = name;
		this.item = item;
		this.initText = initText;
	}

	public boolean open(Player player) {
		if (this.viewer != null) {
			throw new IllegalStateException("既に他のプレイヤーにGUIを表示しています");
		}
		ItemStack item = this.item.clone();
		if (this.item.getType() != Material.AIR) {
			ItemMeta meta = item.getItemMeta();
			meta.setDisplayName(this.initText);
			item.setItemMeta(meta);
		}
		AnvilGUIOpenEvent event = new AnvilGUIOpenEvent(player, this);
		Bukkit.getPluginManager().callEvent(event);
		if (!event.isCancelled()) {
			Optional.ofNullable(getAnvilGUI(player)).ifPresent(AnvilGUI::close);
			PacketContainer openAnvil = new PacketContainer(PacketType.Play.Server.OPEN_WINDOW);
			openAnvil.getIntegers().write(0, this.windowId);
			openAnvil.getStrings().write(0, "minecraft:anvil");
			openAnvil.getChatComponents().write(0, WrappedChatComponent.fromText(this.name));
			PacketContainer windowsItems = new PacketContainer(PacketType.Play.Server.WINDOW_ITEMS);
			windowsItems.getIntegers().write(0, this.windowId);
			windowsItems.getItemListModifier().write(0, Collections.singletonList(item));
			try {
				ProtocolLibrary.getProtocolManager().sendServerPacket(player, openAnvil);
				ProtocolLibrary.getProtocolManager().sendServerPacket(player, windowsItems);
			} catch (InvocationTargetException ex) {
				ex.printStackTrace();
			}
			ANVIL_GUI_MAP.put(player.getUniqueId(), this);
			this.viewer = player;
			return true;
		}
		return false;
	}

	public void close() {
		Optional.ofNullable(this.viewer).orElseThrow(() -> new IllegalStateException("GUIを表示しているプレイヤーがいません"));
		Bukkit.getPluginManager().callEvent(new AnvilGUICloseEvent(this.viewer, this));
		PacketContainer close = new PacketContainer(PacketType.Play.Server.CLOSE_WINDOW);
		close.getIntegers().write(0, this.windowId);
		try {
			ProtocolLibrary.getProtocolManager().sendServerPacket(this.viewer, close);
		} catch (InvocationTargetException ex) {
			ex.printStackTrace();
		}
		ANVIL_GUI_MAP.remove(this.viewer.getUniqueId());
		this.viewer = null;
	}

	protected void onClick(int slot, ItemStack item) {
		Bukkit.getScheduler().runTask(SkRage.getInstance(), () -> {
			if (slot == 2) {
				Bukkit.getPluginManager().callEvent(new AnvilGUIDoneEvent(this.viewer, item.getItemMeta().getDisplayName(), this));
			}
			this.close();
		});
	}


	@Nullable
	public static AnvilGUI getAnvilGUI(Player player) {
		return ANVIL_GUI_MAP.get(player.getUniqueId());
	}

}
