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
        Skript.registerEffect(EffBetterPotionEffect.class, "(add|apply) %potioneffecttype% [potion] [[[of] tier] %number%] to %livingentities% [for %-timespan%] [ambient %-boolean%] [hide particle[s] %-boolean%] [mode (0¦default|1¦force|2¦vanilla)]");
    }

    @Override
    protected void execute(Event e) {
        boolean isAmbient = this.ambient == null ? false : this.ambient.getSingle(e);
        boolean hasParticles = this.particle == null || !this.particle.getSingle(e);
        PotionEffect potionEffect = new PotionEffect(this.potionEffectType.getSingle(e), this.timespan == null ? 600 : this.timespan.getSingle(e).getTicks_i() > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int)this.timespan.getSingle(e).getTicks_i(), this.tier == null ? 0 : this.tier.getSingle(e).intValue(), isAmbient, hasParticles);
        for (LivingEntity entity: this.entity.getAll(e)) {
            if (this.mode == 0 || this.mode == 1) {
                entity.addPotionEffect(potionEffect, this.mode == 1);
            } else if (this.mode == 2) {
                if (!entity.hasPotionEffect(potionEffect.getType())) {
                    entity.addPotionEffect(potionEffect, true);
                } else {
                    for (PotionEffect effect: entity.getActivePotionEffects()) {
                        if (effect.getType() == potionEffect.getType() && ((effect.getAmplifier() < potionEffect.getAmplifier()) || (effect.getAmplifier() == potionEffect.getAmplifier() && effect.getDuration() <= potionEffect.getDuration()))) {
                            entity.addPotionEffect(potionEffect, true);
                            break;
                        }
                    }
                }
            }
        }
    }

    @Override
    public String toString(Event e, boolean debug) {
        return null;
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        this.potionEffectType = (Expression<PotionEffectType>) exprs[0];
        int base = 0;
        if (Number.class.isAssignableFrom(exprs[1].getReturnType())) {
            this.tier = (Expression<Number>) exprs[1];
            base++;
        }
        this.entity = (Expression<LivingEntity>) exprs[base + 1];
        if (exprs[base + 2] != null && exprs[base + 2].getReturnType() == Timespan.class) {
            this.timespan = (Expression<Timespan>) exprs[base + 2];
            base++;
        }
        if (exprs[base + 2] != null && exprs[base + 2].getReturnType() == Boolean.class) {
            this.ambient = (Expression<Boolean>) exprs[base + 2];
            base++;
        }
        if (exprs[base + 2] != null && exprs[base + 2].getReturnType() == Boolean.class) {
            this.particle = (Expression<Boolean>) exprs[base + 2];
        }
        this.mode = parseResult.mark;
        return true;
    }
}
