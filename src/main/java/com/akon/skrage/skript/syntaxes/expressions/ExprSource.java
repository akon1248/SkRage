package com.akon.skrage.skript.syntaxes.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import com.akon.skrage.utils.ReflectionUtil;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftLivingEntity;
import org.bukkit.entity.*;
import org.bukkit.event.Event;


@Description({"エリアエフェクトクラウド、着火されたTNT、エヴォーカーの牙を発生させたエンティティを取得します"})
public class ExprSource extends SimpleExpression<LivingEntity> {

    private Expression<Entity> entity;

    static {
        Skript.registerExpression(ExprSource.class, LivingEntity.class, ExpressionType.COMBINED, "(owner|source) of %entity%");
    }

    @Override
    protected LivingEntity[] get(Event e) {
        if (this.entity != null) {
            Entity entity;
            if ((entity = this.entity.getSingle(e)) instanceof AreaEffectCloud) {
                return new LivingEntity[]{(LivingEntity) ((AreaEffectCloud) entity).getSource()};
            } else if ((entity = this.entity.getSingle(e)) instanceof TNTPrimed) {
                return new LivingEntity[]{(LivingEntity) ((TNTPrimed) entity).getSource()};
            } else if ((entity = this.entity.getSingle(e)) instanceof EvokerFangs) {
                return new LivingEntity[]{((EvokerFangs)entity).getOwner()};
            }
            return null;
        }
        return null;
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends LivingEntity> getReturnType() {
        return LivingEntity.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        this.entity = (Expression<Entity>)exprs[0];
        return true;
    }

    @Override
    public void change(Event e, Object[] delta, Changer.ChangeMode mode) {
        if (this.entity != null) {
            Entity entity;
            if ((entity = this.entity.getSingle(e)) instanceof AreaEffectCloud) {
                if (mode == Changer.ChangeMode.DELETE || mode == Changer.ChangeMode.RESET || (mode == Changer.ChangeMode.SET && delta[0] == null)) {
                    ((AreaEffectCloud) entity).setSource(null);
                } else if (mode == Changer.ChangeMode.SET && delta[0] instanceof LivingEntity) {
                    ((AreaEffectCloud) entity).setSource((LivingEntity) delta[0]);
                }
            } else if ((entity = this.entity.getSingle(e)) instanceof TNTPrimed) {
                if (mode == Changer.ChangeMode.DELETE || mode == Changer.ChangeMode.RESET || (mode == Changer.ChangeMode.SET && delta[0] == null)) {
                    ReflectionUtil.DEFAULT.setField(((CraftEntity)entity).getHandle(), "source", null);
                } else if (mode == Changer.ChangeMode.SET && delta[0] instanceof LivingEntity) {
                    ReflectionUtil.DEFAULT.setField(((CraftEntity)entity).getHandle(), "source", ((CraftLivingEntity)delta[0]).getHandle());
                }
            } else if ((entity = this.entity.getSingle(e)) instanceof EvokerFangs) {
                if (mode == Changer.ChangeMode.DELETE || mode == Changer.ChangeMode.RESET || (mode == Changer.ChangeMode.SET && delta[0] == null)) {
                    ((EvokerFangs) entity).setOwner(null);
                } else if (mode == Changer.ChangeMode.SET && delta[0] instanceof LivingEntity) {
                    ((EvokerFangs) entity).setOwner((LivingEntity) delta[0]);
                }
            }
        }
    }

    @Override
    public Class<?>[] acceptChange(Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.DELETE || mode == Changer.ChangeMode.RESET || mode == Changer.ChangeMode.SET) {
            return CollectionUtils.array(LivingEntity.class);
        }
        return null;
    }
}
