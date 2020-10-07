package com.akon.skrage.utils.freeze;

import com.akon.skrage.SkRage;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.metadata.MetadataValue;

public class FreezeTask implements Runnable {

	@Override
	public void run() {
		Bukkit.getOnlinePlayers().stream().filter(FreezeManager::isFrozen).forEach(player -> {
			if (player.isOnGround()) {
				player.teleport(player.getLocation().add(0, 0.01, 0));
			}
			player.getMetadata("Freeze_BoundLocation")
				.stream()
				.filter(metadata -> metadata.getOwningPlugin() == SkRage.getInstance())
				.map(MetadataValue::value)
				.filter(Location.class::isInstance)
				.findAny()
				.map(Location.class::cast)
				.filter(loc -> player.getLocation().distance(loc) > 0)
				.ifPresent(loc -> {
					loc.setYaw(player.getLocation().getYaw());
					loc.setPitch(player.getLocation().getPitch());
					player.teleport(loc);
				});
			player.setAllowFlight(true);
			player.setFlying(true);
		});
	}

}
