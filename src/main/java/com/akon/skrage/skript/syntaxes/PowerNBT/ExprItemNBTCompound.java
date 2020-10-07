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
import lombok.Getter;
import me.dpohvar.powernbt.PowerNBT;
import me.dpohvar.powernbt.api.NBTCompound;
import me.dpohvar.powernbt.api.NBTManager;
import me.dpohvar.powernbt.utils.ItemStackUtils;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

@Description({"アイテムのNBT"})
public class ExprItemNBTCompound extends SimpleExpression<NBTCompound> {

	static {
		if (Bukkit.getPluginManager().isPluginEnabled("PowerNBT")) {
			Skript.registerExpression(ExprItemNBTCompound.class, NBTCompound.class, ExpressionType.COMBINED, "nbt[ ]compound (of|from) item %itemstack% ");
		}
	}

	@Getter
	private Expression<ItemStack> item;

	@Nullable
	@Override
	protected NBTCompound[] get(Event e) {
		return Optional.ofNullable(this.item).map(expr -> expr.getSingle(e)).map(item -> PowerNBT.getApi().read(item)).map(nbt -> new NBTCompound[]{nbt}).orElse(null);
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
		this.item = (Expression<ItemStack>)exprs[0];
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
		Optional.ofNullable(this.item).map(expr -> expr.getSingle(e)).ifPresent(item -> {
			if (delta == null || !(delta[0] instanceof NBTCompound)) {
				return;
			}
			NBTManager nbtManager = PowerNBT.getApi();
			NBTCompound compound = (NBTCompound)delta[0];
			boolean flag = item.getClass().equals(ItemStack.class);
			if (flag) {
				item = ItemStackUtils.itemStackUtils.createCraftItemStack(item);
			}
			if (mode == Changer.ChangeMode.ADD) {
				NBTCompound nbt = nbtManager.read(item);
				nbt.merge(compound);
				nbtManager.write(item, nbt);

			} else if (mode == Changer.ChangeMode.SET) {
				nbtManager.write(item, compound);
			}
			if (flag) {
				this.item.change(e, new ItemStack[]{item}, Changer.ChangeMode.SET);
			}
		});
	}
}
