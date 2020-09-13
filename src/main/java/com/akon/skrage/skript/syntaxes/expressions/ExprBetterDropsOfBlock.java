package com.akon.skrage.skript.syntaxes.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.akon.skrage.utils.NMSUtil;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

@Description({"ブロックのドロップアイテムをシルクタッチや幸運を考慮して取得します"})
public class ExprBetterDropsOfBlock extends SimpleExpression<ItemStack> {

	static {
		Skript.registerExpression(ExprBetterDropsOfBlock.class, ItemStack.class, ExpressionType.COMBINED, "better drops (from|of) %block% [(with|using) %-itemstack%]");
	}

	private Expression<Block> block;
	private Expression<ItemStack> item;

	@Override
	protected ItemStack[] get(Event e) {
		if (this.block != null) {
			return NMSUtil.getDropsOfBlock(this.block.getSingle(e), this.item == null ? new ItemStack(Material.AIR) : this.item.getSingle(e)).toArray(new ItemStack[0]);
		}
		return null;
	}

	@Override
	public boolean isSingle() {
		return false;
	}

	@Override
	public Class<? extends ItemStack> getReturnType() {
		return ItemStack.class;
	}

	@Override
	public String toString(Event e, boolean debug) {
		return "";
	}

	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
		this.block = (Expression<Block>)exprs[0];
		if (exprs.length == 2) this.item = (Expression<ItemStack>)exprs[1];
		return true;
	}

}
