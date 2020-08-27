package com.akon.skrage.syntaxes.expressions;

import com.akon.skrage.syntaxes.ExpressionFactory;
import com.akon.skrage.utils.NMSUtil;
import com.akon.skrage.utils.anvilgui.AnvilGUI;
import com.akon.skrage.utils.oldaiskeleton.OldAISkeletonManager;
import com.akon.skrage.utils.skin.Skin;
import com.akon.skrage.utils.skin.SkinManager;
import com.shampaggon.crackshot.CSDirector;
import com.shampaggon.crackshot.events.WeaponDamageEntityEvent;
import com.shampaggon.crackshot.events.WeaponReloadEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.block.Block;
import org.bukkit.boss.BossBar;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.entity.*;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

public class SimpleExpressions {

	static {
		if (Bukkit.getPluginManager().isPluginEnabled("ProtocolLib")) {
			ExpressionFactory.registerExpression("ExprAnvilGUI", "anvil[ ]gui", Player.class, AnvilGUI.class, AnvilGUI::getAnvilGUI, null, "プレイヤーに表示されているAnvilGUI");
			ExpressionFactory.registerExpression("ExprAnvilGUIName", "anvil[ ]gui name", AnvilGUI.class, String.class, AnvilGUI::getName, null, "金床のGUIの名前");
			ExpressionFactory.registerExpression("ExprSkin", "skin", OfflinePlayer.class, Skin.class, SkinManager::getSkin, null, "プレイヤーが使用しているスキン");
		}
		if (Bukkit.getPluginManager().isPluginEnabled("CrackShot")) {
			ExpressionFactory.registerExpression("ExprBulletWeaponName", "[(crackshot|cs)] weapon [internal[ ]]name", Entity.class, String.class, entity -> {
				if (entity instanceof Projectile && entity.hasMetadata("projParentNode")) {
					return entity.getMetadata("projParentNode").get(0).asString();
				} else if (entity instanceof TNTPrimed && entity.hasMetadata("CS_Potex")) {
					return entity.getMetadata("CS_Potex").get(0).asString();
				}
				return null;
			}, null, "CrackShotの弾丸もしくは爆弾から武器の登録名を取得します");
			ExpressionFactory.registerExpression("ExprCSDamage", "(crackshot|cs) [weapon] damage", WeaponDamageEntityEvent.class, Number.class, WeaponDamageEntityEvent::getDamage, (event, number) -> event.setDamage(number.doubleValue()), "CrackShot DamageイベントでEntityの受けた防具等によって軽減される前のダメージ");
			ExpressionFactory.registerExpression("ExprCSDamager", "(crackshot|cs) (attacker|damager)", WeaponDamageEntityEvent.class, Entity.class, WeaponDamageEntityEvent::getDamager, null, "CrackShot Damageイベントでエンティティにダメージを与えたエンティティ");
			ExpressionFactory.registerExpression("ExprCSWeaponDisplayName", "weapon display[ ]name", String.class, String.class, str -> ((CSDirector)Bukkit.getServer().getPluginManager().getPlugin("CrackShot")).getString(str + ".Item_Information.Item_Name"), null, "CrackShotの武器の表示名");
			ExpressionFactory.registerExpression("ExprReloadDuration", "reload duration", WeaponReloadEvent.class, Number.class, WeaponReloadEvent::getReloadDuration, (event, number) -> event.setReloadDuration(number.intValue()), "CrackShot Reload Startイベントでの武器のリロード時間");
		}
		ExpressionFactory.registerExpression("ExprBossBarFromEntity", "boss[ ]bar", Entity.class, BossBar.class, NMSUtil::getBossBar, null, "エンダードラゴン、ウィザーのボスバー", "SkellettもしくはskRayFallを導入しないとボスバーを扱うことはできません");
		ExpressionFactory.registerExpression("ExprDeathMessageFormat", "death message format", LivingEntity.class, String.class, NMSUtil::getDeathMessageFormat, null, "エンティティが仮に現在死亡した場合の死亡メッセージのフォーマット");
		ExpressionFactory.registerExpression("ExprEntityName", "entity name", Entity.class, String.class, Entity::getName, null, "エンティティの名前を取得します", "エンティティにカスタムネームが設定されていない場合通常の表示名");
		ExpressionFactory.registerExpression("ExprExplosionRadius", "explo(de|sion) (radius|yield)", ExplosionPrimeEvent.class, Number.class, ExplosionPrimeEvent::getRadius, (event, number) -> event.setRadius(number.floatValue()), "explosion primeイベントでの爆発の半径");
		ExpressionFactory.registerExpression("ExprExplosionFire", "explo(de|sion) (fire|incendiary) [state]", ExplosionPrimeEvent.class, Boolean.class, ExplosionPrimeEvent::getFire, ExplosionPrimeEvent::setFire, "explosion primeイベントで炎が発生するかどうか");
		ExpressionFactory.registerExpression("ExprFrom", "move(e|ing) from", PlayerMoveEvent.class, Location.class, PlayerMoveEvent::getFrom, PlayerMoveEvent::setFrom, "プレイヤーが移動した時の移動元");
		ExpressionFactory.registerExpression("ExprHitBlock", "hit block", ProjectileHitEvent.class, Block.class, ProjectileHitEvent::getHitBlock, null, "Projectile Hitイベントで飛び道具の衝突したブロック");
		ExpressionFactory.registerExpression("ExprHitEntity", "hit entity", ProjectileHitEvent.class, Entity.class, ProjectileHitEvent::getHitEntity, null, "Projectile Hitイベントで飛び道具の衝突したEntity");
		ExpressionFactory.registerExpression("ExprItemName", "item name", ItemStack.class, String.class, item -> CraftItemStack.asNMSCopy(item).getName(), null, "アイテムの名前を取得します", "アイテムにディスプレイネームが設定されていない場合通常の表示名が取得されます");
		ExpressionFactory.registerExpression("ExprKeepInventory", "keep inventory state", PlayerDeathEvent.class, Boolean.class, PlayerDeathEvent::getKeepInventory, PlayerDeathEvent::setKeepInventory, "プレイヤーが死亡したときインベントリのアイテムを保持するかどうか");
		ExpressionFactory.registerExpression("ExprKeepLevel", "keep (level|[e]xp[erience]) state", PlayerDeathEvent.class, Boolean.class, PlayerDeathEvent::getKeepLevel, PlayerDeathEvent::setKeepLevel, "プレイヤーが死亡したとき経験値を保持するかどうか");
		ExpressionFactory.registerExpression("ExprKiller", "killer", LivingEntity.class, Entity.class, NMSUtil::getKiller, null, "Mobを殺害したと認識されているエンティティ");
		ExpressionFactory.registerExpression("ExprMaximumNoDamageTicks", "max[imum] no damage tick[s]", LivingEntity.class, Number.class, LivingEntity::getMaximumNoDamageTicks, (entity, number) -> entity.setMaximumNoDamageTicks(number.intValue()), "ダメージを受けた後の最大無敵時間");
		ExpressionFactory.registerExpression("ExprNoDamageTicks", "no damage tick[s]", LivingEntity.class, Number.class, LivingEntity::getNoDamageTicks, (entity, number) -> entity.setNoDamageTicks(number.intValue()), "ダメージを受けた後の無敵時間");
		ExpressionFactory.registerExpression("ExprSkeletonOldAI", "old ai state", LivingEntity.class, Boolean.class, entity -> entity instanceof Skeleton && OldAISkeletonManager.isOldAI((Skeleton)entity), (entity, bool) -> {if (entity instanceof Skeleton) OldAISkeletonManager.setOldAI((Skeleton)entity, bool);}, "スケルトンのAIが1.8以前の状態かどうか");
		ExpressionFactory.registerExpression("ExprTicksLived", "ticks lived", Entity.class, Number.class, Entity::getTicksLived, (entity, number) -> entity.setTicksLived(number.intValue()), "エンティティがワールドにスポーンしてから経過したtick数");
		ExpressionFactory.registerExpression("ExprTo", "move(e|ing) to", PlayerMoveEvent.class, Location.class, PlayerMoveEvent::getTo, PlayerMoveEvent::setTo, "プレイヤーが移動した時の移動先");
	}

}