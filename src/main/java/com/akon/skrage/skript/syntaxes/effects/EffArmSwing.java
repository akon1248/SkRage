package com.akon.skrage.skript.syntaxes.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftLivingEntity;
import org.bukkit.entity.IronGolem;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class EffArmSwing extends Effect {

    static {
        Skript.registerEffect(EffArmSwing.class, "make arm swing %livingentity%");
    }

    private Expression<LivingEntity> entity;

    @Override
    protected void execute(Event e) {
        Optional.ofNullable(this.entity).map(expr -> expr.getSingle(e)).ifPresent(ent -> ((CraftWorld)ent.getWorld()).getHandle().broadcastEntityEffect(((CraftLivingEntity)ent).getHandle(), ent instanceof IronGolem ? (byte)4 : (byte)1));
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        this.entity = (Expression<LivingEntity>)exprs[0];
        return true;
    }
}
