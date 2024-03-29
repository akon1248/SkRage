package com.akon.skrage.utils;


import com.comphenix.protocol.wrappers.WrappedChatComponent;
import com.google.common.collect.Lists;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import net.minecraft.server.v1_12_R1.Entity;
import net.minecraft.server.v1_12_R1.Item;
import net.minecraft.server.v1_12_R1.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarFlag;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.craftbukkit.v1_12_R1.CraftServer;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_12_R1.entity.*;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.entity.*;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.function.Function;

//主にReflectionを使用してNMSの機能を扱うメソッドを集めたクラス
public class NMSUtil {

	public static String getVersion() {
		return Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
	}

	public static Class<?> getNMSClass(String name) {
		return ReflectionUtil.getClass("net.minecraft.server." + getVersion() + "." + name);
	}

	public static Class<?> getOBCClass(String name) {
		return ReflectionUtil.getClass("org.bukkit.craftbukkit." + getVersion() + "." + name);
	}

	//ブロックを壊した時のアイテムを取得
	public static Collection<ItemStack> getDropsOfBlock(Block block, ItemStack item) {
		ArrayList<ItemStack> result = new ArrayList<>();
		net.minecraft.server.v1_12_R1.WorldServer nmsWorld = ((CraftWorld)block.getWorld()).getHandle();
		BlockPosition blockposition = new BlockPosition(block.getX(), block.getY(), block.getZ());
		Material[] cut = new Material[]{Material.DEAD_BUSH, Material.LEAVES, Material.LEAVES_2, Material.LONG_GRASS, Material.DOUBLE_PLANT, Material.WEB};
		if (block.getType() == Material.SNOW) {
			result.add(new ItemStack(Material.SNOW_BALL, 2 + block.getData()));
		} else if (item.getType() == Material.SHEARS && Arrays.asList(cut).contains(block.getType())) {
			result.add(new ItemStack(block.getType(), 1, (short)(block.getType() == Material.LEAVES ? block.getData() % 4 : block.getType() == Material.LEAVES_2 ? block.getData() % 2 : block.getData())));
		} else {
			IBlockData iblockdata = nmsWorld.getType(blockposition);
			net.minecraft.server.v1_12_R1.Block nmsBlock = iblockdata.getBlock();
			net.minecraft.server.v1_12_R1.ItemStack nmsItemStack = CraftItemStack.asNMSCopy(item);
			if (nmsBlock.getBlockData().g() && !nmsBlock.isTileEntity() && EnchantmentManager.getEnchantmentLevel(Enchantments.SILK_TOUCH, nmsItemStack) > 0) {
				Item nmsItem = Item.getItemOf(nmsBlock);
				int i = 0;
				if (nmsItem.k()) {
					i = nmsBlock.toLegacyData(iblockdata);
				}
				result.add(CraftItemStack.asBukkitCopy(new net.minecraft.server.v1_12_R1.ItemStack(nmsItem, 1, i)));
			} else {
				int fortune = EnchantmentManager.getEnchantmentLevel(Enchantments.LOOT_BONUS_BLOCKS, nmsItemStack);
				int dropCount = nmsBlock.getDropCount(fortune, nmsWorld.random);
				for (int i = 0; i < dropCount; i++) {
					if (nmsWorld.random.nextFloat() < 1) {
						Item nmsItem = nmsBlock.getDropType(iblockdata, nmsWorld.random, fortune);
						if (nmsItem != Items.a) {
							result.add(CraftItemStack.asBukkitCopy(new net.minecraft.server.v1_12_R1.ItemStack(nmsItem, 1, nmsBlock.getDropData(iblockdata))));
						}
					}
				}
			}
		}
		return Collections.unmodifiableCollection(result);
	}

	//爆発を起こしたEntityを指定して爆発を発生させる
	public static void createExplosion(org.bukkit.entity.Entity entity, Location location, float power, boolean fire, boolean isSafe) {
		net.minecraft.server.v1_12_R1.WorldServer nmsWorld = ((CraftWorld)location.getWorld()).getHandle();
		net.minecraft.server.v1_12_R1.Entity nmsEntity = entity == null ? null : ((CraftEntity)entity).getHandle();
		nmsWorld.createExplosion(nmsEntity, location.getX(), location.getY(), location.getZ(), power, fire, !isSafe);
	}

	//プレイヤーのOP権限のレベルを取得する(0~4)
	public static int getOperatorLevel(Player p) {
		DedicatedPlayerList playerList = ((CraftServer)Bukkit.getServer()).getHandle();
		OpList opList = playerList.getOPs();
		OpListEntry opListEntry = opList.get(((CraftPlayer)p).getProfile());
		return opListEntry == null ? 0 : opListEntry.a();
	}


	//消滅したEntityを再度スポーンさせる
	public static void respawnEntity(org.bukkit.entity.Entity entity, Location loc) {
		net.minecraft.server.v1_12_R1.Entity nmsEntity = ((CraftEntity)entity).getHandle();
		if (nmsEntity.dead || nmsEntity.world.getMinecraftServer().a(nmsEntity.getUniqueID()) == null) {
			if (loc != null) {
				nmsEntity.world = ((CraftWorld)loc.getWorld()).getHandle();
				nmsEntity.setLocation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
			}
			if (nmsEntity instanceof EntityLiving) {
				((EntityLiving) nmsEntity).setHealth(((EntityLiving)nmsEntity).getMaxHealth());
			}
			nmsEntity.dead = false;
			if (nmsEntity.getWorld().getMinecraftServer().a(nmsEntity.getUniqueID()) == null) {
				nmsEntity.getWorld().addEntity(nmsEntity);
			}
		}
	}

	public static org.bukkit.entity.Entity createEntity(org.bukkit.World world, EntityType type) {
		return ((CraftWorld)world).createEntity(new Location(world, 0, 0, 0), type.getEntityClass()).getBukkitEntity();
	}

	//エンダードラゴン、ウィザーのボスバーを取得します
	public static BossBar getBossBar(org.bukkit.entity.Entity entity) {
		Function<BossBattleServer, BossBar> convertToBukkitBossBar = bossBar -> {
			ArrayList<BarFlag> barFlags = new ArrayList<>();
			if (bossBar.i()) {
				barFlags.add(BarFlag.DARKEN_SKY);
			}
			if (bossBar.j()) {
				barFlags.add(BarFlag.PLAY_BOSS_MUSIC);
			}
			if (bossBar.k()) {
				barFlags.add(BarFlag.CREATE_FOG);
			}
			BarStyle style;
			switch (bossBar.style) {
				case PROGRESS:
				default:
					style = BarStyle.SOLID;
					break;
				case NOTCHED_6:
					style = BarStyle.SEGMENTED_6;
					break;
				case NOTCHED_10:
					style = BarStyle.SEGMENTED_10;
					break;
				case NOTCHED_12:
					style = BarStyle.SEGMENTED_12;
					break;
				case NOTCHED_20:
					style = BarStyle.SEGMENTED_20;
			}
			BossBar bukkitBossBar = Bukkit.createBossBar(bossBar.title.getText(), BarColor.valueOf(bossBar.color.name()), style, barFlags.toArray(new BarFlag[0]));
			ReflectionUtil.DEFAULT.setField(bukkitBossBar, "handle", bossBar);
			return bukkitBossBar;
		};
		if (entity instanceof EnderDragon) {
			Object enderDragonBattle = ReflectionUtil.DEFAULT.getField(EntityEnderDragon.class, ((CraftEnderDragon)entity).getHandle(), "bK");
			if (enderDragonBattle != null) {
				return convertToBukkitBossBar.apply((BossBattleServer)ReflectionUtil.DEFAULT.getField(enderDragonBattle, "c"));
			}
		} else if (entity instanceof Wither) {
			return convertToBukkitBossBar.apply((BossBattleServer)ReflectionUtil.DEFAULT.getField(EntityWither.class, ((CraftWither)entity).getHandle(), "bG"));
		}
		return null;
	}

	//ゾンビピッグマンのAIを取得し、常に怒っている状態かを確認する
	public static boolean isAlwaysAngry(PigZombie pigZombie) {
		PathfinderGoal angerOther = null;
		PathfinderGoal anger = null;
		for (PathfinderGoal pathfinderGoal: getTargetSelectors(pigZombie)) {
			if (pathfinderGoal.getClass().getName().equals("net.minecraft.server.v1_12_R1.EntityPigZombie$PathfinderGoalAngerOther")) {
				angerOther = pathfinderGoal;
			} else if (pathfinderGoal.getClass().getName().equals("net.minecraft.server.v1_12_R1.EntityPigZombie$PathfinderGoalAnger")) {
				anger = pathfinderGoal;
			}
		}
		return angerOther != null && anger != null;
	}

	//ゾンビピッグマンのAIを操作してAIをゾンビと同様にする
	public static void setAlwaysAngry(PigZombie pigZombie, boolean alwaysAngry) {
		EntityPigZombie entityPigZombie = ((CraftPigZombie)pigZombie).getHandle();
		if (alwaysAngry) {
			getTargetSelectors(pigZombie).stream()
				.filter(pathfinderGoal -> pathfinderGoal.getClass().getName().equals("net.minecraft.server.v1_12_R1.EntityPigZombie$PathfinderGoalAngerOther") || pathfinderGoal.getClass().getName().equals("net.minecraft.server.v1_12_R1.EntityPigZombie$PathfinderGoalAnger"))
				.forEach(entityPigZombie.targetSelector::a);
			entityPigZombie.targetSelector.a(1, new PathfinderGoalHurtByTarget(entityPigZombie, true, EntityPigZombie.class));
			entityPigZombie.targetSelector.a(2, new PathfinderGoalNearestAttackableTarget<>(entityPigZombie, EntityHuman.class, true));
			entityPigZombie.targetSelector.a(3, new PathfinderGoalNearestAttackableTarget<>(entityPigZombie, EntityIronGolem.class, true));
		}
		else if (isAlwaysAngry(pigZombie)) {
			if (isAlwaysAngry(pigZombie)) {
				getTargetSelectors(pigZombie).stream()
					.filter(pathfinderGoal -> pathfinderGoal instanceof PathfinderGoalHurtByTarget || pathfinderGoal instanceof PathfinderGoalNearestAttackableTarget)
					.forEach(entityPigZombie.targetSelector::a);
				entityPigZombie.targetSelector.a(1, (PathfinderGoal) ReflectionUtil.DEFAULT.invokeConstructor(getNMSClass("EntityPigZombie$PathfinderGoalAngerOther"), new Class[]{EntityPigZombie.class}, new Object[]{entityPigZombie}));
				entityPigZombie.targetSelector.a(2, (PathfinderGoal) ReflectionUtil.DEFAULT.invokeConstructor(getNMSClass("EntityPigZombie$PathfinderGoalAnger"), new Class[]{EntityPigZombie.class}, new Object[]{entityPigZombie}));
			}
		}
	}

	//ウィザーの左右の頭のターゲットを取得する
	public static org.bukkit.entity.Entity getWitherHeadTarget(Wither wither, int i) {
		if (i != 0 && i != 1) {
			throw new IllegalArgumentException("第二引数は0か1である必要があります");
		}
		EntityWither entityWither = ((CraftWither)wither).getHandle();
		int entityId = entityWither.m(i + 1);
		for (org.bukkit.World world: Bukkit.getWorlds()) {
			Entity nmsEntity = ((CraftWorld)world).getHandle().getEntity(entityId);
			if (nmsEntity != null) {
				return nmsEntity.getBukkitEntity();
			}
		}
		return null;
	}

	//ウィザーの左右の頭のターゲットを変更する
	public static void setWitherHeadTarget(Wither wither, int i, LivingEntity target) {
		if (i != 0 && i != 1) {
			throw new IllegalArgumentException("第二引数は0か1である必要があります");
		}
		EntityWither entityWither = ((CraftWither)wither).getHandle();
		entityWither.a(i + 1, target == null ? 0 : target.getEntityId());
	}

	//エンティティのGoalSelectorsを取得する
	public static Collection<PathfinderGoal> getGoalSelectors(LivingEntity entity) {
		ArrayList<PathfinderGoal> result = Lists.newArrayList();
		EntityLiving nmsEntity = ((CraftLivingEntity)entity).getHandle();
		if (nmsEntity instanceof EntityInsentient) {
			for (Object selectorItem: (Set<?>)ReflectionUtil.DEFAULT.getField(PathfinderGoalSelector.class, ((EntityInsentient)nmsEntity).goalSelector, "b")) {
				result.add((PathfinderGoal)ReflectionUtil.DEFAULT.getField(selectorItem, "a"));
			}
		}
		return Collections.unmodifiableCollection(result);
	}


	//エンティティのTargetSelectorsを取得する
	public static Collection<PathfinderGoal> getTargetSelectors(LivingEntity entity) {
		ArrayList<PathfinderGoal> result = Lists.newArrayList();
		EntityLiving nmsEntity = ((CraftLivingEntity)entity).getHandle();
		if (nmsEntity instanceof EntityInsentient) {
			for (Object selectorItem: (Set<?>)ReflectionUtil.DEFAULT.getField(PathfinderGoalSelector.class, ((EntityInsentient)nmsEntity).targetSelector, "b")) {
				result.add((PathfinderGoal)ReflectionUtil.DEFAULT.getField(selectorItem, "a"));
			}
		}
		return Collections.unmodifiableCollection(result);
	}

	//エンティティを殺害したと認識されているエンティティを取得する
	public static org.bukkit.entity.Entity getKiller(LivingEntity entity) {
		return Optional.of(entity).map(CraftLivingEntity.class::cast).map(CraftLivingEntity::getHandle).map(EntityLiving::getCombatTracker).map(CombatTracker::c).map(Entity::getBukkitEntity).orElse(null);
	}

	//エンティティの死亡メッセージを取得する
	public static BaseComponent[] getDeathMessage(LivingEntity entity) {
		return ComponentSerializer.parse(WrappedChatComponent.fromHandle((((CraftLivingEntity)entity).getHandle()).getCombatTracker().getDeathMessage()).getJson());
	}

	//エンティティの現在の死亡メッセージのフォーマットを取得する
	public static String getDeathMessageFormat(LivingEntity entity) {
		String language;
		EntityLiving entityLiving = (((CraftLivingEntity)entity).getHandle());
		CombatTracker combatTracker = entityLiving.getCombatTracker();
		List<CombatEntry> combatEntries = (List<CombatEntry>)ReflectionUtil.DEFAULT.getField(CombatTracker.class, combatTracker, "a");
		if (combatEntries.isEmpty()) {
			language = "death.attack.generic";
		} else {
			CombatEntry combatEntry = (CombatEntry)ReflectionUtil.DEFAULT.invokeMethod(CombatTracker.class, combatTracker, "j");
			CombatEntry combatEntry1 = combatEntries.get(combatEntries.size() - 1);
			IChatBaseComponent chatComponent = combatEntry1.h();
			Entity lastAttacker = combatEntry1.a().getEntity();
			if (combatEntry != null && combatEntry1.a() == DamageSource.FALL) {
				IChatBaseComponent killerName = combatEntry.h();
				if (combatEntry.a() != DamageSource.FALL && combatEntry.a() != DamageSource.OUT_OF_WORLD) {
					if (killerName == null || chatComponent != null && killerName.equals(chatComponent)) {
						if (chatComponent != null) {
							net.minecraft.server.v1_12_R1.ItemStack weapon = lastAttacker instanceof EntityLiving ? ((EntityLiving) lastAttacker).getItemInMainHand() : net.minecraft.server.v1_12_R1.ItemStack.a;
							if (!weapon.isEmpty() && weapon.hasName()) {
								language = "death.fell.finish.item";
							} else {
								language = "death.fell.finish";
							}
						} else {
							language = "death.fell.killer";
						}
					} else {
						Entity killer = combatEntry.a().getEntity();
						net.minecraft.server.v1_12_R1.ItemStack weapon = killer instanceof EntityLiving ? ((EntityLiving) killer).getItemInMainHand() : net.minecraft.server.v1_12_R1.ItemStack.a;
						if (!weapon.isEmpty() && weapon.hasName()) {
							language = "death.fell.assist.item";
						} else {
							language = "death.fell.assist";
						}
					}
				} else {
					language = "death.fell.accident.";
				}
			} else {
				EntityLiving entityliving1 = entityLiving.ci();
				String s = "death.attack." + combatEntry1.a().translationIndex;
				String s1 = s + ".player";
				language = entityliving1 != null && LocaleI18n.c(s1) ? s1 : s;
			}
		}
		return LocaleI18n.get(language);
	}




}
