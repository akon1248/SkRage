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
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

@Description({"ブロック、エンティティのNBT"})
public class ExprNBTCompound extends SimpleExpression<NBTCompound> {

	static {
		if (Bukkit.getPluginManager().isPluginEnabled("PowerNBT")) {
			Skript.registerExpression(ExprNBTCompound.class, NBTCompound.class, ExpressionType.COMBINED, "nbt[ ]compound (of|from) %block/entity% ");
		}
	}

	private Expression<?> obj;

	@Nullable
	@Override
	protected NBTCompound[] get(Event e) {
		NBTManager nbtManager = PowerNBT.getApi();
		Object nbtHolder = this.obj.getSingle(e);
		if (nbtHolder instanceof Block) {
			return new NBTCompound[]{nbtManager.read((Block)nbtHolder)};
		} else if (nbtHolder instanceof Entity) {
			return new NBTCompound[]{nbtManager.read((Entity)nbtHolder)};
		}
		return null;
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
		this.obj = exprs[0];
		return true;
	}

	@Nullable
	@Override
	public Class<?>[] acceptChange(Changer.ChangeMode mode) {
		if (mode == Changer.ChangeMode.ADD) {
			return CollectionUtils.array(NBTCompound.class);
		}
		return null;
	}

	@Override
	public void change(Event e, @Nullable Object[] delta, Changer.ChangeMode mode) {
		if (delta == null || !(delta[0] instanceof NBTCompound) || mode != Changer.ChangeMode.ADD) {
			return;
		}
		NBTManager nbtManager = PowerNBT.getApi();
		NBTCompound compound = (NBTCompound)delta[0];
		Object nbtHolder = this.obj.getSingle(e);
		if (nbtHolder instanceof Block) {
			nbtManager.write((Block)nbtHolder, compound);
		} else if (nbtHolder instanceof Entity) {
			nbtManager.write((Entity)nbtHolder, compound);
		}
	}
}
