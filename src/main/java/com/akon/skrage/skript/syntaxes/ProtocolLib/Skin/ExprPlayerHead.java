package com.akon.skrage.skript.syntaxes.ProtocolLib.Skin;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.akon.skrage.utils.skin.Skin;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

@Description({"スキンからプレイヤーヘッドを作成します"})
public class ExprPlayerHead extends SimpleExpression<ItemStack> {

    static {
        Skript.registerExpression(ExprPlayerHead.class, ItemStack.class, ExpressionType.COMBINED, "player head from [skin] %playerskin%");
    }

    private Expression<Skin> skin;

    @Nullable
    @Override
    protected ItemStack[] get(Event e) {
        if (this.skin != null) {
            return new ItemStack[]{this.skin.getSingle(e).toPlayerHead()};
        }
        return null;
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends ItemStack> getReturnType() {
        return ItemStack.class;
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        this.skin = (Expression<Skin>)exprs[0];
        return true;
    }
}
