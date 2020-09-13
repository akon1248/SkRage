package com.akon.skrage.skript.syntaxes.expressions;

import com.akon.skrage.skript.syntaxes.ExpressionFactory;
import com.akon.skrage.utils.NMSUtil;
import com.akon.skrage.utils.oldaiskeleton.OldAISkeletonManager;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.boss.BossBar;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.entity.*;
import org.bukkit.event.entity.ExplosionPrimeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

import java.nio.charset.StandardCharsets;

public class SimpleExpressions {

	static {
		ExpressionFactory.registerExpression("ExprAlwaysAngry", "always angry state", 0b011, LivingEntity.class, Boolean.class, entity -> entity instanceof PigZombie && NMSUtil.isAlwaysAngry((PigZombie)entity), (entity, bool) -> {if (entity instanceof PigZombie) NMSUtil.setAlwaysAngry((PigZombie)entity, bool);}, "ゾンビピッグマンが常に敵対的かどうか");
		ExpressionFactory.registerExpression("ExprBossBarFromEntity", "boss[ ]bar", 0b111, Entity.class, BossBar.class, NMSUtil::getBossBar, null, "エンダードラゴン、ウィザーのボスバー", "SkellettもしくはskRayFallを導入しないとボスバーを扱うことはできません");
		ExpressionFactory.registerExpression("ExprBytes", "bytes", 0b110, String.class, Number[].class, str -> ArrayUtils.toObject(str.getBytes(StandardCharsets.UTF_8)), null, "文字列をバイトのリストに変換します");
		ExpressionFactory.registerExpression("ExprDeathMessageFormat", "death message format", 0b011, LivingEntity.class, String.class, NMSUtil::getDeathMessageFormat, null, "エンティティが仮に現在死亡した場合の死亡メッセージのフォーマット");
		ExpressionFactory.registerExpression("ExprEntityName", "entity name", 0b011, Entity.class, String.class, Entity::getName, null, "エンティティの名前を取得します", "エンティティにカスタムネームが設定されていない場合通常の表示名");
		ExpressionFactory.registerExpression("ExprExplosionRadius", "explo(de|sion) (radius|yield)", 0, ExplosionPrimeEvent.class, Number.class, ExplosionPrimeEvent::getRadius, (event, number) -> event.setRadius(number.floatValue()), "explosion primeイベントでの爆発の半径");
		ExpressionFactory.registerExpression("ExprExplosionFire", "explo(de|sion) (fire|incendiary) [state]", 0, ExplosionPrimeEvent.class, Boolean.class, ExplosionPrimeEvent::getFire, ExplosionPrimeEvent::setFire, "explosion primeイベントで炎が発生するかどうか");
		ExpressionFactory.registerExpression("ExprFrom", "move(e|ing) from", 0, PlayerMoveEvent.class, Location.class, PlayerMoveEvent::getFrom, PlayerMoveEvent::setFrom, "プレイヤーが移動した時の移動元");
		ExpressionFactory.registerExpression("ExprHitBlock", "hit block", 0, ProjectileHitEvent.class, Block.class, ProjectileHitEvent::getHitBlock, null, "Projectile Hitイベントで飛び道具の衝突したブロック");
		ExpressionFactory.registerExpression("ExprHitEntity", "hit entity", 0, ProjectileHitEvent.class, Entity.class, ProjectileHitEvent::getHitEntity, null, "Projectile Hitイベントで飛び道具の衝突したEntity");
		ExpressionFactory.registerExpression("ExprItemName", "item name", 0b010, ItemStack.class, String.class, item -> CraftItemStack.asNMSCopy(item).getName(), null, "アイテムの名前を取得します", "アイテムにディスプレイネームが設定されていない場合通常の表示名が取得されます");
		ExpressionFactory.registerExpression("ExprKeepInventory", "keep inventory state", 0, PlayerDeathEvent.class, Boolean.class, PlayerDeathEvent::getKeepInventory, PlayerDeathEvent::setKeepInventory, "プレイヤーが死亡したときインベントリのアイテムを保持するかどうか");
		ExpressionFactory.registerExpression("ExprKeepLevel", "keep (level|[e]xp[erience]) state", 0, PlayerDeathEvent.class, Boolean.class, PlayerDeathEvent::getKeepLevel, PlayerDeathEvent::setKeepLevel, "プレイヤーが死亡したとき経験値を保持するかどうか");
		ExpressionFactory.registerExpression("ExprKiller", "killer", 0b011, LivingEntity.class, Entity.class, NMSUtil::getKiller, null, "Mobを殺害したと認識されているエンティティ");
		ExpressionFactory.registerExpression("ExprMaximumNoDamageTicks", "max[imum] no damage tick[s]", 0b011, LivingEntity.class, Number.class, LivingEntity::getMaximumNoDamageTicks, (entity, number) -> entity.setMaximumNoDamageTicks(number.intValue()), "ダメージを受けた後の最大無敵時間");
		ExpressionFactory.registerExpression("ExprNoDamageTicks", "no damage tick[s]", 0b011, LivingEntity.class, Number.class, LivingEntity::getNoDamageTicks, (entity, number) -> entity.setNoDamageTicks(number.intValue()), "ダメージを受けた後の無敵時間");
		ExpressionFactory.registerExpression("ExprSkeletonOldAI", "old ai state", 0b011, LivingEntity.class, Boolean.class, entity -> entity instanceof Skeleton && OldAISkeletonManager.isOldAI((Skeleton)entity), (entity, bool) -> {if (entity instanceof Skeleton) OldAISkeletonManager.setOldAI((Skeleton)entity, bool);}, "スケルトンのAIが1.8以前の状態かどうか");
		ExpressionFactory.registerExpression("ExprIllagerSpell", "[casting] spell", 0b011, LivingEntity.class, String.class, entity -> {
			if (entity instanceof Spellcaster) {
				return ((Spellcaster)entity).getSpell().name().replace("_", " ").toLowerCase();
			} else {
				return null;
			}
		}, (entity, spell) -> {
			if (entity instanceof Spellcaster) {
				boolean flag = false;
				if (spell.matches("[a-z ]+")) {
					try {
						((Spellcaster) entity).setSpell(Spellcaster.Spell.valueOf(spell.replace(" ", "_").toUpperCase()));
						flag = true;
					} catch (IllegalArgumentException ignored) {}
				}
				if (!flag) {
					((Spellcaster)entity).setSpell(Spellcaster.Spell.NONE);
				}
				try {
					((Spellcaster)entity).setSpell(Spellcaster.Spell.valueOf(spell.replace(" ", "_").toUpperCase()));
				} catch (IllegalArgumentException ex) {
					((Spellcaster)entity).setSpell(Spellcaster.Spell.NONE);
				}
			}
		}, "エヴォーカー、イリュージョナーが現在唱えている呪文(none, summon vex, fangs, wololo, disappear, blindness)");
		ExpressionFactory.registerExpression("ExprTicksLived", "ticks lived", 0b011, Entity.class, Number.class, Entity::getTicksLived, (entity, number) -> entity.setTicksLived(number.intValue()), "エンティティがワールドにスポーンしてから経過したtick数");
		ExpressionFactory.registerExpression("ExprTo", "move(e|ing) to", 0, PlayerMoveEvent.class, Location.class, PlayerMoveEvent::getTo, PlayerMoveEvent::setTo, "プレイヤーが移動した時の移動先");
	}

}