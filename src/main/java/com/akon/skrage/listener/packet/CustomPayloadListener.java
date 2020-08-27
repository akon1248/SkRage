package com.akon.skrage.listener.packet;

import com.akon.skrage.SkRage;
import com.akon.skrage.event.PluginMessageEvent;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.utility.MinecraftReflection;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class CustomPayloadListener extends PacketAdapter {

	public CustomPayloadListener() {
		super(SkRage.getInstance(), PacketType.Play.Client.CUSTOM_PAYLOAD, PacketType.Play.Server.CUSTOM_PAYLOAD);
	}

	@Override
	public void onPacketReceiving(PacketEvent event) {
		if (event.getPacketType() == PacketType.Play.Client.CUSTOM_PAYLOAD) {
			ByteBuf buffer = (ByteBuf)event.getPacket().getModifier().withType(ByteBuf.class).read(0);
			byte[] contents = new byte[buffer.readableBytes()];
			buffer.readBytes(contents);
			PluginMessageEvent e = new PluginMessageEvent(event.getPlayer(), event.getPacket().getStrings().read(0), contents, false);
			if (!e.isCancelled()) {
				contents = e.getContents();
				buffer = Unpooled.copiedBuffer(contents);
				event.getPacket().getModifier().withType(ByteBuf.class).write(0, MinecraftReflection.getPacketDataSerializer(buffer));
			}
		}
	}

	@Override
	public void onPacketSending(PacketEvent event) {
		if (event.getPacketType() == PacketType.Play.Server.CUSTOM_PAYLOAD) {
			ByteBuf buffer = (ByteBuf)event.getPacket().getModifier().withType(ByteBuf.class).read(0);
			byte[] contents = new byte[buffer.readableBytes()];
			buffer.readBytes(contents);
			PluginMessageEvent e = new PluginMessageEvent(event.getPlayer(), event.getPacket().getStrings().read(0), contents, true);
			if (!e.isCancelled()) {
				contents = e.getContents();
				buffer = Unpooled.copiedBuffer(contents);
				event.getPacket().getModifier().withType(ByteBuf.class).write(0, MinecraftReflection.getPacketDataSerializer(buffer));
			}
		}
	}

}
