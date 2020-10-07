package com.akon.skrage.skript.syntaxes.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.akon.skrage.utils.entityvisibility.EntityVisibilityManager;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

public class EffEntityVisibility extends Effect {

    static {
        Skript.registerEffect(EffEntityVisibility.class, "hide entity %entity% from %players%", "(show|reveal) entity %entity% to %players%");
    }

    private Expression<Entity> entity;
    private Expression<Player> player;
    private boolean hide;

    @Override
    protected void execute(Event e) {
        if (this.entity != null && this.player != null) {
            Arrays.stream(this.player.getAll(e)).forEach(p -> {
                if (this.hide) {
                    EntityVisibilityManager.hideEntity(p, this.entity.getSingle(e));
                } else {
                    EntityVisibilityManager.showEntity(p, this.entity.getSingle(e));
                }
            });
        }
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        this.entity = (Expression<Entity>)exprs[0];
        this.player = (Expression<Player>)exprs[1];
        this.hide = matchedPattern == 0;
        return true;
    }

}
