package com.akon.skrage.skript.syntaxes.Factions;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.massivecraft.factions.event.EventFactionsChunksChange;
import com.massivecraft.massivecore.ps.PS;
import org.bukkit.Chunk;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

public class ExprChangedChunks extends SimpleExpression<Chunk> {

    static {
        Skript.registerExpression(ExprChangedChunks.class, Chunk.class, ExpressionType.SIMPLE, "event-chunks");
    }

    @Nullable
    @Override
    protected Chunk[] get(Event event) {
        return ((EventFactionsChunksChange) event).getChunks().stream()
                .map(ps -> PS.asBukkitChunk(ps))
                .toArray(Chunk[]::new);
    }

    @Override
    public boolean isSingle() {
        return false;
    }

    @Override
    public Class<? extends Chunk> getReturnType() {
        return Chunk.class;
    }

    @Override
    public String toString(@Nullable Event event, boolean b) {
        return "";
    }

    @Override
    public boolean init(Expression<?>[] expressions, int matched, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        if (!ScriptLoader.isCurrentEvent(EventFactionsChunksChange.class)) {
            Skript.error("この式はon akon (de)claimイベントのみで使用可能です");
            return false;
        }
        return true;
    }
}
