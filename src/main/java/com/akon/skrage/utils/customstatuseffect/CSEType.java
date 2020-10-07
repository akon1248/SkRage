package com.akon.skrage.utils.customstatuseffect;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.entity.LivingEntity;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class CSEType {

	private final String id;
	private final String name;
	private final Color color;

	public void onApply(CustomStatusEffect effect, LivingEntity entity) {
		Bukkit.getPluginManager().callEvent(new CSEApplyEvent(entity, effect));
	}

	public void onRemove(CustomStatusEffect effect, LivingEntity entity) {
		Bukkit.getPluginManager().callEvent(new CSERemoveEvent(entity, effect));
	}

	public void onTick(CustomStatusEffect effect, LivingEntity entity) {
		Bukkit.getPluginManager().callEvent(new CSETickEvent(entity, effect));
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName() + " " + this.name;
	}
}
