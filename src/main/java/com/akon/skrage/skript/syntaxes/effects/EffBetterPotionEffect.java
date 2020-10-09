package com.akon.skrage.skript.syntaxes.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.util.Timespan;
import ch.njol.util.Kleenean;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.Optional;

@Description({"ポーション効果をエンティティに追加します", "パーティクルを表示するかどうかとパーティクルを見えずらくするかどうかの設定が可能です", "モードを変更することでポーション効果を付与する条件が設定可能です(デフォルトのモード: default)", "default: 対象のエンティティが指定された種類のポーション効果を持っていなかった場合", "force: 条件なしに強制的に付与します", "vanilla: バニラでポーション効果が付与される条件と同じです"})
public class EffBetterPotionEffect extends Effect {

    private Expression<PotionEffectType> potionEffectType;
    private Expression<Number> tier;
    private Expression<LivingEntity> entity;
    private Expression<Timespan> timespan;
    private Expression<Boolean> ambient;
    private Expression<Boolean> particle;
    private int mode;

    static {
        Skript.registerEffect(EffBetterPotionEffect.class, "(add|apply) %potioneffecttype% [potion] [[[of] tier] %-number%] to %livingentities% [for %-timespan%] [ambient %-boolean%] [hide particle[s] %-boolean%] [mode (0¦default|1¦force|2¦vanilla)]");
    }

    @Override
    protected void execute(Event e) {
        if (this.potionEffectType != null && this.entity != null) {
            boolean isAmbient = Optional.ofNullable(this.ambient).map(expr -> expr.getSingle(e)).orElse(false);
            boolean hasParticles = !Optional.ofNullable(this.particle).map(expr -> expr.getSingle(e)).orElse(false);
            int ticks = Optional.ofNullable(this.timespan).map(expr -> expr.getSingle(e)).map(Timespan::getTicks_i).map(Long::intValue).orElse(600);
            PotionEffect potionEffect = new PotionEffect(this.potionEffectType.getSingle(e), ticks, Optional.ofNullable(this.tier).map(expr -> expr.getSingle(e)).map(Number::intValue).orElse(0), isAmbient, hasParticles);
            for (LivingEntity entity:  this.entity.getAll(e)) {
                if (this.mode == 0 || this.mode == 1) {
                    entity.addPotionEffect(potionEffect, this.mode == 1);
                } else if (this.mode == 2) {
                    if (!entity.hasPotionEffect(potionEffect.getType())) {
                        entity.addPotionEffect(potionEffect, true);
                    } else {
                        entity.getActivePotionEffects()
                            .stream()
                            .filter(effect -> effect.getType() == potionEffect.getType() && ((effect.getAmplifier() < potionEffect.getAmplifier()) || (effect.getAmplifier() == potionEffect.getAmplifier() && effect.getDuration() <= potionEffect.getDuration())))
                            .findFirst()
                            .ifPresent(effect -> entity.addPotionEffect(potionEffect, true));
                    }
                }
            }
        }
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        this.potionEffectType = (Expression<PotionEffectType>)exprs[0];
        this.tier = (Expression<Number>)exprs[1];
        this.entity = (Expression<LivingEntity>) exprs[2];
        this.timespan = (Expression<Timespan>)exprs[3];
        this.ambient = (Expression<Boolean>)exprs[4];
        this.particle = (Expression<Boolean>)exprs[5];
        this.mode = parseResult.mark;
        return true;
    }
}
