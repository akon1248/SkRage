package com.akon.skrage.skript.syntaxes.CrackShot;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.shampaggon.crackshot.CSDirector;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

@Description({"CrackShotの武器を登録名から取得します"})
public class ExprCSItem extends SimpleExpression<ItemStack> {

	static {
		if (Bukkit.getPluginManager().isPluginEnabled("CrackShot")) Skript.registerExpression(ExprCSItem.class, ItemStack.class, ExpressionType.COMBINED, "(crackshot|cs) (weapon|item) %string%");
	}
	
	private Expression<String> weaponName;

	@Override
	public Class<? extends ItemStack> getReturnType() {
		return ItemStack.class;
	}

	@Override
	public boolean isSingle() {
		return true;
	}
	
	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
		this.weaponName = (Expression<String>)exprs[0];
		return true;
	}

	@Override
	public String toString(Event e, boolean debug) {
		return "";
	}

	@Override
	public ItemStack[] get(Event e) {
		return this.weaponName == null ? null : new ItemStack[]{((CSDirector)Bukkit.getPluginManager().getPlugin("CrackShot")).csminion.vendingMachine(this.weaponName.getSingle(e))};
	}

}
