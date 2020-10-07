package com.akon.skrage.skript.syntaxes.ProtocolLib.PluginMessage;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.utility.MinecraftReflection;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class EffSendPluginMessage extends Effect {

	static {
		Skript.registerEffect(EffSendPluginMessage.class, "send plugin message with channel %string% [and] contents %numbers% to %player%");
	}

	private Expression<String> channel;
	private Expression<Number> contents;
	private Expression<Player> player;

	@Override
	protected void execute(Event e) {
		if (this.channel != null && this.contents != null && this.player != null) {
			PacketContainer payload = new PacketContainer(PacketType.Play.Server.CUSTOM_PAYLOAD);
			payload.getStrings().write(0, channel.getSingle(e));
			payload.getModifier().withType(ByteBuf.class).write(0, MinecraftReflection.getPacketDataSerializer(Unpooled.wrappedBuffer(ArrayUtils.toPrimitive(Arrays.stream(this.contents.getAll(e)).map(Number::byteValue).toArray(Byte[]::new)))));
			try {
				ProtocolLibrary.getProtocolManager().sendServerPacket(this.player.getSingle(e), payload);
			} catch (InvocationTargetException ex) {
				ex.printStackTrace();
			}
		}
	}

	@Override
	public String toString(@Nullable Event e, boolean debug) {
		return "";
	}

	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
		this.channel = (Expression<String>)exprs[0];
		this.contents = (Expression<Number>)exprs[1];
		this.player = (Expression<Player>)exprs[2];
		return true;
	}
}
