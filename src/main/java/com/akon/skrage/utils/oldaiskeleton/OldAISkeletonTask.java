package com.akon.skrage.utils.oldaiskeleton;

import com.akon.skrage.utils.NMSUtil;
import com.akon.skrage.utils.ReflectionUtil;
import net.minecraft.server.v1_12_R1.EntitySkeletonAbstract;
import net.minecraft.server.v1_12_R1.PathfinderGoalArrowAttack;
import net.minecraft.server.v1_12_R1.PathfinderGoalBowShoot;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftSkeleton;
import org.bukkit.entity.Skeleton;

//OldAIが有効なスケルトンのAIを書き換える
public class OldAISkeletonTask implements Runnable {

	@Override
	public void run() {
		Bukkit.getWorlds()
			.stream()
			.flatMap(world -> world.getEntitiesByClass(Skeleton.class).stream())
			.forEach(skeleton -> {
				if (skeleton.getEquipment().getItemInMainHand().getType() == Material.BOW && OldAISkeletonManager.isOldAI(skeleton)) {
					EntitySkeletonAbstract nmsSkeleton = ((CraftSkeleton)skeleton).getHandle();
					PathfinderGoalBowShoot<EntitySkeletonAbstract> bowShoot = (PathfinderGoalBowShoot<EntitySkeletonAbstract>)ReflectionUtil.DEFAULT.getField(EntitySkeletonAbstract.class, nmsSkeleton, "b");
					if (NMSUtil.getGoalSelectors(skeleton).contains(bowShoot)) {
						nmsSkeleton.goalSelector.a(bowShoot);
						nmsSkeleton.goalSelector.a(4, new PathfinderGoalArrowAttack(nmsSkeleton, 1.0D, 20, 60, 15.0F));
					}
				}
			});
	}

}
