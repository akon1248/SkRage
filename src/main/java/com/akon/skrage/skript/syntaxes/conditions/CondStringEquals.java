package com.akon.skrage.skript.syntaxes.conditions;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;

@Description({"大文字と小文字を無視せずに文字列を比較します"})
public class CondStringEquals extends Condition {

    static {
        Skript.registerCondition(CondStringEquals.class, "%string% equals strict %string%");
    }

    private Expression<String> str1;
    private Expression<String> str2;

    @Override
    public boolean check(Event e) {
        if (this.str1 == null || this.str2 == null) {
            return false;
        }
        return this.str1.getSingle(e).equals(this.str2.getSingle(e));
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        this.str1 = (Expression<String>)exprs[0];
        this.str2 = (Expression<String>)exprs[1];
        return true;
    }

}
