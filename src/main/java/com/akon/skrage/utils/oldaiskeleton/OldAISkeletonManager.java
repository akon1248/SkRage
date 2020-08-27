package com.akon.skrage.utils.oldaiskeleton;

import com.google.common.collect.Sets;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftSkeleton;
import org.bukkit.entity.Skeleton;

import java.util.HashSet;

//スケルトンのOldAIが有効かどうかを管理する
public class OldAISkeletonManager {

	private static final HashSet<Skeleton> OLD_AI_SKELETONS = Sets.newHashSet();

	public static void setOldAI(Skeleton skeleton, boolean oldAI) {
		if (oldAI) {
			OLD_AI_SKELETONS.add(skeleton);
		} else {
			OLD_AI_SKELETONS.remove(skeleton);
		}
		((CraftSkeleton)skeleton).getHandle().dm();
	}

	public static boolean isOldAI(Skeleton skeleton) {
		return OLD_AI_SKELETONS.contains(skeleton);
	}

}
