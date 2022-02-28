package com.akon.skrage.skript.syntaxes.Factions;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.event.EventFactionsChunksChange;
import com.massivecraft.factions.event.EventFactionsRelationChange;
import com.massivecraft.massivecore.ps.PS;
import org.bukkit.Chunk;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

public class ExprAnotherFactionRelationChanged extends SimpleExpression<Faction> {

    static {
        Skript.registerExpression(ExprAnotherFactionRelationChanged.class, Faction.class, ExpressionType.SIMPLE, "another faction");
    }

    @Nullable
    @Override
    protected Faction[] get(Event event) {
        return new Faction[]{((EventFactionsRelationChange) event).getOtherFaction()};
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends Faction> getReturnType() {
        return Faction.class;
    }

    @Override
    public String toString(@Nullable Event event, boolean b) {
        return "";
    }

    @Override
    public boolean init(Expression<?>[] expressions, int matched, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        if (!ScriptLoader.isCurrentEvent(EventFactionsRelationChange.class)) {
            Skript.error("この式はon relation changeイベントのみで使用可能です");
            return false;
        }
        return true;
    }
}
