package com.akon.skrage.skript.syntaxes.Factions;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import com.massivecraft.factions.entity.Faction;
import com.massivecraft.factions.event.EventFactionsChunkChangeType;
import com.massivecraft.factions.event.EventFactionsChunksChange;
import lombok.val;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

public class EvtFactionsChunkChange extends SkriptEvent {

    private boolean claimed;

    static {
        if (Bukkit.getPluginManager().isPluginEnabled("Factions")) {
            Skript.registerEvent("factions land claimed/declaimed", EvtFactionsChunkChange.class, EventFactionsChunksChange.class, "akon [faction[s]] [land] claim[ed]", "akon [faction[s]] [land] (de|un)claim[ed]")
                    .description("あるFactionがチャンクを獲得又は手放したとき");
            EventValues.registerEventValue(EventFactionsChunksChange.class, Faction.class, new Getter<Faction, EventFactionsChunksChange>() {

                @Override
                public Faction get(EventFactionsChunksChange arg) {
                    return arg.getNewFaction();
                }

            }, 0);
            EventValues.registerEventValue(EventFactionsChunksChange.class, Player.class, new Getter<Player, EventFactionsChunksChange>() {

                @Nullable
                @Override
                public Player get(EventFactionsChunksChange arg) {
                    return arg.getMPlayer().getPlayer();
                }

            }, 0);
        }
    }

    @Override
    public boolean init(Literal<?>[] literals, int matched, SkriptParser.ParseResult parseResult) {
        this.claimed = matched == 0;
        return true;
    }

    @Override
    public boolean check(Event event) {
        if (!(event instanceof EventFactionsChunksChange)) {
            return false;
        }
        EventFactionsChunksChange e = (EventFactionsChunksChange) event;
        val type = Arrays.stream(EventFactionsChunkChangeType.values())
                .filter(t -> e.getChunkType().values().stream().allMatch(t0 -> t0 == t))
                .findFirst()
                .orElse(EventFactionsChunkChangeType.NONE);
        return this.claimed ? type == EventFactionsChunkChangeType.BUY : type == EventFactionsChunkChangeType.CONQUER || type == EventFactionsChunkChangeType.PILLAGE;
    }

    @Override
    public String toString(@Nullable Event event, boolean b) {
        return "";
    }
}
