package com.akon.skrage.skript.syntaxes.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.akon.skrage.utils.damagesource.DamageSourceBuilder;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

@Description({"指定されたタイプからDamageSourceを作成します(タイプは自由ですがデフォルトにないものを使用した場合に死亡メッセージが通常通り表示されなくなります)"})
public class ExprNewDamageSource extends SimpleExpression<DamageSourceBuilder> {

    static {
        Skript.registerExpression(ExprNewDamageSource.class, DamageSourceBuilder.class, ExpressionType.COMBINED, "[a] [new] damage[ ]source with type %string%");
    }

    private Expression<String> type;

    @Nullable
    @Override
    protected DamageSourceBuilder[] get(Event e) {
        if (this.type != null) {
            return new DamageSourceBuilder[]{new DamageSourceBuilder(this.type.getSingle(e))};
        }
        return null;
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends DamageSourceBuilder> getReturnType() {
        return DamageSourceBuilder.class;
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        this.type = (Expression<String>)exprs[0];
        return true;
    }
}
