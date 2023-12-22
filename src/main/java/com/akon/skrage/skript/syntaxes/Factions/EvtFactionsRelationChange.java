package com.akon.skrage.skript.syntaxes.Factions;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import com.akon.skrage.skript.syntaxes.CrackShot.EvtReloadStart;
import com.massivecraft.factions.Rel;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.event.EventFactionsRelationChange;
import com.shampaggon.crackshot.events.WeaponReloadEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

public class EvtFactionsRelationChange extends SkriptEvent {

    static {
        if (Bukkit.getPluginManager().isPluginEnabled("Factions")) {
            Skript.registerEvent("factions relation change", EvtReloadStart.class, WeaponReloadEvent.class, "[faction[s]] relation (change|update)[d]")
                    .description("Faction同士の関係が変化したとき");
            EventValues.registerEventValue(EventFactionsRelationChange.class, Rel.class, new Getter<Rel, EventFactionsRelationChange>() {

                @Override
                @Nullable
                public Rel get(EventFactionsRelationChange arg) {
                    return arg.getNewRelation();
                }

            }, 0);
            EventValues.registerEventValue(EventFactionsRelationChange.class, Faction.class, new Getter<Faction, EventFactionsRelationChange>() {

                @Nullable
                @Override
                public Faction get(EventFactionsRelationChange arg) {
                    return arg.getOtherFaction();
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
        return event instanceof EventFactionsRelationChange;
    }

    @Override
    public String toString(@Nullable Event event, boolean b) {
        return "";
    }
}
