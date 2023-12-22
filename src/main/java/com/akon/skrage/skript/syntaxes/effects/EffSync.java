package com.akon.skrage.skript.syntaxes.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.TriggerItem;
import ch.njol.skript.variables.Variables;
import ch.njol.util.Kleenean;
import com.akon.skrage.SkRage;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

public class EffSync extends Effect {

    static {
        Skript.registerEffect(EffSync.class, "(sync[hronize]|wait until [the] end of [(the|this)] tick)");
    }

    @Override
    protected void execute(Event e) {
        throw new UnsupportedOperationException();
    }

    @Nullable
    @Override
    protected TriggerItem walk(Event e) {
        TriggerItem triggerItem = this.getNext();
        if (triggerItem == null) {
            return null;
        }
        Object localVars = Variables.removeLocals(e);
        Bukkit.getScheduler().runTask(SkRage.getInstance(), () -> {
            if (localVars != null) {
                Variables.setLocalVariables(e, localVars);
            }
            TriggerItem.walk(triggerItem, e);
            Variables.removeLocals(e);
        });
        return null;
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "synchronize";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        return true;
    }
}
