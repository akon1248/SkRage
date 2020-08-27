package com.akon.skrage.event;

import com.google.common.collect.Maps;
import lombok.Getter;
import lombok.Setter;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.core.filter.AbstractFilter;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.spi.StandardLevel;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.HashMap;
import java.util.Map;

//呼び出しを行ってるクラス: com.akon.skrage.event.ConsoleLogEvent$1
@Getter
public class ConsoleLogEvent extends Event implements Cancellable {

	private static final HandlerList HANDLER_LIST = new HandlerList();
	private static final HashMap<String, ChatColor> CONSOLE_COLOR_MAP = Maps.newHashMap();
	@Setter
	private boolean cancelled;
	private String message;
	private StandardLevel level;

	static {
		CONSOLE_COLOR_MAP.put('\u001b' + "[0;30;22m", ChatColor.BLACK);
		CONSOLE_COLOR_MAP.put('\u001b' + "[0;34;22m", ChatColor.DARK_BLUE);
		CONSOLE_COLOR_MAP.put('\u001b' + "[0;32;22m", ChatColor.DARK_GREEN);
		CONSOLE_COLOR_MAP.put('\u001b' + "[0;36;22m", ChatColor.DARK_AQUA);
		CONSOLE_COLOR_MAP.put('\u001b' + "[0;31;22m", ChatColor.DARK_RED);
		CONSOLE_COLOR_MAP.put('\u001b' + "[0;35;22m", ChatColor.DARK_PURPLE);
		CONSOLE_COLOR_MAP.put('\u001b' + "[0;33;22m", ChatColor.GOLD);
		CONSOLE_COLOR_MAP.put('\u001b' + "[0;37;22m", ChatColor.GRAY);
		CONSOLE_COLOR_MAP.put('\u001b' + "[0;30;1m", ChatColor.DARK_GRAY);
		CONSOLE_COLOR_MAP.put('\u001b' + "[0;34;1m", ChatColor.BLUE);
		CONSOLE_COLOR_MAP.put('\u001b' + "[0;32;1m", ChatColor.GREEN);
		CONSOLE_COLOR_MAP.put('\u001b' + "[0;36;1m", ChatColor.AQUA);
		CONSOLE_COLOR_MAP.put('\u001b' + "[0;31;1m", ChatColor.RED);
		CONSOLE_COLOR_MAP.put('\u001b' + "[0;35;1m", ChatColor.LIGHT_PURPLE);
		CONSOLE_COLOR_MAP.put('\u001b' + "[0;33;1m", ChatColor.YELLOW);
		CONSOLE_COLOR_MAP.put('\u001b' + "[0;37;1m", ChatColor.WHITE);
		CONSOLE_COLOR_MAP.put('\u001b' + "[m", ChatColor.RESET);

		((Logger)LogManager.getRootLogger()).addFilter(new AbstractFilter() {

			private Result callEvent(Level level, String message) {
				for (Map.Entry<String, ChatColor> entry : CONSOLE_COLOR_MAP.entrySet()) {
					message = message.replace(entry.getKey(), entry.getValue().toString());
				}
				ConsoleLogEvent event = new ConsoleLogEvent(message, StandardLevel.getStandardLevel(level.intLevel()));
				Bukkit.getPluginManager().callEvent(event);
				return event.isCancelled() ? Result.DENY : Result.NEUTRAL;
			}

			@Override
			public Result filter(LogEvent logEvent) {
				return this.callEvent(logEvent.getLevel(), logEvent.getMessage().getFormattedMessage());
			}

			@Override
			public Result filter(Logger logger, Level level, Marker marker, Message message, Throwable throwable) {
				return this.callEvent(level, message.getFormattedMessage());
			}

			@Override
			public Result filter(Logger logger, Level level, Marker marker, Object o, Throwable throwable) {
				return this.callEvent(level, String.valueOf(o));
			}

			@Override
			public Result filter(Logger logger, Level level, Marker marker, String s, Object... object) {
				return this.callEvent(level, s);
			}

		});
	}

	public ConsoleLogEvent(String message, StandardLevel level) {
		this.message = message;
		this.level = level;
	}

	@Override
	public HandlerList getHandlers() {
		return HANDLER_LIST;
	}

	public static HandlerList getHandlerList() {
		return HANDLER_LIST;
	}

}
