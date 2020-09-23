package com.akon.skrage.skript.syntaxes.ProtocolLib;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser;
import com.akon.skrage.event.ItemStackShowEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

public class EvtShowItem extends SkriptEvent {

    static {
        Skript.registerEvent("show item", EvtShowItem.class, ItemStackShowEvent.class, "(sen(d|t)|show) item[stack]").description("アイテムがプレイヤーに表示されるとき");
    }

    @Override
    public boolean init(Literal<?>[] args, int matchedPattern, SkriptParser.ParseResult parseResult) {
        return true;
    }

    @Override
    public boolean check(Event e) {
        return e instanceof ItemStackShowEvent;
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "";
    }
}
