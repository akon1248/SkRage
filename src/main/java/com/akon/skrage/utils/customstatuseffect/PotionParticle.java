package com.akon.skrage.utils.customstatuseffect;

import lombok.Data;
import org.bukkit.Color;


@Data
class PotionParticle {

	static final PotionParticle NONE = new PotionParticle(Color.BLACK, false);

	private final Color color;
	private final boolean ambient;

}
