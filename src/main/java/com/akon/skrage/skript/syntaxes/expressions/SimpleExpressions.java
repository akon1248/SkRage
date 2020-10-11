package com.akon.skrage.skript.syntaxes.expressions;

import ch.njol.skript.util.Color;
import ch.njol.skript.util.Timespan;
import com.akon.skrage.skript.syntaxes.ExpressionFactory;
import com.akon.skrage.utils.ColorConverter;
import com.akon.skrage.utils.NMSUtil;
import com.akon.skrage.utils.customstatuseffect.CSEType;
import com.akon.skrage.utils.customstatuseffect.CustomStatusEffect;
import com.akon.skrage.utils.damagesource.DamageSourceBuilder;
import com.akon.skrage.utils.exceptionsafe.ExceptionSafe;
import com.akon.skrage.utils.freeze.FreezeManager;
import com.akon.skrage.utils.oldaiskeleton.OldAISkeletonManager;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.Location;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
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
import java.util.Optional;
import java.util.Random;

public class SimpleExpressions {

	static {
		ExpressionFactory.registerExpression("ExprAlwaysAngry", "always angry state", 0b011, LivingEntity.class, Boolean.class, entity -> entity instanceof PigZombie && NMSUtil.isAlwaysAngry((PigZombie)entity), (entity, bool) -> Optional.of(entity).filter(PigZombie.class::isInstance).map(PigZombie.class::cast).ifPresent(pigZombie -> NMSUtil.setAlwaysAngry(pigZombie, bool)), "ゾンビピッグマンが常に敵対的かどうか");
		ExpressionFactory.registerExpression("ExprAttributeBaseValue", "attribute base value", 0b111, AttributeInstance.class, Number.class, AttributeInstance::getBaseValue, (attribute, num) -> attribute.setBaseValue(num.doubleValue()), "Attributeの基本の値");
		ExpressionFactory.registerExpression("ExprAttributeModifierAmount", "[attribute[ ]]modifier amount", 0b011, AttributeModifier.class, Number.class, AttributeModifier::getAmount, null, "AttributeModifierが適用する値");
		ExpressionFactory.registerExpression("ExprAttributeModifierName", "[attribute[ ]]modifier name", 0b011, AttributeModifier.class, String.class, AttributeModifier::getName, null, "AttributeModifierの名前");
		ExpressionFactory.registerExpression("ExprAttributeModifierOperation", "[attribute[ ]]modifier operation", 0b011, AttributeModifier.class, Number.class, modifier -> modifier.getOperation().ordinal(), null, "AttributeModifierが適当されるときの値の操作(0: 加算, 1: 1+x倍(値が0.5なら1.5倍される), 2: 乗算)");
		ExpressionFactory.registerExpression("ExprAttributeModifierUUID", "[attribute[ ]]modifier uuid", 0b011, AttributeModifier.class, String.class, modifier -> modifier.getUniqueId().toString(), null, "AttributeModifierのUUID");
		ExpressionFactory.registerExpression("ExprAttributeValue", "attribute value", 0b111 , AttributeInstance.class, Number.class, AttributeInstance::getValue, null, "Attributeの値");
		ExpressionFactory.registerExpression("ExprBossBarFromEntity", "boss[ ]bar", 0b111, Entity.class, BossBar.class, NMSUtil::getBossBar, null, "エンダードラゴン、ウィザーのボスバー", "SkellettもしくはskRayFallを導入しないとボスバーを扱うことはできません");
		ExpressionFactory.registerExpression("ExprBytes", "bytes", 0b110, String.class, Number[].class, str -> ArrayUtils.toObject(str.getBytes(StandardCharsets.UTF_8)), null, "文字列をバイトのリストに変換します");
		ExpressionFactory.registerExpression("ExprErrorCause", "error cause", 0b011, Throwable.class, Throwable.class, Throwable::getCause, null, "エラーの原因となった別のエラー");
		ExpressionFactory.registerExpression("ExprErrorMessage", "error message", 0b011, Throwable.class, String.class, Throwable::getMessage, null, "エラーメッセージ");
		ExpressionFactory.registerExpression("ExprErrorName", "error name", 0b011, Throwable.class, String.class, throwable -> throwable.getClass().getName(), null, "エラー名");
		ExpressionFactory.registerExpression("ExprChatColorCode", "chat color code", 0b100, Color.class, String.class, Color::getFormattedChat, null, "Colorからテキストのカラーコードに変換します");
		ExpressionFactory.registerExpression("ExprCSEAmplifier", "(cse|custom[ ]status[ ]effect) (amplifier|level|tier)", 0b011, CustomStatusEffect.class, Number.class, CustomStatusEffect::getAmplifier, null, "CustomStatusEffectの効果の強さ");
		ExpressionFactory.registerExpression("ExprCSEDuration", "(cse|custom[ ]status[ ]effect) duration", 0b011, CustomStatusEffect.class, Number.class, CustomStatusEffect::getDuration, (effect, num) -> effect.setDuration(num.intValue()), "CustomStatusEffectの効果時間(tick)");
		ExpressionFactory.registerExpression("ExprCSEType", "(cse|custom[ ]status[ ]effect) type", 0b011, CustomStatusEffect.class, CSEType.class, CustomStatusEffect::getType, null, "CustomStatusEffectのタイプ");
		ExpressionFactory.registerExpression("ExprCSETypeColor", "(cse|custom[ ]status[ ]effect) type color", 0b011, CSEType.class, Color.class, type -> ColorConverter.toSkriptColor(type.getColor()), null, "CustomStatusEffectのタイプから色を取得します");
		ExpressionFactory.registerExpression("ExprCSETypeId", "(cse|custom[ ]status[ ]effect) type id", 0b011, CSEType.class, String.class, CSEType::getId, null, "CustomStatusEffectのタイプからIDを取得します");
		ExpressionFactory.registerExpression("ExprCSETypeName", "(cse|custom[ ]status[ ]effect) type name", 0b011, CSEType.class, String.class, CSEType::getName, null, "CustomStatusEffectのタイプから名前を取得します");
		/*DamageSource expressions start*/
		ExpressionFactory.registerExpression("ExprDamageSourceDifficultyScaled", "difficulty scaled", 0b011, DamageSourceBuilder.class, Boolean.class, DamageSourceBuilder::isDifficultyScaled, ExceptionSafe.biConsumer(DamageSourceBuilder::setDifficultyScaled), "難易度によって受けるダメージが変化するかどうか");
		ExpressionFactory.registerExpression("ExprDamageSourceEntity", "damage[ ]source (entity|attacker)", 0b011, DamageSourceBuilder.class, Entity.class, DamageSourceBuilder::getEntity, ExceptionSafe.biConsumer(DamageSourceBuilder::setEntity), "ダメージを与えるエンティティ");
		ExpressionFactory.registerExpression("ExprDamageSourceEntityProjectile", "damage[ ]source projectile", 0b011, DamageSourceBuilder.class, Entity.class, DamageSourceBuilder::getProjectile, ExceptionSafe.biConsumer(DamageSourceBuilder::setProjectile), "ダメージを与える飛び道具");
		ExpressionFactory.registerExpression("ExprDamageSourceExhaustion", "exhaustion (cost|damage)", 0b011, DamageSourceBuilder.class, Number.class, DamageSourceBuilder::getExhaustion, ExceptionSafe.biConsumer((damageSource, num) -> damageSource.setExhaustion(num.floatValue())), "ダメージを受けたときの疲労度");
		ExpressionFactory.registerExpression("ExprDamageSourceExplosion", "damage[ ]source explosion", 0b011, DamageSourceBuilder.class, Boolean.class, DamageSourceBuilder::isExplosion, ExceptionSafe.biConsumer(DamageSourceBuilder::setExplosion), "爆発のダメージかどうか");
		ExpressionFactory.registerExpression("ExprDamageSourceFire", "damage[ ]source fire", 0b011, DamageSourceBuilder.class, Boolean.class, DamageSourceBuilder::isFire, ExceptionSafe.biConsumer(DamageSourceBuilder::setFire), "炎のダメージかどうか");
		ExpressionFactory.registerExpression("ExprDamageSourceIgnoreArmor", "ignore armor", 0b011, DamageSourceBuilder.class, Boolean.class, DamageSourceBuilder::isIgnoreArmor, ExceptionSafe.biConsumer(DamageSourceBuilder::setIgnoreArmor), "防具を無視してダメージを与えるかどうか");
		ExpressionFactory.registerExpression("ExprDamageSourceIgnoreCreativeMode", "ignore creative[[ ]mode]", 0b011, DamageSourceBuilder.class, Boolean.class, DamageSourceBuilder::isIgnoreCreativeMode, ExceptionSafe.biConsumer(DamageSourceBuilder::setIgnoreCreativeMode), "クリエイティブモードの無敵を無視してダメージを与えるかどうか");
		ExpressionFactory.registerExpression("ExprDamageSourceIgnoreNoDamageTicks", "ignore no damage ticks", 0b011, DamageSourceBuilder.class, Boolean.class, DamageSourceBuilder::isIgnoreNoDamageTicks, ExceptionSafe.biConsumer(DamageSourceBuilder::setIgnoreNoDamageTicks), "無敵時間を無視してダメージを与えるかどうか");
		ExpressionFactory.registerExpression("ExprDamageSourceMagic", "damage[ ]source magic", 0b011, DamageSourceBuilder.class, Boolean.class, DamageSourceBuilder::isMagic, ExceptionSafe.biConsumer(DamageSourceBuilder::setMagic), "魔法ダメージかどうか");
		ExpressionFactory.registerExpression("ExprDamageSourcePreventKnockback", "prevent knockback", 0b011, DamageSourceBuilder.class, Boolean.class, DamageSourceBuilder::isPreventKnockback, ExceptionSafe.biConsumer(DamageSourceBuilder::setPreventKnockback), "ダメージを与えたときのノックバックを防ぐかどうか");
		ExpressionFactory.registerExpression("ExprDamageSourceProjectile", "damage[ ]source projectile damage", 0b011, DamageSourceBuilder.class, Boolean.class, DamageSourceBuilder::isProjectile, ExceptionSafe.biConsumer(DamageSourceBuilder::setProjectile), "飛び道具によるダメージかどうか");
		ExpressionFactory.registerExpression("ExprDamageSourceStarvation", "damage[ ]source starvation", 0b011, DamageSourceBuilder.class, Boolean.class, DamageSourceBuilder::isStarvation, ExceptionSafe.biConsumer(DamageSourceBuilder::setStarvation), "空腹ダメージかどうか");
		ExpressionFactory.registerExpression("ExprDamageSourceSweep", "damage[ ]source sweep", 0b011, DamageSourceBuilder.class, Boolean.class, DamageSourceBuilder::isSweep, ExceptionSafe.biConsumer(DamageSourceBuilder::setSweep), "薙ぎ払い攻撃によるダメージかどうか");
		ExpressionFactory.registerExpression("ExprDamageSourceThorns", "damage[ ]source thorns", 0b011, DamageSourceBuilder.class, Boolean.class, DamageSourceBuilder::isThorns, ExceptionSafe.biConsumer(DamageSourceBuilder::setThorns), "棘の鎧のダメージかどうか");
		ExpressionFactory.registerExpression("ExprDamageSourceType", "damage[ ]source type", 0b011, DamageSourceBuilder.class, String.class, DamageSourceBuilder::getType, ExceptionSafe.biConsumer(DamageSourceBuilder::setType), "ダメージのタイプ");
		/*DamageSource expressions end*/
		ExpressionFactory.registerExpression("ExprDeathMessageFormat", "death message format", 0b011, LivingEntity.class, String.class, NMSUtil::getDeathMessageFormat, null, "エンティティが仮に現在死亡した場合の死亡メッセージのフォーマット");
		ExpressionFactory.registerExpression("ExprEntityName", "entity name", 0b011, Entity.class, String.class, Entity::getName, null, "エンティティの名前を取得します", "エンティティにカスタムネームが設定されていない場合通常の表示名");
		ExpressionFactory.registerExpression("ExprExplosionFire", "explo(de|sion) (fire|incendiary) [state]", 0, ExplosionPrimeEvent.class, Boolean.class, ExplosionPrimeEvent::getFire, ExplosionPrimeEvent::setFire, "explosion primeイベントで炎が発生するかどうか");
		ExpressionFactory.registerExpression("ExprExplosionRadius", "explo(de|sion) (radius|yield)", 0, ExplosionPrimeEvent.class, Number.class, ExplosionPrimeEvent::getRadius, (event, number) -> event.setRadius(number.floatValue()), "explosion primeイベントでの爆発の半径");
		ExpressionFactory.registerExpression("ExprFreeze", "fr(eeze|oze[n]) state", 0b011, Player.class, Boolean.class, FreezeManager::isFrozen, (player, bool) -> {if (bool) FreezeManager.freeze(player); else FreezeManager.unfreeze(player);}, "プレイヤーの硬直状態");
		ExpressionFactory.registerExpression("ExprFrom", "move(e|ing) from", 0, PlayerMoveEvent.class, Location.class, PlayerMoveEvent::getFrom, PlayerMoveEvent::setFrom, "プレイヤーが移動した時の移動元");
		ExpressionFactory.registerExpression("ExprHitBlock", "hit block", 0, ProjectileHitEvent.class, Block.class, ProjectileHitEvent::getHitBlock, null, "Projectile Hitイベントで飛び道具の衝突したブロック");
		ExpressionFactory.registerExpression("ExprHitEntity", "hit entity", 0, ProjectileHitEvent.class, Entity.class, ProjectileHitEvent::getHitEntity, null, "Projectile Hitイベントで飛び道具の衝突したEntity");
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
						((Spellcaster)entity).setSpell(Spellcaster.Spell.valueOf(spell.replace(" ", "_").toUpperCase()));
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
		ExpressionFactory.registerExpression("ExprItemName", "item name", 0b010, ItemStack.class, String.class, item -> CraftItemStack.asNMSCopy(item).getName(), null, "アイテムの名前を取得します", "アイテムにディスプレイネームが設定されていない場合通常の表示名が取得されます");
		ExpressionFactory.registerExpression("ExprKeepInventory", "keep inventory state", 0, PlayerDeathEvent.class, Boolean.class, PlayerDeathEvent::getKeepInventory, PlayerDeathEvent::setKeepInventory, "プレイヤーが死亡したときインベントリのアイテムを保持するかどうか");
		ExpressionFactory.registerExpression("ExprKeepLevel", "keep (level|[e]xp[erience]) state", 0, PlayerDeathEvent.class, Boolean.class, PlayerDeathEvent::getKeepLevel, PlayerDeathEvent::setKeepLevel, "プレイヤーが死亡したとき経験値を保持するかどうか");
		ExpressionFactory.registerExpression("ExprKiller", "killer", 0b011, LivingEntity.class, Entity.class, NMSUtil::getKiller, null, "Mobを殺害したと認識されているエンティティ");
		ExpressionFactory.registerExpression("ExprMaximumNoDamageTicks", "max[imum] no damage tick[s]", 0b011, LivingEntity.class, Number.class, LivingEntity::getMaximumNoDamageTicks, (entity, number) -> entity.setMaximumNoDamageTicks(number.intValue()), "ダメージを受けた後の最大無敵時間");
		ExpressionFactory.registerExpression("ExprNoDamageTicks", "no damage tick[s]", 0b011, LivingEntity.class, Number.class, LivingEntity::getNoDamageTicks, (entity, number) -> entity.setNoDamageTicks(number.intValue()), "ダメージを受けた後の無敵時間");
		ExpressionFactory.registerExpression("ExprPlayerTime", "(client|player) time", 0b011, Player.class, Number.class, Player::getPlayerTimeOffset, (player, num) -> player.setPlayerTime(num.longValue(), player.isPlayerTimeRelative()), "プレイヤーの時間を変更します");
		ExpressionFactory.registerExpression("ExprPlayerTimeRelative", "(client|player) time relative", 0b011, Player.class, Boolean.class, Player::isPlayerTimeRelative, (player, bool) -> player.setPlayerTime(player.getPlayerTimeOffset(), bool), "プレイヤーの時間がサーバーの時間と相対的かどうか");
		ExpressionFactory.registerExpression("ExprRandomBoolean", "random boolean", 0b100, Random.class, Boolean.class, Random::nextBoolean, null, "ランダムな真偽値を生成します");
		ExpressionFactory.registerExpression("ExprRandomGaussian", "random gauss[ian]", 0b100, Random.class, Number.class, Random::nextGaussian, null, "平均0、標準偏差1.0のガウス分布の乱数を生成します");
		ExpressionFactory.registerExpression("ExprRandomLong", "random long", 0b100, Random.class, Number.class, Random::nextLong, null, "ランダムな整数(64bitの範囲)を生成します");
		ExpressionFactory.registerExpression("ExprRandomNumber", "random number", 0b100, Random.class, Number.class, Random::nextDouble, null, "ランダムな0~1の小数を生成します");
		ExpressionFactory.registerExpression("ExprSimpleErrorName", "simple error name", 0b011, Throwable.class, String.class, throwable -> throwable.getClass().getSimpleName(), null, "エラーのシンプルな名前");
		ExpressionFactory.registerExpression("ExprSkeletonOldAI", "old ai state", 0b011, LivingEntity.class, Boolean.class, entity -> entity instanceof Skeleton && OldAISkeletonManager.isOldAI((Skeleton)entity), (entity, bool) -> Optional.of(entity).filter(Skeleton.class::isInstance).map(Skeleton.class::cast).ifPresent(skeleton -> OldAISkeletonManager.setOldAI(skeleton, bool)), "スケルトンのAIが1.8以前の状態かどうか");
		ExpressionFactory.registerExpression("ExprStackTrace", "stack[ ]trace", 0b111, Throwable.class, StackTraceElement[].class, Throwable::getStackTrace, null, "エラーのスタックトレース");
		ExpressionFactory.registerExpression("ExprStackTraceClass", "stack[ ]trace class", 0b011, StackTraceElement.class, String.class, StackTraceElement::getClassName, null, "スタックトーレスのクラス名");
		ExpressionFactory.registerExpression("ExprStackTraceFile", "stack[ ]trace file", 0b011, StackTraceElement.class, String.class, StackTraceElement::getFileName, null, "スタックトレースのファイル名");
		ExpressionFactory.registerExpression("ExprStackTraceLine", "stack[ ]trace line", 0b011, StackTraceElement.class, Number.class, StackTraceElement::getLineNumber, null, "スタックトーレスの行数");
		ExpressionFactory.registerExpression("ExprStackTraceMethod", "stack[ ]trace method", 0b011, StackTraceElement.class, String.class, StackTraceElement::getMethodName, null, "スタックトレースのメソッド名");
		ExpressionFactory.registerExpression("ExprTicksLived", "ticks lived", 0b011, Entity.class, Number.class, Entity::getTicksLived, (entity, number) -> entity.setTicksLived(number.intValue()), "エンティティがワールドにスポーンしてから経過したtick数");
		ExpressionFactory.registerExpression("ExprTimespanTicks", "ticks", 0b110, Timespan.class, Number.class, Timespan::getTicks_i, null, "timespanをtick数に変換します");
		ExpressionFactory.registerExpression("ExprTimespanFromTicks", "timespan", 0b100, Number.class, Timespan.class, num -> Timespan.fromTicks_i(num.longValue()), null, "tick数からtimespanを作成します");
		ExpressionFactory.registerExpression("ExprTo", "move(e|ing) to", 0, PlayerMoveEvent.class, Location.class, PlayerMoveEvent::getTo, PlayerMoveEvent::setTo, "プレイヤーが移動した時の移動先");
	}

}