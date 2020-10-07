package com.akon.skrage.utils.customstatuseffect;

import lombok.Getter;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.EntityEvent;

public abstract class CSEEvent extends EntityEvent {

	@Getter
	private final CustomStatusEffect effect;

	public CSEEvent(LivingEntity what, CustomStatusEffect effect) {
		super(what);
		this.effect = effect;
	}

	@Override
	public LivingEntity getEntity() {
		return (LivingEntity)this.entity;
	}
}
