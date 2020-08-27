package com.akon.skrage;

import ch.njol.skript.Skript;
import com.comphenix.protocol.ProtocolLibrary;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public class SkRage extends JavaPlugin {

	private static SkRage instance;

	@Override
	public void onEnable() {
		instance = this;
		try {
			Skript.registerAddon(this).loadClasses("com.akon", "skrage");
		} catch (IOException e) {
			e.printStackTrace();
		}
		Registration.registerRepeatingTasks();
		Registration.registerBukkitListeners();
		if (Bukkit.getPluginManager().isPluginEnabled("ProtocolLib")) {
			Registration.registerPacketListeners();
		}
	}

	@Override
	public void onDisable() {
		if (Bukkit.getPluginManager().isPluginEnabled("ProtocolLib")) {
			ProtocolLibrary.getProtocolManager().removePacketListeners(this);
		}
	}
	
	public static SkRage getInstance() {
		return instance;
	}

}
