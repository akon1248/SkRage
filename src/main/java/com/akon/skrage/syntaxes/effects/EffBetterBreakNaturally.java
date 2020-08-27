package com.akon.skrage.syntaxes.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.akon.skrage.utils.NMSUtil;
import net.minecraft.server.v1_12_R1.BlockPosition;
import net.minecraft.server.v1_12_R1.WorldServer;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

@Description({"指定されたアイテムの幸運やシルクタッチを考慮してブロックを破壊し、アイテムをドロップさせます"})
public class EffBetterBreakNaturally extends Effect {

	static {
		Skript.registerEffect(EffBetterBreakNaturally.class, "better break %block% [naturally] [(with|using) %-itemstack%]");
	}

	private Expression<Block> block;
	private Expression<ItemStack> item;

	@Override
	protected void execute(Event e) {
		Block block;
		if (this.block != null && (block = this.block.getSingle(e)).getType() != Material.AIR) {
			WorldServer nmsWorld = ((CraftWorld)block.getWorld()).getHandle();
			BlockPosition blockposition = new BlockPosition(block.getX(), block.getY(), block.getZ());
			NMSUtil.getDropsOfBlock(block, this.item == null ? new ItemStack(Material.AIR) : this.item.getSingle(e)).forEach(item -> net.minecraft.server.v1_12_R1.Block.a(nmsWorld, blockposition, CraftItemStack.asNMSCopy(item)));
			block.setType(Material.AIR);
		}
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
