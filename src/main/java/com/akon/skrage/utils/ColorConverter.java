package com.akon.skrage.utils;

import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.util.Vector;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ColorConverter {

	private static final HashMap<ChatColor, Vector> CHAT_COLOR_MAP = Maps.newHashMap();
	private static final HashMap<DyeColor, Vector> DYE_COLOR_MAP = Maps.newHashMap();

	static {
		CHAT_COLOR_MAP.put(ChatColor.BLACK, colorToVector(Color.fromRGB(0x000000)));
		CHAT_COLOR_MAP.put(ChatColor.DARK_BLUE, colorToVector(Color.fromRGB(0x0000AA)));
		CHAT_COLOR_MAP.put(ChatColor.DARK_GREEN, colorToVector(Color.fromRGB(0x00AA00)));
		CHAT_COLOR_MAP.put(ChatColor.DARK_AQUA, colorToVector(Color.fromRGB(0x00AAAA)));
		CHAT_COLOR_MAP.put(ChatColor.DARK_RED, colorToVector(Color.fromRGB(0xAA0000)));
		CHAT_COLOR_MAP.put(ChatColor.DARK_PURPLE, colorToVector(Color.fromRGB(0xAA00AA)));
		CHAT_COLOR_MAP.put(ChatColor.GOLD, colorToVector(Color.fromRGB(0xFFAA00)));
		CHAT_COLOR_MAP.put(ChatColor.GRAY, colorToVector(Color.fromRGB(0xAAAAAA)));
		CHAT_COLOR_MAP.put(ChatColor.DARK_GRAY, colorToVector(Color.fromRGB(0x555555)));
		CHAT_COLOR_MAP.put(ChatColor.BLUE, colorToVector(Color.fromRGB(0x5555FF)));
		CHAT_COLOR_MAP.put(ChatColor.GREEN, colorToVector(Color.fromRGB(0x55FF55)));
		CHAT_COLOR_MAP.put(ChatColor.AQUA, colorToVector(Color.fromRGB(0x55FFFF)));
		CHAT_COLOR_MAP.put(ChatColor.RED, colorToVector(Color.fromRGB(0xFF5555)));
		CHAT_COLOR_MAP.put(ChatColor.LIGHT_PURPLE, colorToVector(Color.fromRGB(0xFF55FF)));
		CHAT_COLOR_MAP.put(ChatColor.YELLOW, colorToVector(Color.fromRGB(0xFFFF55)));
		CHAT_COLOR_MAP.put(ChatColor.WHITE, colorToVector(Color.fromRGB(0xFFFFFF)));
		Arrays.stream(DyeColor.values()).forEach(dyeColor -> DYE_COLOR_MAP.put(dyeColor, colorToVector(dyeColor.getColor())));
	}

	private static Vector colorToVector(Color color) {
		return new Vector(color.getRed(), color.getGreen(), color.getBlue());
	}

	private static Color vectorToColor(Vector vec) {
		return Color.fromRGB(vec.getBlockX(), vec.getBlockY(), vec.getBlockZ());
	}


	public static Color fromChatColor(ChatColor chatColor) {
		return vectorToColor(CHAT_COLOR_MAP.get(chatColor));
	}

	public static Color fromDyeColor(DyeColor dyeColor) {
		return dyeColor.getColor();
	}

	public static Color fromSkriptColor(ch.njol.skript.util.Color skriptColor) {
		return skriptColor.asBukkitColor();
	}

	public static ChatColor toChatColor(Color color) {
		Vector rgbVec = colorToVector(color);
		ChatColor closest = null;
		double closestDist = -1;
		for (Map.Entry<ChatColor, Vector> entry: CHAT_COLOR_MAP.entrySet()) {
			Vector vec = entry.getValue();
			double dist = rgbVec.distance(vec);
			if (closest == null || dist < closestDist) {
				closest = entry.getKey();
				closestDist = dist;
			}
		}
		return closest;
	}

	public static DyeColor toDyeColor(Color color) {
		Vector rgbVec = colorToVector(color);
		DyeColor closest = null;
		double closestDist = -1;
		for (Map.Entry<DyeColor, Vector> entry: DYE_COLOR_MAP.entrySet()) {
			Vector vec = entry.getValue();
			double dist = rgbVec.distance(vec);
			if (closest == null || dist < closestDist) {
				closest = entry.getKey();
				closestDist = dist;
			}
		}
		return closest;
	}

	public static ch.njol.skript.util.Color toSkriptColor(Color color) {
		return new RGBColor(color, toDyeColor(color), toChatColor(color));
	}

	@AllArgsConstructor
	private static class RGBColor implements ch.njol.skript.util.Color {

		private final Color color;
		private final DyeColor dyeColor;
		private final ChatColor chatColor;

		@Override
		public Color asBukkitColor() {
			return this.color;
		}

		@Override
		public ChatColor asChatColor() {
			return this.chatColor;
		}

		@Override
		public DyeColor asDyeColor() {
			return this.dyeColor;
		}

		@Override
		public String getName() {
			return "#" + Integer.toHexString(this.color.asRGB()).toUpperCase();
		}

		@Override
		public byte getWoolData() {
			return this.dyeColor.getWoolData();
		}

		@Override
		public byte getDyeData() {
			return this.dyeColor.getDyeData();
		}

		@Override
		public String getFormattedChat() {
			return this.chatColor.toString();
		}
	}
}
