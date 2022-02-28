package com.akon.skrage.skript.syntaxes.Factions;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import com.massivecraft.factions.event.EventFactionsDisband;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

public class EvtFactionsDisband extends SkriptEvent {

    static {
        if (Bukkit.getPluginManager().isPluginEnabled("Factions")) {
            Skript.registerEvent("factions disband", EvtFactionsDisband.class, EventFactionsDisband.class, "faction[s] disband[ed]")
                    .description("あるFactionが解散したとき");
            EventValues.registerEventValue(EventFactionsDisband.class, String.class, new Getter<String, EventFactionsDisband>() {

                @Override
                public String get(EventFactionsDisband arg) {
                    return arg.getFactionId();
                }

            }, 0);
        }
    }

    @Override
    public boolean init(Literal<?>[] literals, int matched, SkriptParser.ParseResult parseResult) {
        return true;
    }

    @Override
    public boolean check(Event event) {
        return event instanceof EventFactionsDisband;
    }

    @Override
    public String toString(@Nullable Event event, boolean b) {
        return "";
    }
}
