package com.akon.skrage.skript.syntaxes.PowerNBT;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import me.dpohvar.powernbt.PowerNBT;
import me.dpohvar.powernbt.api.NBTCompound;
import me.dpohvar.powernbt.api.NBTManager;
import me.dpohvar.powernbt.utils.ItemStackUtils;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

@Description({"ブロック、エンティティ、アイテムのNBT"})
public class ExprNBTCompound extends SimpleExpression<NBTCompound> {

	static {
		if (Bukkit.getPluginManager().isPluginEnabled("PowerNBT")) {
			Skript.registerExpression(ExprNBTCompound.class, NBTCompound.class, ExpressionType.COMBINED, "nbt[ ]compound (of|from) %block/entity/itemstack% ");
		}
	}

	private Expression<Object> obj;

	@Nullable
	@Override
	protected NBTCompound[] get(Event e) {
		return Optional.ofNullable(this.obj).map(expr -> expr.getSingle(e)).map(ExprNBTCompound::getNBT).map(compound -> new NBTCompound[]{compound}).orElse(null);
	}

	@Override
	public boolean isSingle() {
		return true;
	}

	@Override
	public Class<? extends NBTCompound> getReturnType() {
		return NBTCompound.class;
	}

	@Override
	public String toString(@Nullable Event e, boolean debug) {
		return "";
	}

	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
		this.obj = (Expression<Object>)exprs[0];
		return true;
	}

	@Nullable
	@Override
	public Class<?>[] acceptChange(Changer.ChangeMode mode) {
		if (mode == Changer.ChangeMode.ADD || mode == Changer.ChangeMode.SET) {
			return CollectionUtils.array(NBTCompound.class);
		}
		return null;
	}

	@Override
	public void change(Event e, @Nullable Object[] delta, Changer.ChangeMode mode) {
		if (delta == null || !(delta[0] instanceof NBTCompound) || (mode != Changer.ChangeMode.ADD && mode != Changer.ChangeMode.SET)) {
			return;
		}
		Optional.ofNullable(this.obj).map(expr -> expr.getSingle(e)).ifPresent(nbtHolder -> {
			NBTManager nbtManager = PowerNBT.getApi();
			NBTCompound compound = (NBTCompound)delta[0];
			if (mode == Changer.ChangeMode.ADD) {
				NBTCompound nbt = getNBT(nbtHolder);
				if (nbt != null) {
					nbt.merge(compound);
					compound = nbt;
				}
			}
			if (nbtHolder instanceof Block) {
				nbtManager.write((Block)nbtHolder, compound);
			} else if (nbtHolder instanceof Entity) {
				nbtManager.write((Entity)nbtHolder, compound);
			} else if (nbtHolder instanceof ItemStack) {
				boolean flag = nbtHolder.getClass().equals(ItemStack.class);
				if (flag) {
					nbtHolder = ItemStackUtils.itemStackUtils.createCraftItemStack((ItemStack)nbtHolder);
				}
				nbtManager.write((ItemStack)nbtHolder, compound);
				if (flag) {
					this.obj.change(e, new Object[]{nbtHolder}, Changer.ChangeMode.SET);
				}
			}
		});
	}

	private static NBTCompound getNBT(Object obj) {
		NBTManager nbtManager = PowerNBT.getApi();
		if (obj instanceof Block) {
			return nbtManager.read((Block)obj);
		} else if (obj instanceof Entity) {
			return nbtManager.read((Entity)obj);
		} else if (obj instanceof ItemStack) {
			return nbtManager.read((ItemStack)obj);
		}
		return null;

	}
}
