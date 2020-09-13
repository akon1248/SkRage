package com.akon.skrage.skript.syntaxes.LibsDisguises;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import me.libraryaddict.disguise.disguisetypes.Disguise;
import me.libraryaddict.disguise.events.DisguiseEvent;
import me.libraryaddict.disguise.events.UndisguiseEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

public class EvtDisguise extends SkriptEvent {

    static {
        if (Bukkit.getPluginManager().isPluginEnabled("LibsDisguises")) {
            Skript.registerEvent("disguise", EvtDisguise.class, DisguiseEvent.class, "disguise").description("エンティティが変身したとき");
            Skript.registerEvent("undisguise", EvtDisguise.class, UndisguiseEvent.class, "undisguise").description("エンティティが変身を解いたとき");
            EventValues.registerEventValue(DisguiseEvent.class, Disguise.class, new Getter<Disguise, DisguiseEvent>() {

                @Nullable
                @Override
                public Disguise get(DisguiseEvent arg) {
                    return arg.getDisguise();
                }

            }, 0);
            EventValues.registerEventValue(UndisguiseEvent.class, Disguise.class, new Getter<Disguise, UndisguiseEvent>() {

                @Nullable
                @Override
                public Disguise get(UndisguiseEvent arg) {
                    return arg.getDisguise();
                }

            }, 0);
            EventValues.registerEventValue(DisguiseEvent.class, Entity.class, new Getter<Entity, DisguiseEvent>() {

                @Nullable
                @Override
                public Entity get(DisguiseEvent arg) {
                    return arg.getEntity();
                }

            }, 0);
            EventValues.registerEventValue(UndisguiseEvent.class, Entity.class, new Getter<Entity, UndisguiseEvent>() {

                @Nullable
                @Override
                public Entity get(UndisguiseEvent arg) {
                    return arg.getEntity();
                }

            }, 0);
        }
    }

    @Override
    public boolean init(Literal<?>[] args, int matchedPattern, SkriptParser.ParseResult parseResult) {
        return true;
    }

    @Override
    public boolean check(Event e) {
        return e instanceof DisguiseEvent || e instanceof UndisguiseEvent;
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "";
    }
}
