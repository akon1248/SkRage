package com.akon.skrage;

import com.akon.skrage.mmextension.MythicListener;
import com.akon.skrage.utils.anvilgui.AnvilGUIListener;
import com.akon.skrage.utils.combattracker.CombatTrackerListener;
import com.akon.skrage.utils.oldaiskeleton.OldAISkeletonPacketListener;
import com.akon.skrage.utils.oldaiskeleton.OldAISkeletonTask;
import com.akon.skrage.utils.signeditor.SignEditorListener;
import com.akon.skrage.utils.skin.SkinListener;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketListener;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

import java.io.IOException;
import java.net.URI;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.HashMap;
import java.util.function.Consumer;

public class Registration {

	private static final Plugin PLUGIN = SkRage.getInstance();

	public static void registerRepeatingTasks() {
		Bukkit.getScheduler().runTaskTimer(PLUGIN, new OldAISkeletonTask(), 0, 1);
	}

	public static void registerBukkitListeners() {
		if (Bukkit.getPluginManager().isPluginEnabled("MythicMobs") && Bukkit.getPluginManager().isPluginEnabled("MythicSkriptAddon")) {
			Bukkit.getPluginManager().registerEvents(new MythicListener(), PLUGIN);
		}
		Bukkit.getPluginManager().registerEvents(new CombatTrackerListener(), PLUGIN);
		Bukkit.getPluginManager().registerEvents(AnvilGUIListener.INSTANCE, PLUGIN);
		Bukkit.getPluginManager().registerEvents(SignEditorListener.INSTANCE, PLUGIN);
		registerClasses("com.akon.skrage.listener", clazz -> {
			if (Listener.class.isAssignableFrom(clazz)) {
				try {
					Listener listener = (Listener)clazz.newInstance();
					Bukkit.getPluginManager().registerEvents(listener, PLUGIN);
				} catch (ReflectiveOperationException ex) {
					ex.printStackTrace();
				}
			}
		});
	}

	public static void registerPacketListeners() {
		ProtocolLibrary.getProtocolManager().addPacketListener(new OldAISkeletonPacketListener());
		ProtocolLibrary.getProtocolManager().addPacketListener(new SkinListener());
		ProtocolLibrary.getProtocolManager().addPacketListener(AnvilGUIListener.INSTANCE);
		ProtocolLibrary.getProtocolManager().addPacketListener(SignEditorListener.INSTANCE);
		registerClasses("com.akon.skrage.listener.packet", clazz -> {
			if (PacketListener.class.isAssignableFrom(clazz)) {
				try {
					PacketListener packetListener = (PacketListener)clazz.newInstance();
					ProtocolLibrary.getProtocolManager().addPacketListener(packetListener);
				} catch (ReflectiveOperationException ex) {
					ex.printStackTrace();
				}
			}
		});
	}

	public static void registerClasses(String pkg, Consumer<Class<?>> consumer) {
		try (FileSystem fileSystem = FileSystems.newFileSystem(URI.create("jar:" + Registration.class.getProtectionDomain().getCodeSource().getLocation().toString()), new HashMap<>())) {
			Files.walkFileTree(fileSystem.getPath("/" + pkg.replace(".", "/") + "/"), new SimpleFileVisitor<Path>() {

				@Override
				public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
					if (file.toString().endsWith(".class")) {
						try {
							Class<?> clazz = Class.forName(file.toString().substring(1, file.toString().length()-6).replace("/", "."));
							consumer.accept(clazz);
						} catch (ClassNotFoundException ex) {
							ex.printStackTrace();
						}
					}
					return FileVisitResult.CONTINUE;
				}

			});
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

}
