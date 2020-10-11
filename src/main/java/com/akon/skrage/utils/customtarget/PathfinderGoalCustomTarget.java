package com.akon.skrage.utils.customtarget;

import com.google.common.base.Predicate;
import net.minecraft.server.v1_12_R1.*;

public class PathfinderGoalCustomTarget extends PathfinderGoalNearestAttackableTarget<EntityLiving> implements CustomTarget {

	public PathfinderGoalCustomTarget(EntityCreature entitycreature, Predicate<? super EntityLiving> predicate) {
		super(entitycreature, EntityLiving.class, 0, true, false, predicate);
	}

}
