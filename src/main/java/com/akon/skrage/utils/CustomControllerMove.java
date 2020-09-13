package com.akon.skrage.utils;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.server.v1_12_R1.ControllerMove;
import net.minecraft.server.v1_12_R1.EntityInsentient;

//Vexのブロック透過を制御するためのクラス
@Getter
@Setter
public class CustomControllerMove extends ControllerMove {

	private boolean noClip;

	public CustomControllerMove(EntityInsentient entityInsentient) {
		super(entityInsentient);
	}

	@Override
	public void a() {
		super.a();
		this.a.noclip = this.noClip;
	}

}
