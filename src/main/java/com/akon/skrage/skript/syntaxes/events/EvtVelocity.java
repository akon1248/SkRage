package com.akon.skrage.skript.syntaxes.events;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerVelocityEvent;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class EvtVelocity extends SkriptEvent {

    static {
        Skript.registerEvent("player velocity", EvtVelocity.class, PlayerVelocityEvent.class, "[player] velocity").description("プレイヤーのベクトルが変化したとき");
        EventValues.registerEventValue(PlayerVelocityEvent.class, Vector.class, new Getter<Vector, PlayerVelocityEvent>() {

            @Nullable
            @Override
            public Vector get(@NotNull PlayerVelocityEvent arg) {
                return arg.getVelocity();
            }

        }, 0);
    }

    @Override
    public boolean init(Literal<?>[] args, int matchedPattern, SkriptParser.ParseResult parseResult) {
        return true;
    }

    @Override
    public boolean check(@NotNull Event e) {
        return e instanceof PlayerVelocityEvent;
    }

    @Override
    public @NotNull String toString(@Nullable Event e, boolean debug) {
        return "";
    }
}
