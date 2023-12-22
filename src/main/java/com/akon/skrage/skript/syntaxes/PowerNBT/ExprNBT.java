package com.akon.skrage.skript.syntaxes.PowerNBT;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.ConvertedExpression;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.akon.skrage.SkRage;
import com.akon.skrage.utils.LogUtil;
import me.dpohvar.powernbt.PowerNBT;
import me.dpohvar.powernbt.api.NBTCompound;
import me.dpohvar.powernbt.api.NBTList;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

public class ExprNBT extends SimpleExpression<Object> {

    static {
        if (Bukkit.getPluginManager().isPluginEnabled("PowerNBT")) {
            Skript.registerExpression(ExprNBT.class, Object.class, ExpressionType.COMBINED, "nbt[[ ]literal] %string%", "nbt[[ ]literal] (0¦\\{<.*>\\}|1¦[<.*>])");
        }
    }

    private String literal;
    private Expression<String> literalExpr;
    private Object value;

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        if (matchedPattern == 1) {
            String matched = parseResult.regexes.get(0).group();
            boolean isList = parseResult.mark == 1;
            this.literal = isList ? '[' + matched + ']' : '{' + matched + '}';
            this.value = parse(this.literal);
            if ((!isList && this.value instanceof NBTCompound) || (isList && this.value instanceof NBTList)) {
                return true;
            }
        } else {
            this.literalExpr = (Expression<String>)exprs[0];
            return true;
        }
        return false;
    }

    private static Object parse(String literal) {
        try {
            Object nbt = PowerNBT.getApi().parseMojangson(literal);
            if (nbt instanceof NBTCompound || nbt instanceof NBTList) {
                return nbt;
            } else {
                SkRage.getInstance().getLogger().warning("Invalid nbt literal: " + literal + " " + nbt);
            }
        } catch (RuntimeException e) {
            SkRage.getInstance().getLogger().warning("Failed to parse nbt literal: " + literal);
            LogUtil.logThrowable(e);
        }
        return null;
    }

    @Nullable
    @Override
    protected Object[] get(Event e) {
        if (this.value != null) {
            return new Object[]{this.value};
        } else {
            return new Object[]{parse(this.literalExpr.getSingle(e))};
        }
    }

    @Nullable
    @Override
    protected <R> ConvertedExpression<Object, ? extends R> getConvertedExpr(Class<R>... to) {
        for (Class<?> clazz: to) {
            if (clazz == NBTCompound.class || clazz == NBTList.class) {
                return new ConvertedExpression<>(this, (Class<R>) clazz, value -> (R) value);
            }
        }
        return super.getConvertedExpr(to);
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<?> getReturnType() {
        return this.value != null ? this.value.getClass() : Object.class;
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "nbt " + (this.literal != null ? this.literal : this.literalExpr.toString(e, debug));
    }
}
