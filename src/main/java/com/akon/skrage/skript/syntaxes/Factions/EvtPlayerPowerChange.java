package com.akon.skrage.skript.syntaxes.Factions;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import com.massivecraft.factions.event.EventFactionsPowerChange;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

public class EvtPlayerPowerChange extends SkriptEvent {

    static {
        if (Bukkit.getPluginManager().isPluginEnabled("Factions")) {
            Skript.registerEvent("player power change", EvtPlayerPowerChange.class, EventFactionsPowerChange.class, "[faction[s]] [player] power (change|update)[d]")
                    .description("プレイヤーのpowerが変化したとき");
            EventValues.registerEventValue(EventFactionsPowerChange.class, Number.class, new Getter<Number, EventFactionsPowerChange>() {

                @Nullable
                @Override
                public Number get(EventFactionsPowerChange arg) {
                    return arg.getNewPower() - arg.getMPlayer().getPower();
                }

            }, 0);
            EventValues.registerEventValue(EventFactionsPowerChange.class, Player.class, new Getter<Player, EventFactionsPowerChange>() {

                @Nullable
                @Override
                public Player get(EventFactionsPowerChange arg) {
                    return arg.getMPlayer().getPlayer();
                }

            }, 0);
            EventValues.registerEventValue(EventFactionsPowerChange.class, EventFactionsPowerChange.PowerChangeReason.class, new Getter<EventFactionsPowerChange.PowerChangeReason, EventFactionsPowerChange>() {

                @Nullable
                @Override
                public EventFactionsPowerChange.PowerChangeReason get(EventFactionsPowerChange arg) {
                    return arg.getReason();
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
        return event instanceof EventFactionsPowerChange;
    }

    @Override
    public String toString(@Nullable Event event, boolean b) {
        return "";
    }
}
