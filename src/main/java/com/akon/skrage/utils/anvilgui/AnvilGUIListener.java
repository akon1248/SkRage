package com.akon.skrage.utils.anvilgui;

import com.akon.skrage.SkRage;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.PluginDisableEvent;

public class AnvilGUIListener extends PacketAdapter implements Listener {

	public static final AnvilGUIListener INSTANCE = new AnvilGUIListener();

	private AnvilGUIListener() {
		super(SkRage.getInstance(), PacketType.Play.Client.CLOSE_WINDOW, PacketType.Play.Client.WINDOW_CLICK);
	}

	@Override
	public void onPacketReceiving(PacketEvent e) {
		AnvilGUI anvilGUI = AnvilGUI.getAnvilGUI(e.getPlayer());
		if (anvilGUI != null) {
			if (e.getPacketType() == PacketType.Play.Client.CLOSE_WINDOW) {
				Bukkit.getScheduler().runTask(SkRage.getInstance(), anvilGUI::close);
			} else if (e.getPacketType() == PacketType.Play.Client.WINDOW_CLICK) {
				anvilGUI.onClick(e.getPacket().getIntegers().read(1), e.getPacket().getItemModifier().read(0));
			}
		}
	}


	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		closeAnvilGUI(e.getPlayer());
	}

	@EventHandler
	public void onDisable(PluginDisableEvent e) {
		if (e.getPlugin().equals(SkRage.getInstance())) {
			Bukkit.getOnlinePlayers().forEach(AnvilGUIListener::closeAnvilGUI);
		}
	}

	private static void closeAnvilGUI(Player player) {
		AnvilGUI anvilGUI;
		if ((anvilGUI = AnvilGUI.getAnvilGUI(player)) != null) {
			anvilGUI.close();
		}
	}

}
