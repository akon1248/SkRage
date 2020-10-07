package com.akon.skrage.utils.freeze;

import com.akon.skrage.SkRage;
import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.EnumWrappers;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

public class FreezeListener extends PacketAdapter implements Listener {

	public static final FreezeListener INSTANCE = new FreezeListener();

	private FreezeListener() {
		super(SkRage.getInstance(), PacketType.Play.Server.ABILITIES, PacketType.Play.Client.USE_ENTITY, PacketType.Play.Client.ARM_ANIMATION, PacketType.Play.Client.BLOCK_PLACE, PacketType.Play.Client.USE_ITEM);
	}

	@EventHandler
	public void onVelocity(PlayerVelocityEvent e) {
		if (FreezeManager.isFrozen(e.getPlayer())) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onToggleFlight(PlayerToggleFlightEvent e) {
		if (e.getPlayer().isFlying() && FreezeManager.isFrozen(e.getPlayer())) {
			e.setCancelled(true);
		}
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onTeleport(PlayerTeleportEvent e) {
		if (!e.isCancelled() && e.getCause() != PlayerTeleportEvent.TeleportCause.UNKNOWN) {
			e.getPlayer().setMetadata("Freeze_BoundLocation", new FixedMetadataValue(SkRage.getInstance(), e.getTo()));
		}
	}

	@Override
	public void onPacketSending(PacketEvent e) {
		if (FreezeManager.isFrozen(e.getPlayer()) && e.getPacketType() == PacketType.Play.Server.ABILITIES) {
			e.getPacket().getFloat().write(0, 0.0F);
		}
	}

	@Override
	public void onPacketReceiving(PacketEvent e) {
		if (FreezeManager.isFrozen(e.getPlayer())) {
			boolean flag = e.getPacketType() == PacketType.Play.Client.USE_ENTITY || e.getPacketType() == PacketType.Play.Client.ARM_ANIMATION;
			if (e.getPacketType() == PacketType.Play.Client.BLOCK_PLACE || e.getPacketType() == PacketType.Play.Client.USE_ITEM) {
				ItemStack item = e.getPacket().getHands().read(0) == EnumWrappers.Hand.MAIN_HAND ? e.getPlayer().getInventory().getItemInMainHand() : e.getPlayer().getInventory().getItemInOffHand();
				flag = !item.getType().isBlock();
			}
			e.setCancelled(flag);
		}
	}

	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		if (FreezeManager.isFrozen(e.getPlayer())) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		e.getPlayer().setAllowFlight(FreezeManager.wasAllowFlight(e.getPlayer()));
		e.getPlayer().setFlying(FreezeManager.wasFlying(e.getPlayer()));
	}

}
