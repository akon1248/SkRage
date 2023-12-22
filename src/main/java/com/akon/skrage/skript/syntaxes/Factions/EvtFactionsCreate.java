package com.akon.skrage.skript.syntaxes.Factions;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import com.massivecraft.factions.event.EventFactionsCreate;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

public class EvtFactionsCreate extends SkriptEvent {

    static {
        if (Bukkit.getPluginManager().isPluginEnabled("Factions")) {
            Skript.registerEvent("factions create", EvtFactionsCreate.class, EventFactionsCreate.class, "faction[s] create[d]")
                    .description("あるFactionが作成されたとき");
            EventValues.registerEventValue(EventFactionsCreate.class, String.class, new Getter<String, EventFactionsCreate>() {

                @Override
                public String get(EventFactionsCreate arg) {
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
        return event instanceof EventFactionsCreate;
    }

    @Override
    public String toString(@Nullable Event event, boolean b) {
        return "";
    }
}
