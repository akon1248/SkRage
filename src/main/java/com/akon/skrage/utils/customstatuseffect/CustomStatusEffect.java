package com.akon.skrage.utils.customstatuseffect;

import lombok.*;

@Getter
@EqualsAndHashCode
@ToString
public class CustomStatusEffect {

	private final CSEType type;
	private final int amplifier;
	private int duration;
	private final boolean ambient;
	@Getter(AccessLevel.NONE)
	private final boolean particles;

	public CustomStatusEffect(CSEType type, int amplifier, int duration, boolean ambient, boolean particles) {
		this.type = type;
		this.amplifier = amplifier;
		this.setDuration(duration);
		this.ambient = ambient;
		this.particles = particles;
	}

	public CustomStatusEffect(CSEType type, int amplifier, int duration, boolean ambient) {
		this(type, amplifier, duration, ambient, true);
	}

	public CustomStatusEffect(CSEType type, int amplifier, int duration) {
		this(type, amplifier, duration, false);
	}

	public boolean hasParticles() {
		return this.particles;
	}

	public void setDuration(int duration) {
		this.duration = Math.max(0, duration);
	}
}
