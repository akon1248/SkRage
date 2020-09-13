package com.akon.skrage.skript.syntaxes.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.akon.skrage.utils.NMSUtil;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;

@Description({"通常のExplosion構文に爆発時の炎上、爆発を引き起こしたエンティティの指定を可能にしたものです"})
public class EffBetterExplosion extends Effect {

    private Expression<Entity> entity;
    private boolean isSafe;
    private Expression<Number> power;
    private Expression<Location> location;
    private Expression<Boolean> fire;

    static {
        Skript.registerEffect(EffBetterExplosion.class, "(create|make) [(a|an)] (1¦safe|) explosion (of|with) (force|strength|power) %number% at %location% [fire %-boolean%]", "(create|make) [(a|an)] %entity% (1¦safe|) explosion (of|with) (force|strength|power) %number% at %location% [fire %-boolean%]");
    }

    @Override
    protected void execute(Event e) {
        if (this.power != null && this.location != null) {
            NMSUtil.createExplosion(this.entity == null ? null : entity.getSingle(e), this.location.getSingle(e), this.power.getSingle(e).floatValue(), this.fire == null ? false : this.fire.getSingle(e), this.isSafe);
        }

    }

    @Override
    public String toString(Event e, boolean debug) {
        return "";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        if (matchedPattern == 0) {
            this.power = (Expression<Number>)exprs[0];
            this.location = (Expression<Location>)exprs[1];
            if (exprs[2] != null) this.fire = (Expression<Boolean>)exprs[2];
        } else if (matchedPattern == 1) {
            this.entity = (Expression<Entity>)exprs[0];
            this.power = (Expression<Number>)exprs[1];
            this.location = (Expression<Location>)exprs[2];
            if (exprs[3] != null) this.fire = (Expression<Boolean>)exprs[3];
        }
        this.isSafe = parseResult.mark == 1;
        return true;
    }
}
