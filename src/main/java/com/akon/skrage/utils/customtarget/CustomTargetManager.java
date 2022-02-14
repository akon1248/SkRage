package com.akon.skrage.utils.customtarget;

import com.akon.skrage.utils.ReflectionUtil;
import com.akon.skrage.utils.exceptionsafe.ExceptionSafe;
import com.google.common.base.Predicate;
import com.google.common.collect.Sets;
import net.minecraft.server.v1_12_R1.*;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftLivingEntity;
import org.bukkit.entity.LivingEntity;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.WeakHashMap;

public class CustomTargetManager {

	private static final WeakHashMap<LivingEntity, HashSet<Object>> REMOVED_AI_MAP = new WeakHashMap<>();
	private static final WeakHashMap<LivingEntity, CustomTarget> CUSTOM_TARGET_MAP = new WeakHashMap<>();

	public static boolean setCustomTarget(LivingEntity entity, Predicate<LivingEntity> predicate) {
		if (CUSTOM_TARGET_MAP.containsKey(entity)) {
			return false;
		}
		Optional.ofNullable(entity).map(CraftLivingEntity.class::cast).map(CraftLivingEntity::getHandle).filter(EntityInsentient.class::isInstance).map(EntityInsentient.class::cast).ifPresent(ExceptionSafe.<EntityInsentient, Exception>consumer(nmsEntity -> {
			Set<?> tasks = (Set<?>)ReflectionUtil.DEFAULT.getField(nmsEntity.targetSelector, "b");
			Set<?> executingTasks = (Set<?>)ReflectionUtil.DEFAULT.getField(nmsEntity.targetSelector, "c");
			for (Object item: Sets.newHashSet(tasks)) {
				PathfinderGoal pathfinderGoal = (PathfinderGoal)ReflectionUtil.DEFAULT.getField(item, "a");
				if (pathfinderGoal instanceof PathfinderGoalNearestAttackableTarget || pathfinderGoal instanceof PathfinderGoalNearestAttackableTargetInsentient || pathfinderGoal instanceof PathfinderGoalTargetNearestPlayer) {
					tasks.remove(item);
					executingTasks.remove(item);
					Optional.ofNullable(REMOVED_AI_MAP.get(entity)).orElseGet(() -> {
						HashSet<Object> set = Sets.newHashSet();
						REMOVED_AI_MAP.put(entity, set);
						return set;
					}).add(item);
				}
			}
			CustomTarget targetAI;
			Predicate<EntityLiving> entityLivingPredicate = ent -> ent != null && predicate.test((LivingEntity)ent.getBukkitEntity());
			if (nmsEntity instanceof EntityCreature) {
				targetAI = new PathfinderGoalCustomTarget((EntityCreature)nmsEntity, entityLivingPredicate);
			} else {
				targetAI = new PathfinderGoalCustomTargetInsentient(nmsEntity, entityLivingPredicate);
			}
			CUSTOM_TARGET_MAP.put(entity, targetAI);
			nmsEntity.targetSelector.a(1, (PathfinderGoal)targetAI);
		}).caught(ExceptionSafe.PRINT_STACK_TRACE));
		return true;
	}

	public static boolean resetCustomTarget(LivingEntity entity) {
		CustomTarget targetAI = CUSTOM_TARGET_MAP.get(entity);
		if (targetAI == null) {
			return false;
		}
		Optional.ofNullable(entity).map(CraftLivingEntity.class::cast).map(CraftLivingEntity::getHandle).filter(EntityInsentient.class::isInstance).map(EntityInsentient.class::cast).ifPresent(ExceptionSafe.<EntityInsentient, Exception>consumer(nmsEntity -> {
			nmsEntity.targetSelector.a((PathfinderGoal)targetAI);
			Set<Object> tasks = (Set<Object>)ReflectionUtil.DEFAULT.getField(nmsEntity.targetSelector, "b");
			Optional.ofNullable(REMOVED_AI_MAP.remove(entity)).ifPresent(set -> set.forEach(ExceptionSafe.consumer(item -> ReflectionUtil.DEFAULT.invokeMethod(tasks, "add", new Class[]{Object.class}, new Object[]{item}))));
		}).caught(ExceptionSafe.PRINT_STACK_TRACE));
		return true;
	}

}
