package com.akon.skrage.skript.syntaxes.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import org.bukkit.World;
import org.bukkit.event.Event;

@Description({"ゲームルールの値を取得します"})
public class ExprGameRule extends SimpleExpression<String> {

    private Expression<String> gameRule;
    private Expression<World> world;

    static {
        Skript.registerExpression(ExprGameRule.class, String.class, ExpressionType.COMBINED, "gamerule %string% of %world%", "%world%'s gamerule %string%");
    }

    @Override
    protected String[] get(Event e) {
        if (this.gameRule == null || this.world == null) {
            return null;
        } else {
            return new String[]{this.world.getSingle(e).getGameRuleValue(this.gameRule.getSingle(e))};
        }
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
    public String toString(Event e, boolean debug) {
        return "";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        if (matchedPattern == 0) {
            this.gameRule = (Expression<String>) exprs[0];
            this.world = (Expression<World>) exprs[1];
        } else if (matchedPattern == 1) {
            this.world = (Expression<World>) exprs[0];
            this.gameRule = (Expression<String>) exprs[1];
        }
        return true;
    }

    @Override
    public void change(Event e, Object[] delta, Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.SET && delta[0] instanceof String) {
            this.world.getSingle(e).setGameRuleValue(this.gameRule.getSingle(e), (String)delta[0]);
        }
    }

    @Override
    public Class<?>[] acceptChange(Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.SET) {
            return CollectionUtils.array(String.class);
        }
        return null;
    }
}
