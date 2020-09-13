package com.akon.skrage.skript.syntaxes.ProtocolLib;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser;
import com.akon.skrage.event.CreativeItemReceiveEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

public class EvtReceiveCreativeItem extends SkriptEvent {

    static {
        if (Bukkit.getPluginManager().isPluginEnabled("ProtocolLib")) {
            Skript.registerEvent("receive creative item", EvtReceiveCreativeItem.class, CreativeItemReceiveEvent.class, "receive creative item");
        }
    }

    @Override
    public boolean init(Literal<?>[] args, int matchedPattern, SkriptParser.ParseResult parseResult) {
        return true;
    }

    @Override
    public boolean check(Event e) {
        return e instanceof CreativeItemReceiveEvent;
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "";
    }
}
