package com.akon.skrage.skript.syntaxes.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.akon.skrage.utils.damagesource.DamageSourceBuilder;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

@Description({"デフォルトに存在しているDamageSourceを取得します","※この構文を使用して取得できるDamageSourceは不変です"})
public class ExprDamageSources extends SimpleExpression<DamageSourceBuilder> {

    static {
        Skript.registerExpression(ExprDamageSources.class, DamageSourceBuilder.class, ExpressionType.COMBINED, "damage[ ]source (0¦fire|1¦lightning|2¦burn|3¦lava|4¦hot floor|5¦stuck|6¦cramming|7¦drown|8¦starve|9¦cactus|10¦fall|11¦fly into wall|12¦out of world|13¦generic|14¦magic|15¦wither|16¦anvil|17¦falling block|18¦dragon breath|19¦fireworks)");
    }

    private int type;

    @Nullable
    @Override
    protected DamageSourceBuilder[] get(Event e) {
        DamageSourceBuilder damageSource = null;
        switch (this.type) {
            case 0:
                damageSource = DamageSourceBuilder.FIRE;
                break;
            case 1:
                damageSource = DamageSourceBuilder.LIGHTNING;
                break;
            case 2:
                damageSource = DamageSourceBuilder.BURN;
                break;
            case 3:
                damageSource = DamageSourceBuilder.LAVA;
                break;
            case 4:
                damageSource = DamageSourceBuilder.HOT_FLOOR;
                break;
            case 5:
                damageSource = DamageSourceBuilder.STUCK;
                break;
            case 6:
                damageSource = DamageSourceBuilder.CRAMMING;
                break;
            case 7:
                damageSource = DamageSourceBuilder.DROWN;
                break;
            case 8:
                damageSource = DamageSourceBuilder.STARVE;
                break;
            case 9:
                damageSource = DamageSourceBuilder.CACTUS;
                break;
            case 10:
                damageSource = DamageSourceBuilder.FALL;
                break;
            case 11:
                damageSource = DamageSourceBuilder.FLY_INTO_WALL;
                break;
            case 12:
                damageSource = DamageSourceBuilder.OUT_OF_WORLD;
                break;
            case 13:
                damageSource = DamageSourceBuilder.GENERIC;
                break;
            case 14:
                damageSource = DamageSourceBuilder.MAGIC;
                break;
            case 15:
                damageSource = DamageSourceBuilder.WITHER;
                break;
            case 16:
                damageSource = DamageSourceBuilder.ANVIL;
                break;
            case 17:
                damageSource = DamageSourceBuilder.FALLING_BLOCK;
                break;
            case 18:
                damageSource = DamageSourceBuilder.DRAGON_BREATH;
                break;
            case 19:
                damageSource = DamageSourceBuilder.FIREWORKS;
        }
        return new DamageSourceBuilder[]{damageSource};
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends DamageSourceBuilder> getReturnType() {
        return DamageSourceBuilder.class;
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        this.type = parseResult.mark;
        return true;
    }
}
