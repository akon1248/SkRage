package com.akon.skrage.skript.syntaxes.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

@Description({"エンティティの幅と高さを取得します"})
public class ExprEntitySize extends SimpleExpression<Number> {

    static {
        Skript.registerExpression(ExprEntitySize.class, Number.class, ExpressionType.COMBINED, "height of %entity%", "%entity%'s height", "width of %entity%", "%entity%'s width");
    }

    private Expression<Entity> entity;
    private boolean height;

    @Nullable
    @Override
    protected Number[] get(Event e) {
        if (this.entity != null) {
            if (this.height) {
                return new Number[]{this.entity.getSingle(e).getHeight()};
            } else {
                return new Number[]{this.entity.getSingle(e).getWidth()};
            }
        }
        return null;
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends Number> getReturnType() {
        return Number.class;
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        this.entity = (Expression<Entity>)exprs[0];
        this.height = matchedPattern <= 1;
        return true;
    }
}
