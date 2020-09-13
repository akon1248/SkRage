package com.akon.skrage.skript.syntaxes.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftPlayer;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

@Description({"プレイヤーに攻撃をさせます"})
public class EffMakePlayerAttack extends Effect {

    static {
        Skript.registerEffect(EffMakePlayerAttack.class, "make %player% attack %livingentity%");
    }

    private Expression<Player> player;
    private Expression<LivingEntity> entity;

    @Override
    protected void execute(Event e) {
        if (this.player != null && this.entity != null) {
            ((CraftPlayer)this.player.getSingle(e)).getHandle().attack(((CraftLivingEntity)this.entity.getSingle(e)).getHandle());
        }
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        this.player = (Expression<Player>)exprs[0];
        this.entity = (Expression<LivingEntity>)exprs[1];
        return true;
    }
}
