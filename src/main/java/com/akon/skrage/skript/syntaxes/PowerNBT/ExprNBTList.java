package com.akon.skrage.skript.syntaxes.PowerNBT;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.akon.skrage.utils.ReflectionUtil;
import me.dpohvar.powernbt.api.NBTList;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Optional;

public class ExprNBTList extends SimpleExpression<NBTList> {

	static {
		if (Bukkit.getPluginManager().isPluginEnabled("PowerNBT")) {
			Skript.registerExpression(ExprNBTList.class, NBTList.class, ExpressionType.COMBINED, "nbt[ ]list from %objects%", "nbt[ ]list from %objects% with type (1¦byte|2¦short|3¦int[eger]|4¦long|5¦float|6¦double|7¦byte array|8¦string|9¦list|10¦nbt[ ]compound|11¦int[eger] array)");
		}
	}

	private Expression<Object> obj;
	private Byte type;

	@Nullable
	@Override
	protected NBTList[] get(Event e) {
		return Optional.ofNullable(this.obj).map(expr -> expr.getAll(e)).map(Arrays::asList).map(list -> {
			NBTList nbtList = new NBTList();
			if (this.type != null) {
				ReflectionUtil.DEFAULT.invokeMethod(nbtList, "setType", new Class[]{byte.class}, new Object[]{this.type});
			}
			nbtList.addAll(list);
			return nbtList;
		}).map(nbtList -> new NBTList[]{nbtList}).orElse(null);
	}

	@Override
	public boolean isSingle() {
		return true;
	}

	@Override
	public Class<? extends NBTList> getReturnType() {
		return NBTList.class;
	}

	@Override
	public String toString(@Nullable Event e, boolean debug) {
		return "";
	}

	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
		this.obj = (Expression<Object>)exprs[0];
		if (matchedPattern == 1) {
			this.type = (byte)parseResult.mark;
		}
		return true;
	}
}
