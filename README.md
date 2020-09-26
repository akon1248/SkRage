# SkRage
 AnvilGUI、SignEditor、Skin、CrackShot、その他実用性皆無な構文が入っているアドオン
 
 Required: ProtocolLib

Document
```
Events:
	On add tracking player:
		ID: add_tracking_player
		Patterns:
			[on] add tracking player
		Event values:
			event-world
			event-player
			event-entity
		Cancellable: true
	On anvil gui close:
		ID: anvil_gui_close
		Description:
			AnvilGUIが閉じられたとき
		Patterns:
			[on] anvil[ ]gui close
		Event values:
			event-anvilinv
			event-world
			event-player
		Cancellable: false
	On anvil gui done:
		ID: anvil_gui_done
		Description:
			AnvilGUIにプレイヤーがテキストを入力し終わったとき
		Patterns:
			[on] anvil[ ]gui done
		Event values:
			event-anvilinv
			event-world
			event-player
			event-string
		Cancellable: false
	On anvil gui open:
		ID: anvil_gui_open
		Description:
			AnvilGUIが開かれるとき
		Patterns:
			[on] anvil[ ]gui open
		Event values:
			event-anvilinv
			event-world
			event-player
		Cancellable: true
	On combust by sunlight:
		ID: combust_by_sunlight
		Description:
			ゾンビやスケルトンが日光によって燃えたとき
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
			event-projectile
			event-player
			event-string
		Cancellable: true
	On cs pre shoot:
		ID: cs_pre_shoot
		Description:
			CrackShotの武器が発射される直前
		Patterns:
			[on] (crackshot|cs) pre shoot
		Event values:
			event-player
			event-string
		Cancellable: true
	On cs prepare shoot:
		ID: cs_prepare_shoot
		Description:
			CrackShotの武器が発射されようとしたとき
		Patterns:
			[on] (crackshot|cs) prepare shoot
		Event values:
			event-player
			event-string
		Cancellable: true
	On cs shoot:
		ID: cs_shoot
		Description:
			CrackShotの武器が発射されるとき
		Patterns:
			[on] (crackshot|cs) shoot
		Event values:
			event-projectile
			event-player
			event-entity
			event-string
		Cancellable: false
	On disguise:
		ID: disguise
		Description:
			エンティティが変身したとき
		Patterns:
			[on] disguise
		Event values:
			event-disguise
			event-entity
		Cancellable: true
	On jump:
		ID: jump
		Description:
			プレイヤーがジャンプしたとき
		Patterns:
			[on] jump
		Event values:
			event-world
			event-player
		Cancellable: true
	On player velocity:
		ID: player_velocity
		Description:
			プレイヤーのベクトルが変化したとき
		Patterns:
			[on] [player] velocity
		Event values:
			event-world
			event-player
			event-vector
		Cancellable: true
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
	On receive creative item:
		ID: receive_creative_item
		Patterns:
			[on] receive creative item
		Event values:
			event-world
			event-player
		Cancellable: false
	On reload complete:
		ID: reload_complete
		Description:
			CrackShotの武器のリロードが完了したとき
		Patterns:
			[on] [(crackshot|cs)] reload complete
		Event values:
			event-player
			event-string
		Cancellable: false
	On reload start:
		ID: reload_start
		Description:
			CrackShotの武器のリロードを開始したとき
		Patterns:
			[on] [(crackshot|cs)] reload [start]
		Event values:
			event-player
			event-string
		Cancellable: false
	On remove tracking player:
		ID: remove_tracking_player
		Patterns:
			[on] remove tracking player
		Event values:
			event-world
			event-player
			event-entity
		Cancellable: true
	On scope:
		ID: scope
		Description:
			CrackShotの武器のスコープを覗いたとき
		Patterns:
			[on] [(crackshot|cs)] scope
		Event values:
			event-player
			event-string
		Cancellable: true
	On show item:
		ID: show_item
		Description:
			アイテムがプレイヤーに表示されるとき
		Patterns:
			[on] (sen(d|t)|show) item[stack]
		Event values:
			event-world
			event-player
		Cancellable: false
	On sign editor done:
		ID: sign_editor_done
		Description:
			プレイヤーがSignEditorでの編集を終えた時(通常の看板では発動しません)
		Patterns:
			[on] sign[ ]editor done
		Event values:
			event-world
			event-player
			event-signeditor
		Cancellable: false
	On sign editor open:
		ID: sign_editor_open
		Description:
			SignEditorが開かれたとき(通常の看板では発動しません)
		Patterns:
			[on] sign[ ]editor open
		Event values:
			event-world
			event-player
			event-signeditor
		Cancellable: true
	On undisguise:
		ID: undisguise
		Description:
			エンティティが変身を解いたとき
		Patterns:
			[on] undisguise
		Event values:
			event-disguise
			event-entity
		Cancellable: true
	On unscope:
		ID: unscope
		Description:
			CrackShotの武器でスコープ状態を解除したとき
		Patterns:
			[on] [(crackshot|cs)] unscope
		Event values:
			event-player
			event-string
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
	EffArmSwing:
		ID: EffArmSwing
		Patterns:
			make arm swing %livingentity%
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
	EffBroadcastDeathMessage:
		ID: EffBroadcastDeathMessage
		Patterns:
			broadcast death message of %livingentity%
	EffChestOpenClose:
		ID: EffChestOpenClose
		Description:
			チェストやシュルカーボックスなどに開く/閉じるの動作をさせます
		Patterns:
			make open %block%
			make close %block%
	EffClientSideEquipment:
		ID: EffClientSideEquipment
		Patterns:
			make %entities% (0¦[main]hand|1¦offhand|2¦(boots|feet)|3¦(le[ggin]gs)|4¦chest[plate]|5¦he(lmet|ad)) %itemtype% for %players%
	EffClientSideGlowing:
		ID: EffClientSideGlowing
		Description:
			特定のプレイヤーに対してEntityが発光しているように見せます
		Patterns:
			make %entities% glow[ing] for %players%
			make %entities% unglow[ing] for %players%
	EffCloseAnvilGUI:
		ID: EffCloseAnvilGUI
		Description:
			AnvilGUIを閉じます
		Patterns:
			close anvil[ ]gui %anvilinv%
	EffDamageSource:
		ID: EffDamageSource
		Description:
			DamageSourceを用いてエンティティにダメージを与えます
			※一度使用したDamageSourceは不変になります
		Patterns:
			damage %entity% by %number% with damage[ ]source %damagesource%
	EffDespawn:
		ID: EffDespawn
		Description:
			エンティティをデスポーンさせます
		Patterns:
			(despawn|remove) %entities%
	EffDestroyStage:
		ID: EffDestroyStage
		Description:
			ブロックにひびをつけます
			idを同一にすれば後からひびの入り具合を変更できます
			ひびの入り具合は0 ~ 9
		Patterns:
			set destroy stage with id %string% of %block% to %number%
	EffEntityVisibility:
		ID: EffEntityVisibility
		Patterns:
			hide entity %entity% from %players%
			(show|reveal) entity %entity% to %players%
	EffMakePlayerAttack:
		ID: EffMakePlayerAttack
		Description:
			プレイヤーに攻撃をさせます
		Patterns:
			make %player% attack %livingentity%
	EffOpenAnvilGUI:
		ID: EffOpenAnvilGUI
		Description:
			プレイヤーにAnvilGUIを表示します
		Patterns:
			open anvil[ ]gui named %string% with icon %itemtype% text %string% (to|for) %player%
	EffOpenSignEditor:
		ID: EffOpenSignEditor
		Description:
			プレイヤーに看板のSignEditorを開かせます
		Patterns:
			open sign[ ]editor named %string% with (line|text) 1 %string% (line|text) 2 %string% (line|text) 3 %string% (line|text) 4 %string% (to|for) %player%
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
			new entity構文で作成したエンティティをスポーンさせることができます
			また、一度死亡したエンティティをリスポーンさせることも可能です
		Patterns:
			[re]spawn [(a|an)] %entity% at %location%
	EffUpdateInventory:
		ID: EffUpdateInventory
		Description:
			プレイヤーのインベントリを更新します
		Patterns:
			update %player%'s inventory
			update inventory of %player%
Expressions:
	ExprAllCSWeapons:
		ID: ExprAllCSWeapons
		Return type: Text
		Changers:
			none
		Patterns:
			all (crackshot|cs) weapons
	ExprAlwaysAngry:
		ID: ExprAlwaysAngry
		Description:
			ゾンビピッグマンが常に敵対的かどうか
		Return type: Boolean
		Changers:
			set
		Patterns:
			%livingentity%'s always angry state
			always angry state of %livingentity%
	ExprAnvilGUI:
		ID: ExprAnvilGUI
		Description:
			プレイヤーに表示されているAnvilGUI
		Return type: Anvil GUI
		Changers:
			none
		Patterns:
			%player%'s [opened] anvil[ ]gui
			[opened] anvil[ ]gui of %player%
			[opened] anvil[ ]gui from %player%
	ExprAnvilGUIName:
		ID: ExprAnvilGUIName
		Description:
			AnvilGUIの名前
		Return type: Text
		Changers:
			none
		Patterns:
			%anvilinv%'s anvil[ ]gui name
			anvil[ ]gui name of %anvilinv%
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
			%entity%'s boss[ ]bar
			boss[ ]bar of %entity%
			boss[ ]bar from %entity%
	ExprBulletWeaponName:
		ID: ExprBulletWeaponName
		Description:
			CrackShotの弾丸もしくは爆弾から武器の登録名を取得します
		Return type: Text
		Changers:
			none
		Patterns:
			%entity%'s [(crackshot|cs)] weapon [internal[ ]]name
			[(crackshot|cs)] weapon [internal[ ]]name of %entity%
			[(crackshot|cs)] weapon [internal[ ]]name from %entity%
	ExprBytes:
		ID: ExprBytes
		Description:
			文字列をバイトのリストに変換します
		Return type: Number
		Changers:
			none
		Patterns:
			bytes of %string%
			bytes from %string%
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
	ExprCSVictim:
		ID: ExprCSVictim
		Description:
			CrackShot Damageイベントでダメージを受けたエンティティ
		Return type: Entity
		Changers:
			add
			remove
			remove all
		Patterns:
			(crackshot|cs) vicitm
	ExprCSWeaponDisplayName:
		ID: ExprCSWeaponDisplayName
		Description:
			CrackShotの武器の表示名
		Return type: Text
		Changers:
			none
		Patterns:
			weapon display[ ]name of %string%
			weapon display[ ]name from %string%
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
			(cs|crackshot) weapon (name|title) (from|of) %itemtype%
	ExprCreateItemType:
		ID: ExprCreateItemType
		Return type: Item Type
		Changers:
			none
		Patterns:
			[a] [new] item[ ]type %string% [data %-number%]
	ExprCustomDamageSource:
		ID: ExprCustomDamageSource
		Description:
			このアドオンのDamageSourceによるダメージだった場合、そのDamageSourceを取得します
		Return type: Damage Source
		Changers:
			none
		Patterns:
			custom damage source
	ExprCustomPlayerDisguise:
		ID: ExprCustomPlayerDisguise
		Description:
			スキンからプレイヤーのDisguiseを取得します
			Skellettが導入されていないとLibsDisguiseの機能を利用することはできません
		Return type: disguise
		Changers:
			none
		Patterns:
			[a] [new] player disguise named %string% with skin %skin%
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
	ExprDamageSourceDifficultyScaled:
		ID: ExprDamageSourceDifficultyScaled
		Description:
			難易度によって受けるダメージが変化するかどうか
		Return type: Boolean
		Changers:
			set
		Patterns:
			%damagesource%'s difficulty scaled
			difficulty scaled of %damagesource%
	ExprDamageSourceEntity:
		ID: ExprDamageSourceEntity
		Description:
			ダメージを与えるエンティティ
		Return type: Entity
		Changers:
			set
		Patterns:
			%damagesource%'s damage[ ]source (entity|attacker)
			damage[ ]source (entity|attacker) of %damagesource%
	ExprDamageSourceEntityProjectile:
		ID: ExprDamageSourceEntityProjectile
		Description:
			ダメージを与える飛び道具
		Return type: Entity
		Changers:
			set
		Patterns:
			%damagesource%'s damage[ ]source projectile
			damage[ ]source projectile of %damagesource%
	ExprDamageSourceExhaustion:
		ID: ExprDamageSourceExhaustion
		Description:
			ダメージを受けたときの疲労度
		Return type: Number
		Changers:
			add
			set
			remove
		Patterns:
			%damagesource%'s exhaustion (cost|damage)
			exhaustion (cost|damage) of %damagesource%
	ExprDamageSourceExplosion:
		ID: ExprDamageSourceExplosion
		Description:
			爆発のダメージかどうか
		Return type: Boolean
		Changers:
			set
		Patterns:
			%damagesource%'s damage[ ]source explosion
			damage[ ]source explosion of %damagesource%
	ExprDamageSourceFire:
		ID: ExprDamageSourceFire
		Description:
			炎のダメージかどうか
		Return type: Boolean
		Changers:
			set
		Patterns:
			%damagesource%'s damage[ ]source fire
			damage[ ]source fire of %damagesource%
	ExprDamageSourceIgnoreArmor:
		ID: ExprDamageSourceIgnoreArmor
		Description:
			防具を無視してダメージを与えるかどうか
		Return type: Boolean
		Changers:
			set
		Patterns:
			%damagesource%'s ignore armor
			ignore armor of %damagesource%
	ExprDamageSourceIgnoreCreativeMode:
		ID: ExprDamageSourceIgnoreCreativeMode
		Description:
			クリエイティブモードの無敵を無視してダメージを与えるかどうか
		Return type: Boolean
		Changers:
			set
		Patterns:
			%damagesource%'s ignore creative[[ ]mode]
			ignore creative[[ ]mode] of %damagesource%
	ExprDamageSourceIgnoreNoDamageTicks:
		ID: ExprDamageSourceIgnoreNoDamageTicks
		Description:
			無敵時間を無視してダメージを与えるかどうか
		Return type: Boolean
		Changers:
			set
		Patterns:
			%damagesource%'s ignore no damage ticks
			ignore no damage ticks of %damagesource%
	ExprDamageSourceMagic:
		ID: ExprDamageSourceMagic
		Description:
			魔法ダメージかどうか
		Return type: Boolean
		Changers:
			set
		Patterns:
			%damagesource%'s damage[ ]source magic
			damage[ ]source magic of %damagesource%
	ExprDamageSourcePreventKnockback:
		ID: ExprDamageSourcePreventKnockback
		Description:
			ダメージを与えたときのノックバックを防ぐかどうか
		Return type: Boolean
		Changers:
			set
		Patterns:
			%damagesource%'s prevent knockback
			prevent knockback of %damagesource%
	ExprDamageSourceProjectile:
		ID: ExprDamageSourceProjectile
		Description:
			飛び道具によるダメージかどうか
		Return type: Boolean
		Changers:
			set
		Patterns:
			%damagesource%'s damage[ ]source projectile damage
			damage[ ]source projectile damage of %damagesource%
	ExprDamageSourceStarvation:
		ID: ExprDamageSourceStarvation
		Description:
			空腹ダメージかどうか
		Return type: Boolean
		Changers:
			set
		Patterns:
			%damagesource%'s damage[ ]source starvation
			damage[ ]source starvation of %damagesource%
	ExprDamageSourceSweep:
		ID: ExprDamageSourceSweep
		Description:
			薙ぎ払い攻撃によるダメージかどうか
		Return type: Boolean
		Changers:
			set
		Patterns:
			%damagesource%'s damage[ ]source sweep
			damage[ ]source sweep of %damagesource%
	ExprDamageSourceThorns:
		ID: ExprDamageSourceThorns
		Description:
			棘の鎧のダメージかどうか
		Return type: Boolean
		Changers:
			set
		Patterns:
			%damagesource%'s damage[ ]source thorns
			damage[ ]source thorns of %damagesource%
	ExprDamageSourceType:
		ID: ExprDamageSourceType
		Description:
			ダメージのタイプ
		Return type: Text
		Changers:
			set
		Patterns:
			%damagesource%'s damage[ ]source type
			damage[ ]source type of %damagesource%
	ExprDamageSources:
		ID: ExprDamageSources
		Description:
			デフォルトに存在しているDamageSourceを取得します
			※この構文を使用して取得できるDamageSourceは不変です
		Return type: Damage Source
		Changers:
			none
		Patterns:
			damage[ ]source (0¦fire|1¦lightning|2¦burn|3¦lava|4¦hot floor|5¦stuck|6¦cramming|7¦drown|8¦starve|9¦cactus|10¦fall|11¦fly into wall|12¦out of world|13¦generic|14¦magic|15¦wither|16¦anvil|17¦falling block|18¦dragon breath|19¦fireworks)
	ExprDeathMessageFormat:
		ID: ExprDeathMessageFormat
		Description:
			エンティティが仮に現在死亡した場合の死亡メッセージのフォーマット
		Return type: Text
		Changers:
			none
		Patterns:
			%livingentity%'s death message format
			death message format of %livingentity%
	ExprDisguiseSlimeSize:
		ID: ExprDisguiseSlimeSize
		Description:
			スライムのDisguiseのサイズ
		Return type: Number
		Changers:
			add
			set
			remove
		Patterns:
			%disguise%'s [slime] disguise size
			[slime] disguise size of %disguise%
	ExprDisplayedSkin:
		ID: ExprDisplayedSkin
		Description:
			プレイヤーに表示されているスキン
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
			%entity%'s entity name
			entity name of %entity%
	ExprEntitySize:
		ID: ExprEntitySize
		Description:
			エンティティの幅と高さを取得します
		Return type: Number
		Changers:
			none
		Patterns:
			height of %entity%
			%entity%'s height
			width of %entity%
			%entity%'s width
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
	ExprIllagerSpell:
		ID: ExprIllagerSpell
		Description:
			エヴォーカー、イリュージョナーが現在唱えている呪文(none, summon vex, fangs, wololo, disappear, blindness)
		Return type: Text
		Changers:
			set
		Patterns:
			%livingentity%'s [casting] spell
			[casting] spell of %livingentity%
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
			%livingentity%'s killer
			killer of %livingentity%
	ExprLastOpenedAnvilGUI:
		ID: ExprLastOpenedAnvilGUI
		Description:
			最後に開かれたAnvilGUI
		Return type: Anvil GUI
		Changers:
			none
		Patterns:
			last opened anvil[ ]gui
	ExprLastOpenedSignEditor:
		ID: ExprLastOpenedSignEditor
		Description:
			open sign editorの構文を使って最後に開かれたSignEditorを取得します
		Return type: Sign Editor
		Changers:
			none
		Patterns:
			last opened sign[ ]editor
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
			%livingentity%'s max[imum] no damage tick[s]
			max[imum] no damage tick[s] of %livingentity%
	ExprNewDamageSource:
		ID: ExprNewDamageSource
		Description:
			指定されたタイプからDamageSourceを作成します(タイプは自由ですがデフォルトにないものを使用した場合に死亡メッセージが通常通り表示されなくなります)
		Return type: Damage Source
		Changers:
			none
		Patterns:
			[a] [new] damage[ ]source with type %string%
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
	ExprNewSkin:
		ID: ExprNewSkin
		Description:
			valueとsignatureから新しいスキンを作成します
		Return type: Skin
		Changers:
			none
		Patterns:
			new skin value %string% signature %string%
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
			%livingentity%'s no damage tick[s]
			no damage tick[s] of %livingentity%
	ExprPlayerHead:
		ID: ExprPlayerHead
		Description:
			スキンからプレイヤーヘッドを作成します
		Return type: Item / Material
		Changers:
			none
		Patterns:
			player head from [skin] %skin%
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
	ExprReceivedItem:
		ID: ExprReceivedItem
		Description:
			creative item receiveイベントでクライアント側から送られたアイテム
		Return type: Item / Material
		Changers:
			set
		Patterns:
			receive[d] item[stack]
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
	ExprShownItem:
		ID: ExprShownItem
		Description:
			show itemイベントで表示されるアイテム
		Return type: Item / Material
		Changers:
			set
		Patterns:
			show[n] item[stack]
	ExprSignEditor:
		ID: ExprSignEditor
		Description:
			プレイヤーに表示されているSignEditor
		Return type: Sign Editor
		Changers:
			none
		Patterns:
			%player%'s [opened] sign[ ]editor
			[opened] sign[ ]editor of %player%
			[opened] sign[ ]editor from %player%
	ExprSignEditorLine:
		ID: ExprSignEditorLine
		Description:
			sign editor doneイベントでプレイヤーが入力した文字列を取得します
		Return type: Text
		Changers:
			none
		Patterns:
			sign[ ]editor (line|text) %number%
	ExprSkeletonOldAI:
		ID: ExprSkeletonOldAI
		Description:
			スケルトンのAIが1.8以前の状態かどうか
		Return type: Boolean
		Changers:
			set
		Patterns:
			%livingentity%'s old ai state
			old ai state of %livingentity%
	ExprSkin:
		ID: ExprSkin
		Description:
			プレイヤーが使用しているスキン
		Return type: Skin
		Changers:
			none
		Patterns:
			%offlineplayer%'s skin
			skin of %offlineplayer%
			skin from %offlineplayer%
	ExprSource:
		ID: ExprSource
		Description:
			エリアエフェクトクラウド、着火されたTNT、エヴォーカーの牙を発生させたエンティティを取得します
		Return type: Living Entity
		Changers:
			set
			delete
			reset
		Patterns:
			(owner|source) of %entity%
	ExprStringFromBytes:
		ID: ExprStringFromBytes
		Description:
			バイトのリストから文字列を作成します(UTF-8)
		Return type: Text
		Changers:
			none
		Patterns:
			(string|text) from [bytes] %numbers%
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
			%entity%'s ticks lived
			ticks lived of %entity%
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
	Damage Source:
		ID: DamageSourceBuilder
		Patterns:
			damage[ ]source[s]
	Log Level:
		ID: StandardLevel
		Usage: off, fatal, error, warn, info, debug, trace, all
		Patterns:
			log[ ]level[s]
	Sign Editor:
		ID: SignEditor
		Patterns:
			sign[ ]editor[s]
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
