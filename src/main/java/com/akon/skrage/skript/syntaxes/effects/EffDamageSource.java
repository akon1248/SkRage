package com.akon.skrage.skript.syntaxes.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.akon.skrage.utils.damagesource.DamageSourceBuilder;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

@Description({"DamageSourceを用いてエンティティにダメージを与えます","※一度使用したDamageSourceは不変になります"})
public class EffDamageSource extends Effect {

    static {
        Skript.registerEffect(EffDamageSource.class, "damage %entity% by %number% with damage[ ]source %damagesource%");
    }

    private Expression<Entity> entity;
    private Expression<Number> damage;
    private Expression<DamageSourceBuilder> damageSource;

    @Override
    protected void execute(Event e) {
        if (this.entity != null && this.damage != null && this.damageSource != null) {
            this.damageSource.getSingle(e).buildAndDamage(this.entity.getSingle(e), this.damage.getSingle(e).floatValue());
        }
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        this.entity = (Expression<Entity>)exprs[0];
        this.damage = (Expression<Number>)exprs[1];
        this.damageSource = (Expression<DamageSourceBuilder>)exprs[2];
        return true;
    }
}
