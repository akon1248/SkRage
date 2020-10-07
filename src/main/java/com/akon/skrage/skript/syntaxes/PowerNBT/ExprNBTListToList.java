package com.akon.skrage.skript.syntaxes.PowerNBT;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import me.dpohvar.powernbt.api.NBTList;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class ExprNBTListToList extends SimpleExpression<Object> {

	static {
		if (Bukkit.getPluginManager().isPluginEnabled("PowerNBT")) {
			Skript.registerExpression(ExprNBTListToList.class, Object.class, ExpressionType.COMBINED, "convert nbt[ ]list %nbtlist% to list");
		}
	}

	private Expression<NBTList> nbtList;

	@Nullable
	@Override
	protected Object[] get(Event e) {
		return Optional.ofNullable(this.nbtList).map(expr -> expr.getSingle(e)).map(NBTList::toArray).orElse(null);
	}

	@Override
	public boolean isSingle() {
		return false;
	}

	@Override
	public Class<?> getReturnType() {
		return Object.class;
	}

	@Override
	public String toString(@Nullable Event e, boolean debug) {
		return "";
	}

	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
		this.nbtList = (Expression<NBTList>)exprs[0];
		return true;
	}
}
