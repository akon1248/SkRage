package com.akon.skrage.utils.customstatuseffect;

import org.bukkit.Bukkit;

public class CSEUpdateTask implements Runnable {

	@Override
	public void run() {
		Bukkit.getWorlds()
			.stream()
			.flatMap(world -> world.getLivingEntities().stream())
			.forEach(entity -> CSEManager.getActiveCSEs(entity).values().forEach(effect -> {
				if (effect.getDuration() <= 0) {
					CSEManager.remove(entity, effect.getType());
				}
				effect.setDuration(effect.getDuration()-1);
				effect.getType().onTick(effect, entity);
			}));
	}

}
