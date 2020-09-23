package com.akon.skrage.skript.syntaxes.ProtocolLib;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import com.akon.skrage.event.EntityAddTrackingPlayerEvent;
import com.akon.skrage.event.EntityRemoveTrackingPlayerEvent;
import com.akon.skrage.event.TrackingEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

public class EvtEntityTracking extends SkriptEvent {

    static {
        Skript.registerEvent("add tracking player", EvtEntityTracking.class, EntityAddTrackingPlayerEvent.class, "add tracking player");
        Skript.registerEvent("remove tracking player", EvtEntityTracking.class, EntityRemoveTrackingPlayerEvent.class, "remove tracking player");
        EventValues.registerEventValue(TrackingEvent.class, Player.class, new Getter<Player, TrackingEvent>() {

            @Nullable
            @Override
            public Player get(TrackingEvent arg) {
                return arg.getPlayer();
            }

        }, 0);
    }

    @Override
    public boolean init(Literal<?>[] args, int matchedPattern, SkriptParser.ParseResult parseResult) {
        return true;
    }

    @Override
    public boolean check(Event e) {
        return e instanceof EntityAddTrackingPlayerEvent || e instanceof EntityRemoveTrackingPlayerEvent;
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "";
    }
}
