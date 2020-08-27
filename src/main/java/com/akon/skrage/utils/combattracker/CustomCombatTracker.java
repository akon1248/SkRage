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
		try {
			ReflectionUtil.copyFields(CombatTracker.class, this, combatTracker);
		} catch (ReflectiveOperationException ex) {
			ex.printStackTrace();
		}
		return combatTracker;
	}
	
	public static CustomCombatTracker fromOrigin(CombatTracker combatTracker) {
		Class<CombatTracker> clazz = CombatTracker.class;
		if (!combatTracker.getClass().equals(clazz)) {
			throw new IllegalArgumentException("CombatTrackerのサブクラスのインスタンスを引数として渡すことはできません");
		}
		CustomCombatTracker custom = new CustomCombatTracker(combatTracker.h());
		try {
			ReflectionUtil.copyFields(clazz, combatTracker, custom);
		} catch (ReflectiveOperationException ex) {
			ex.printStackTrace();
		}
		return custom;
	}

}
