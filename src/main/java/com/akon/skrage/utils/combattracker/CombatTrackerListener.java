package com.akon.skrage.utils.combattracker;

import com.akon.skrage.SkRage;
import net.minecraft.server.v1_12_R1.CombatTracker;
import net.minecraft.server.v1_12_R1.EntityLiving;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftLivingEntity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.server.PluginDisableEvent;
import org.bukkit.event.server.PluginEnableEvent;
import org.bukkit.event.world.ChunkLoadEvent;

import java.util.Arrays;

public class CombatTrackerListener implements Listener {

	public static final CombatTrackerListener INSTANCE = new CombatTrackerListener();

	private CombatTrackerListener() {}

	@EventHandler
	public void onEnable(PluginEnableEvent e) {
		if (e.getPlugin() == SkRage.getInstance()) {
			Bukkit.getWorlds().stream().flatMap(world -> world.getEntitiesByClass(LivingEntity.class).stream()).forEach(CombatTrackerListener::replaceCombatTracker);
		}
	}

	@EventHandler
	public void onChunkLoad(ChunkLoadEvent e) {
		Arrays.stream(e.getChunk().getEntities())
			.filter(LivingEntity.class::isInstance)
			.map(LivingEntity.class::cast)
			.forEach(CombatTrackerListener::replaceCombatTracker);
	}

	@EventHandler
	public void onSpawn(EntitySpawnEvent e) {
		if (e.getEntity() instanceof LivingEntity) {
			replaceCombatTracker((LivingEntity)e.getEntity());
		}
	}

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		replaceCombatTracker(e.getPlayer());
	}

	@EventHandler
	public void onRespawn(PlayerRespawnEvent e) {
		Bukkit.getScheduler().runTask(SkRage.getInstance(), () -> replaceCombatTracker(e.getPlayer()));
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onDeath(EntityDeathEvent e) {
		CombatTracker combatTracker = (((CraftLivingEntity)e.getEntity()).getHandle()).getCombatTracker();
		if (combatTracker instanceof CustomCombatTracker) {
			((CustomCombatTracker)combatTracker).reset();
		}
	}

	@EventHandler
	public void onDisable(PluginDisableEvent e) {
		if (e.getPlugin() == SkRage.getInstance()) {
			Bukkit.getWorlds().stream().flatMap(world -> world.getEntitiesByClass(LivingEntity.class).stream()).forEach(entity -> {
				EntityLiving entityLiving = (((CraftLivingEntity)entity).getHandle());
				if (entityLiving.combatTracker instanceof CustomCombatTracker) {
					entityLiving.combatTracker = ((CustomCombatTracker)entityLiving.combatTracker).getOrigin();
				}
			});
		}
	}

	private static void replaceCombatTracker(LivingEntity entity) {
		EntityLiving entityLiving = (((CraftLivingEntity)entity).getHandle());
		entityLiving.combatTracker = CustomCombatTracker.fromOrigin(entityLiving.getCombatTracker());
	}

}
