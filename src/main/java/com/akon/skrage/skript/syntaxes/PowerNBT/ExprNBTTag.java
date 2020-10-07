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
import me.dpohvar.powernbt.api.NBTCompound;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;


@Description({"NBTの指定されたキーに結び付けられている値"})
public class ExprNBTTag extends SimpleExpression<Object> {

	static {
		if (Bukkit.getPluginManager().isPluginEnabled("PowerNBT")) {
			Skript.registerExpression(ExprNBTTag.class, Object.class, ExpressionType.COMBINED, "tag key %string% (from|of) %nbtcompound%");
		}
	}

	private Expression<String> key;
	private Expression<NBTCompound> nbt;

	@Nullable
	@Override
	protected Object[] get(Event e) {
		return Optional.ofNullable(this.nbt)
			.map(expr -> expr.getSingle(e))
			.flatMap(compound -> Optional.ofNullable(this.key)
				.map(expr -> expr.getSingle(e))
				.map(compound::get)
			)
			.map(value -> new Object[]{value}).orElse(null);
	}

	@Override
	public boolean isSingle() {
		return true;
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
		this.key = (Expression<String>)exprs[0];
		this.nbt = (Expression<NBTCompound>)exprs[1];
		return true;
	}

	@Nullable
	@Override
	public Class<?>[] acceptChange(Changer.ChangeMode mode) {
		return mode == Changer.ChangeMode.SET || mode == Changer.ChangeMode.DELETE ? CollectionUtils.array(Object.class) : null;
	}

	@Override
	public void change(Event e, @Nullable Object[] delta, Changer.ChangeMode mode) {
		Optional.ofNullable(this.nbt).map(expr -> expr.getSingle(e)).ifPresent(compound -> {
			if (mode == Changer.ChangeMode.SET) {
				if (delta == null) {
					return;
				}
				Optional.ofNullable(this.key).map(expr -> expr.getSingle(e)).ifPresent(key -> compound.put(key, delta[0]));
				if (this.nbt instanceof ExprNBTCompound || this.nbt instanceof ExprItemNBTCompound) {
					this.nbt.change(e, new Object[]{compound}, Changer.ChangeMode.ADD);
				}
			} else if (mode == Changer.ChangeMode.DELETE) {
				Optional.ofNullable(this.key).map(expr -> expr.getSingle(e)).ifPresent(key -> {
					compound.remove(key);
					if (this.nbt instanceof ExprItemNBTCompound) {
						this.nbt.change(e, new Object[]{compound}, Changer.ChangeMode.SET);
					}
				});
			}
		});
	}

}
