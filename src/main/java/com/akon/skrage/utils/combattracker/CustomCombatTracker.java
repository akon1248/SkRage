package com.akon.skrage.utils.combattracker;

import com.akon.skrage.utils.ReflectionUtil;
import net.minecraft.server.v1_12_R1.CombatTracker;
import net.minecraft.server.v1_12_R1.EntityLiving;

//通常のリセット用メソッドではリセットできないCombatTracker
//リセット後に呼び出される死亡イベントの段階までCombatTrackerの保持している値を維持させることを目的としている
public class CustomCombatTracker extends CombatTracker {

	public CustomCombatTracker(EntityLiving entityLiving) {
		super(entityLiving);
	}

	@Override
	public void g() {}

	public void reset() {
		super.g();
	}
	
	public CombatTracker getOrigin() {
		CombatTracker combatTracker = new CombatTracker(this.h());
		ReflectionUtil.DEFAULT.shallowCopyFields(CombatTracker.class, this, combatTracker);
		return combatTracker;
	}
	
	public static CustomCombatTracker fromOrigin(CombatTracker combatTracker) {
		if (combatTracker instanceof CustomCombatTracker) {
			return (CustomCombatTracker)combatTracker;
		}
		Class<CombatTracker> clazz = CombatTracker.class;
		CustomCombatTracker custom = new CustomCombatTracker(combatTracker.h());
		ReflectionUtil.DEFAULT.shallowCopyFields(clazz, combatTracker, custom);
		return custom;
	}

}
