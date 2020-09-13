package com.akon.skrage.skript.syntaxes.events;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser;
import org.bukkit.Statistic;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerStatisticIncrementEvent;
import org.jetbrains.annotations.Nullable;

public class EvtJump extends SkriptEvent {

    static {
        Skript.registerEvent("jump", EvtJump.class, PlayerStatisticIncrementEvent.class, "jump").description("プレイヤーがジャンプしたとき");
    }

    @Override
    public boolean init(Literal<?>[] args, int matchedPattern, SkriptParser.ParseResult parseResult) {
        return true;
    }

    @Override
    public boolean check(Event e) {
        return e instanceof PlayerStatisticIncrementEvent && ((PlayerStatisticIncrementEvent)e).getStatistic() == Statistic.JUMP;
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "";
    }
}
