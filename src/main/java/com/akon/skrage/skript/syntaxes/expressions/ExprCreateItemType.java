package com.akon.skrage.skript.syntaxes.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.aliases.ItemType;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import org.bukkit.Material;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class ExprCreateItemType extends SimpleExpression<ItemType> {

    static {
        Skript.registerExpression(ExprCreateItemType.class, ItemType.class, ExpressionType.COMBINED, "[a] [new] item[ ]type %string% [data %-number%]");
    }

    private Expression<String> material;
    private Expression<Number> data;

    @Nullable
    @Override
    protected ItemType[] get(Event e) {
        if (this.material != null) {
            return Optional.ofNullable(Material.getMaterial(this.material.getSingle(e)))
                .map(material -> new ItemStack(material, 1, Optional.ofNullable(this.data)
                    .map(data -> data.getSingle(e).shortValue())
                    .orElse((short)0)))
                .map(item -> new ItemType[]{new ItemType(item)})
                .orElse(null);
        } else {
            return null;
        }
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends ItemType> getReturnType() {
        return ItemType.class;
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        this.material = (Expression<String>)exprs[0];
        if (exprs.length == 2) {
            this.data = (Expression<Number>)exprs[1];
        }
        return true;
    }

}
