package com.akon.skrage.syntaxes.CrackShot;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.doc.Examples;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.shampaggon.crackshot.CSDirector;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;

@Description({"CrackShotの武器の設定項目の値を取得します"})
@Examples({"set {_weapondamage} to weapon module \"Shooting.Projectile_Damage\" of \"AK-47\"\nsend \"Projectile_Damage: %{_weapondamage}%\""})
public class ExprCSWeaponModule extends SimpleExpression<Object> {

	private Expression<String> node;
	private Expression<String> weaponName;
	
	static {
		if (Bukkit.getPluginManager().isPluginEnabled("CrackShot")) Skript.registerExpression(ExprCSWeaponModule.class, Object.class, ExpressionType.COMBINED, "weapon module %string% of %string%");
	}
	
	@Override
	public Class<?> getReturnType() {
		return Object.class;
	}

	@Override
	public boolean isSingle() {
		return true;
	}

	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
		this.node = (Expression<String>)exprs[0];
		this.weaponName = (Expression<String>)exprs[1];
		return true;
	}

	@Override
	public String toString(Event e, boolean debug) {
		return "";
	}

	@Override
	public Object[] get(Event e) {
		if (this.node == null || this.weaponName == null) {
			return null;
		} else {
			Object result = CSDirector.dubs.get(this.weaponName.getSingle(e) + "." + this.node.getSingle(e));
			if (result == null) CSDirector.bools.get(this.weaponName.getSingle(e) + "." + this.node.getSingle(e));
			if (result == null) CSDirector.ints.get(this.weaponName.getSingle(e) + "." + this.node.getSingle(e));
			if (result == null) CSDirector.strings.get(this.weaponName.getSingle(e) + "." + this.node.getSingle(e));
			return new Object[]{result};
		}
	}

}
