package com.akon.skrage;

import ch.njol.skript.Skript;
import com.comphenix.protocol.ProtocolLibrary;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public class SkRage extends JavaPlugin {

	private static SkRage instance;

	@Override
	public void onEnable() {
		instance = this;
		try {
			Skript.registerAddon(this).loadClasses("com.akon.skrage", "skript");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		Registration.registerRepeatingTasks();
		Registration.registerBukkitListeners();
		Registration.registerPacketListeners();
	}

	@Override
	public void onDisable() {
		ProtocolLibrary.getProtocolManager().removePacketListeners(this);
	}
	
	public static SkRage getInstance() {
		return instance;
	}

}
