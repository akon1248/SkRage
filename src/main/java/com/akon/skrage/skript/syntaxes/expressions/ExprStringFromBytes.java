package com.akon.skrage.skript.syntaxes.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import org.apache.commons.lang.ArrayUtils;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@Description({"バイトのリストから文字列を作成します(UTF-8)"})
public class ExprStringFromBytes extends SimpleExpression<String> {

    static {
        Skript.registerExpression(ExprStringFromBytes.class, String.class, ExpressionType.COMBINED, "(string|text) from [bytes] %numbers%");
    }

    private Expression<Number> bytes;

    @Nullable
    @Override
    protected String[] get(Event e) {
        if (this.bytes != null) {
            return new String[]{new String(ArrayUtils.toPrimitive(Arrays.stream(this.bytes.getAll(e)).map(Number::byteValue).toArray(Byte[]::new)), StandardCharsets.UTF_8)};
        }
        return null;
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        this.bytes = (Expression<Number>)exprs[0];
        return true;
    }
}
