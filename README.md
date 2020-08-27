# SkRage
 Skript Addon

Document
```
Events:
	On anvil gui close:
		ID: anvil_gui_close
		Description:
			金床のGUIが閉じられたとき
		Patterns:
			[on] anvil[ ]gui close
		Event values:
			event-world
			event-anvilinv
			event-player
		Cancellable: false
	On anvil gui done:
		ID: anvil_gui_done
		Description:
			金床のGUIにプレイヤーがテキストを入力し終わったとき
		Patterns:
			[on] anvil[ ]gui done
		Event values:
			event-world
			event-anvilinv
			event-string
			event-player
		Cancellable: false
	On anvil gui open:
		ID: anvil_gui_open
		Description:
			金床のGUIが開かれるとき
		Patterns:
			[on] anvil[ ]gui open
		Event values:
			event-world
			event-anvilinv
			event-player
		Cancellable: true
	On combust by sunlight:
		ID: combust_by_sunlight
		Description:
			ゾンビやスケルトンなどが日光によって燃えたとき
		Patterns:
			[on] combust[ing] by sunlight
		Event values:
			event-world
			event-entity
		Cancellable: true
	On console log:
		ID: console_log
		Description:
			コンソールにログが出力されたとき
		Patterns:
			[on] console log
		Event values:
			event-loglevel
			event-string
		Cancellable: true
	On cs damage:
		ID: cs_damage
		Description:
			CrackShotの武器によってEntityがダメージを受けたとき
		Patterns:
			[on] (crackshot|cs) damage
		Event values:
			event-entity
			event-string
			event-projectile
			event-player
		Cancellable: true
	On cs pre shoot:
		ID: cs_pre_shoot
		Description:
			CrackShotの武器が発射される直前
		Patterns:
			[on] (crackshot|cs) pre shoot
		Event values:
			event-string
			event-player
		Cancellable: true
	On cs prepare shoot:
		ID: cs_prepare_shoot
		Description:
			CrackShotの武器が発射されようとしたとき
		Patterns:
			[on] (crackshot|cs) prepare shoot
		Event values:
			event-string
			event-player
		Cancellable: true
	On cs shoot:
		ID: cs_shoot
		Description:
			CrackShotの武器が発射されるとき
		Patterns:
			[on] (crackshot|cs) shoot
		Event values:
			event-entity
			event-string
			event-projectile
			event-player
		Cancellable: false
	On plugin message receiving:
		ID: plugin_message_receiving
		Patterns:
			[on] plugin message receiv(e|ing)
		Event values:
			event-world
			event-player
		Cancellable: true
	On plugin message sending:
		ID: plugin_message_sending
		Patterns:
			[on] plugin message send[ing]
		Event values:
			event-world
			event-player
		Cancellable: true
	On reload complete:
		ID: reload_complete
		Description:
			CrackShotの武器のリロードが完了したとき
		Patterns:
			[on] [(crackshot|cs)] reload complete
		Event values:
			event-string
			event-player
		Cancellable: false
	On reload start:
		ID: reload_start
		Description:
			CrackShotの武器のリロードを開始したとき
		Patterns:
			[on] [(crackshot|cs)] reload [start]
		Event values:
			event-string
			event-player
		Cancellable: false
	On scope:
		ID: scope
		Description:
			CrackShotの武器のスコープを覗いたとき
		Patterns:
			[on] [(crackshot|cs)] scope
		Event values:
			event-string
			event-player
		Cancellable: true
	On unscope:
		ID: unscope
		Description:
			CrackShotの武器でスコープ状態を解除したとき
		Patterns:
			[on] [(crackshot|cs)] unscope
		Event values:
			event-string
			event-player
		Cancellable: true
Conditions:
	CondBackstab:
		ID: CondBackstab
		Description:
			不意打ちかどうかを判定します
		Patterns:
			event was [a] backstab
	CondCritical:
		ID: CondCritical
		Description:
			クリティカルかどうかを判定します
		Patterns:
			event was [a] critical
	CondHeadShot:
		ID: CondHeadShot
		Description:
			ヘッドショットかどうかを判定します
		Patterns:
			event was [a] head[ ]shot
	CondStringEquals:
		ID: CondStringEquals
		Description:
			大文字と小文字を無視せずに文字列を比較します
		Patterns:
			%string% equals strict %string%
	CondWeaponBullet:
		ID: CondWeaponBullet
		Description:
			EntityがCrackShotの武器によって放たれたものかどうかを判定します
		Patterns:
			%entity% is [(crackshot|cs)] weapon bullet
Effects:
	EffBetterBreakNaturally:
		ID: EffBetterBreakNaturally
		Description:
			指定されたアイテムの幸運やシルクタッチを考慮してブロックを破壊し、アイテムをドロップさせます
		Patterns:
			better break %block% [naturally] [(with|using) %-itemstack%]
	EffBetterExplosion:
		ID: EffBetterExplosion
		Description:
			通常のExplosion構文に爆発時の炎上、爆発を引き起こしたエンティティの指定を可能にしたものです
		Patterns:
			(create|make) [(a|an)] (1¦safe|) explosion (of|with) (force|strength|power) %number% at %location% [fire %-boolean%]
			(create|make) [(a|an)] %entity% (1¦safe|) explosion (of|with) (force|strength|power) %number% at %location% [fire %-boolean%]
	EffBetterPotionEffect:
		ID: EffBetterPotionEffect
		Description:
			ポーション効果をエンティティに追加します
			パーティクルを表示するかどうかとパーティクルを見えずらくするかどうかの設定が可能です
			モードを変更することでポーション効果を付与する条件が設定可能です(デフォルトのモード: default)
			default: 対象のエンティティが指定された種類のポーション効果を持っていなかった場合
			force: 条件なしに強制的に付与します
			vanilla: バニラでポーション効果が付与される条件と同じです
		Patterns:
			(add|apply) %potioneffecttype% [potion] [[[of] tier] %number%] to %livingentities% [for %-timespan%] [ambient %-boolean%] [hide particle[s] %-boolean%] [mode (0¦default|1¦force|2¦vanilla)]
	EffBlockBreakEffect:
		ID: EffBlockBreakEffect
		Description:
			ブロックが壊れた時のエフェクトを発生させます
		Patterns:
			show %itemtype% (break[ing]|destroy) effect at %location% [for %-players%]
	EffBlockBreakEffect:
		ID: EffBlockBreakEffect
		Description:
			ブロックが壊れた時のエフェクトを発生させます
		Patterns:
			make %entities% glow[ing] for %players%
			make %entities% unglow[ing] for %players%
	EffBroadcastDeathMessage:
		ID: EffBroadcastDeathMessage
		Patterns:
			broadcast death message of %livingentity%
	EffCloseAnvilGUI:
		ID: EffCloseAnvilGUI
		Description:
			金床のGUIを閉じます
		Patterns:
			close anvil[ ]gui %anvilinv%
	EffDespawn:
		ID: EffDespawn
		Description:
			エンティティをデスポーンさせます
		Patterns:
			(despawn|remove) %entities%
	EffOpenAnvilGUI:
		ID: EffOpenAnvilGUI
		Description:
			プレイヤーに金床のGUIを表示します
		Patterns:
			open anvil[ ]gui named %string% with icon %itemtype% text %string% (to|for) %player%
	EffPotionBreakEffect:
		ID: EffPotionBreakEffect
		Description:
			ポーションが割れた時のエフェクトを発生させます
		Patterns:
			show (1¦splash|) potion break[ing] effect at %location% with [color] %color% [(for|to) %players%]
			show (1¦splash|) potion break[ing] effect at %location% with [color] %number%[, %-number%(,| and) %-number%] [(for|to) %players%]
	EffSpawnEntity:
		ID: EffSpawnEntity
		Description:
			new entity構文で作成したEntityをスポーンさせることができます
			また、一度死亡したEntityをリスポーンさせることも可能です
		Patterns:
			[re]spawn [(a|an)] %entity% at %location%
Expressions:
	ExprAllCSWeapons:
		ID: ExprAllCSWeapons
		Return type: Text
		Changers:
			none
		Patterns:
			all (crackshot|cs) weapons
	ExprAnvilGUI:
		ID: ExprAnvilGUI
		Description:
			プレイヤーに表示されているAnvilGUI
		Return type: Anvil GUI
		Changers:
			none
		Patterns:
			anvil[ ]gui of %player%
			%player%'s anvil[ ]gui
	ExprAnvilGUIName:
		ID: ExprAnvilGUIName
		Description:
			金床のGUIの名前
		Return type: Text
		Changers:
			none
		Patterns:
			anvil[ ]gui name of %anvilinv%
			%anvilinv%'s anvil[ ]gui name
	ExprBetterDropsOfBlock:
		ID: ExprBetterDropsOfBlock
		Description:
			ブロックのドロップアイテムをシルクタッチや幸運を考慮して取得します
		Return type: Item / Material
		Changers:
			none
		Patterns:
			better drops (from|of) %block% [(with|using) %-itemstack%]
	ExprBossBarFromEntity:
		ID: ExprBossBarFromEntity
		Description:
			エンダードラゴン、ウィザーのボスバー
			SkellettもしくはskRayFallを導入しないとボスバーを扱うことはできません
		Return type: bossbar
		Changers:
			none
		Patterns:
			boss[ ]bar of %entity%
			%entity%'s boss[ ]bar
	ExprBulletWeaponName:
		ID: ExprBulletWeaponName
		Description:
			CrackShotの弾丸もしくは爆弾から武器の登録名を取得します
		Return type: Text
		Changers:
			none
		Patterns:
			[(crackshot|cs)] weapon [internal[ ]]name of %entity%
			%entity%'s [(crackshot|cs)] weapon [internal[ ]]name
	ExprCSDamage:
		ID: ExprCSDamage
		Description:
			CrackShot DamageイベントでEntityの受けた防具等によって軽減される前のダメージ
		Return type: Number
		Changers:
			add
			set
			remove
		Patterns:
			(crackshot|cs) [weapon] damage
	ExprCSDamager:
		ID: ExprCSDamager
		Description:
			CrackShot Damageイベントでエンティティにダメージを与えたエンティティ
		Return type: Entity
		Changers:
			add
			remove
			remove all
		Patterns:
			(crackshot|cs) (attacker|damager)
	ExprCSItem:
		ID: ExprCSItem
		Description:
			CrackShotの武器を登録名から取得します
		Return type: Item / Material
		Changers:
			none
		Patterns:
			(crackshot|cs) (weapon|item) %string%
	ExprCSWeaponDisplayName:
		ID: ExprCSWeaponDisplayName
		Description:
			CrackShotの武器の表示名
		Return type: Text
		Changers:
			none
		Patterns:
			weapon display[ ]name of %string%
			%string%'s weapon display[ ]name
	ExprCSWeaponModule:
		ID: ExprCSWeaponModule
		Description:
			CrackShotの武器の設定項目の値を取得します
		Examples:
			set {_weapondamage} to weapon module "Shooting.Projectile_Damage" of "AK-47"
send "Projectile_Damage: %{_weapondamage}%"
		Return type: Object
		Changers:
			none
		Patterns:
			weapon module %string% of %string%
	ExprCSWeaponName:
		ID: ExprCSWeaponName
		Description:
			アイテムからCrackShotの武器としての登録名を取得します
			値が返ってきたかどうかでアイテムがCrackShotの武器かどうかの判別も可能です
		Return type: Text
		Changers:
			none
		Patterns:
			(cs|crackshot) weapon (name|title) (from|of) %item%
	ExprCustomPlayerDisguise:
		ID: ExprCustomPlayerDisguise
		Description:
			スキンからプレイヤーのDisguiseを取得します
			Skellettが導入されていないとLibsDisguiseの機能を利用することはできません
		Return type: Object
		Changers:
			none
		Patterns:
			[a] [new] player disguise named %string% with skin %skin%
	ExprDamageModifier:
		ID: ExprDamageModifier
		Return type: Damage Modifier
		Changers:
			none
		Patterns:
			[the] damage modifier
	ExprDamageModifierDamage:
		ID: ExprDamageModifierDamage
		Description:
			Damage Modifierによって変化するダメージの量を取得します
		Return type: Number
		Changers:
			add
			set
			remove
		Patterns:
			damage modifier %damagemodifier%
	ExprDeathMessageFormat:
		ID: ExprDeathMessageFormat
		Description:
			エンティティが仮に現在死亡した場合の死亡メッセージのフォーマット
		Return type: Text
		Changers:
			none
		Patterns:
			death message format of %livingentity%
			%livingentity%'s death message format
	ExprDisplayedSkin:
		ID: ExprDisplayedSkin
		Description:
			プレイヤーの使用しているスキンを取得します
		Return type: Skin
		Changers:
			set
			reset
		Patterns:
			%player%'s displayed skin
			displayed skin of %player%
	ExprEntityName:
		ID: ExprEntityName
		Description:
			エンティティの名前を取得します
			エンティティにカスタムネームが設定されていない場合通常の表示名
		Return type: Text
		Changers:
			none
		Patterns:
			entity name of %entity%
			%entity%'s entity name
	ExprExplosionFire:
		ID: ExprExplosionFire
		Description:
			explosion primeイベントで炎が発生するかどうか
		Return type: Boolean
		Changers:
			set
		Patterns:
			explo(de|sion) (fire|incendiary) [state]
	ExprExplosionRadius:
		ID: ExprExplosionRadius
		Description:
			explosion primeイベントでの爆発の半径
		Return type: Number
		Changers:
			add
			set
			remove
		Patterns:
			explo(de|sion) (radius|yield)
	ExprFrom:
		ID: ExprFrom
		Description:
			プレイヤーが移動した時の移動元
		Return type: Location
		Changers:
			set
		Patterns:
			move(e|ing) from
	ExprGameRule:
		ID: ExprGameRule
		Description:
			ゲームルールの値を取得します
		Return type: Text
		Changers:
			set
		Patterns:
			gamerule %string% of %world%
			%world%'s gamerule %string%
	ExprHitBlock:
		ID: ExprHitBlock
		Description:
			Projectile Hitイベントで飛び道具の衝突したブロック
		Return type: Block
		Changers:
			add
			set
			remove
			remove all
			delete
		Patterns:
			hit block
	ExprHitEntity:
		ID: ExprHitEntity
		Description:
			Projectile Hitイベントで飛び道具の衝突したEntity
		Return type: Entity
		Changers:
			add
			remove
			remove all
		Patterns:
			hit entity
	ExprItemCooldown:
		ID: ExprItemCooldown
		Description:
			アイテムのエンダーパールのようなクールダウンを設定します
		Return type: Number
		Changers:
			add
			set
			remove
			delete
			reset
		Patterns:
			cool[]down of %itemtype% of %player%
	ExprItemName:
		ID: ExprItemName
		Description:
			アイテムの名前を取得します
			アイテムにディスプレイネームが設定されていない場合通常の表示名が取得されます
		Return type: Text
		Changers:
			none
		Patterns:
			item name of %itemstack%
			%itemstack%'s item name
	ExprKeepInventory:
		ID: ExprKeepInventory
		Description:
			プレイヤーが死亡したときインベントリのアイテムを保持するかどうか
		Return type: Boolean
		Changers:
			set
		Patterns:
			keep inventory state
	ExprKeepLevel:
		ID: ExprKeepLevel
		Description:
			プレイヤーが死亡したとき経験値を保持するかどうか
		Return type: Boolean
		Changers:
			set
		Patterns:
			keep (level|[e]xp[erience]) state
	ExprKiller:
		ID: ExprKiller
		Description:
			Mobを殺害したと認識されているエンティティ
		Return type: Entity
		Changers:
			add
			remove
			remove all
		Patterns:
			killer of %livingentity%
			%livingentity%'s killer
	ExprLastOpenedAnvilGUI:
		ID: ExprLastOpenedAnvilGUI
		Description:
			最後に開かれた金床のGUI
		Return type: Anvil GUI
		Changers:
			none
		Patterns:
			last opened anvil[ ]gui
	ExprLunaChatJapanize:
		ID: ExprLunaChatJapanize
		Description:
			LunaChatのAPIを使用して文字列をローマ字変換します
		Return type: Text
		Changers:
			none
		Patterns:
			[(lunachat|lc)] japanize %string%
	ExprLunaChatJapanizeMode:
		ID: ExprLunaChatJapanizeMode
		Description:
			LunaChatのAPIを使用してプレイヤーのローマ字変換が有効かどうかを取得します
		Return type: Boolean
		Changers:
			set
		Patterns:
			[(lunachat|lc)] %player%'s japanize [mode]
			[(lunachat|lc)] japanize [mode] of %player%
	ExprMaximumNoDamageTicks:
		ID: ExprMaximumNoDamageTicks
		Description:
			ダメージを受けた後の最大無敵時間
		Return type: Number
		Changers:
			add
			set
			remove
		Patterns:
			max[imum] no damage tick[s] of %livingentity%
			%livingentity%'s max[imum] no damage tick[s]
	ExprNewEntity:
		ID: ExprNewEntity
		Description:
			エンティティを作成しますがスポーンはしません
			一部のエンティティを除いてほぼすべてのエンティティを作成できます
			スポーンさせるためにはSkRageの別の構文を用いてください
		Return type: Entity
		Changers:
			add
			remove
			remove all
		Patterns:
			new entity %entitytype%
	ExprNoDamageTicks:
		ID: ExprNoDamageTicks
		Description:
			ダメージを受けた後の無敵時間
		Return type: Number
		Changers:
			add
			set
			remove
		Patterns:
			no damage tick[s] of %livingentity%
			%livingentity%'s no damage tick[s]
	ExprPluginMessageChannel:
		ID: ExprPluginMessageChannel
		Description:
			プラグインメッセージのチャンネルを取得します
		Return type: Text
		Changers:
			none
		Patterns:
			plugin message channel
	ExprPluginMessageContents:
		ID: ExprPluginMessageContents
		Description:
			プラグインメッセージの内容を取得します
		Return type: Number
		Changers:
			add
			set
			remove
			remove all
			delete
		Patterns:
			plugin message contents
	ExprReloadDuration:
		ID: ExprReloadDuration
		Description:
			CrackShot Reload Startイベントでの武器のリロード時間
		Return type: Number
		Changers:
			add
			set
			remove
		Patterns:
			reload duration
	ExprSkeletonOldAI:
		ID: ExprSkeletonOldAI
		Description:
			スケルトンのAIが1.8以前の状態かどうか
		Return type: Boolean
		Changers:
			set
		Patterns:
			old ai state of %livingentity%
			%livingentity%'s old ai state
	ExprSkin:
		ID: ExprSkin
		Return type: Skin
		Changers:
			none
		Patterns:
			%player%'s skin
			skin of %player%
	ExprSource:
		ID: ExprSource
		Description:
			エリアエフェクトクラウド、着火されたTNT、エヴォーカーの牙を発生させたEntityを取得します
		Return type: Living Entity
		Changers:
			set
			delete
			reset
		Patterns:
			(owner|source) of %entity%
	ExprTicksLived:
		ID: ExprTicksLived
		Description:
			エンティティがワールドにスポーンしてから経過したtick数
		Return type: Number
		Changers:
			add
			set
			remove
		Patterns:
			ticks lived of %entity%
			%entity%'s ticks lived
	ExprTo:
		ID: ExprTo
		Description:
			プレイヤーが移動した時の移動先
		Return type: Location
		Changers:
			set
		Patterns:
			move(e|ing) to
	ExprWitherHeadTarget:
		ID: ExprWitherHeadTarget
		Return type: Entity
		Changers:
			set
			delete
			reset
		Patterns:
			(0¦right|1¦left) head target of %livingentity%
			%livingentity%'s (0¦right|1¦left) head target
Types:
	Anvil GUI:
		ID: AnvilGUI
		Patterns:
			anvil[ ]inv[s]
	Damage Modifier:
		ID: DamageModifier
		Usage: absorption, armor, base, blocking, hard hat, magic, resistance
		Patterns:
			damage[ ]modifier[s]
	Log Level:
		ID: StandardLevel
		Usage: off, fatal, error, warn, info, debug, trace, all
		Patterns:
			log[ ]level[s]
	Skin:
		ID: Skin
		Patterns:
			skin[s]
Functions:
	and:
		Return type: Number
		Patterns:
			and(val1: number, val2: number)
	not:
		Return type: Number
		Patterns:
			not(val: number)
	or:
		Return type: Number
		Patterns:
			or(val1: number, val2: number)
	xor:
		Return type: Number
		Patterns:
			xor(val1: number, val2: number)
```
