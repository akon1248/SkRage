package com.akon.skrage.skript.syntaxes.PowerNBT;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.dpohvar.powernbt.api.NBTCompound;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

@Description({"複数のNBTをマージします"})
public class ExprMergeNBT extends SimpleExpression<NBTCompound> {

	static {
		if (Bukkit.getPluginManager().isPluginEnabled("PowerNBT")) {
			Skript.registerExpression(ExprMergeNBT.class, NBTCompound.class, ExpressionType.COMBINED, "merge nbt[ ]compound[s] %nbtcompounds%");
		}
	}

	private Expression<NBTCompound> nbt;

	@Nullable
	@Override
	protected NBTCompound[] get(Event e) {
		if (this.nbt != null) {
			NBTCompound compound = new NBTCompound();
			Arrays.stream(this.nbt.getAll(e)).forEach(compound::merge);
			return new NBTCompound[]{compound};
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
		this.nbt = (Expression<NBTCompound>)exprs[0];
		return true;
	}
}
