package com.akon.skrage.utils;

import com.google.common.base.Predicate;
import net.minecraft.server.v1_12_R1.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.bukkit.event.entity.EntityTargetEvent;

import org.jetbrains.annotations.Nullable;
import java.util.List;

//Spigotビルド時に生成されるソースコードからの移植
public class PathfinderGoalNearestAttackableTargetInsentient extends PathfinderGoal {

    private static final Logger a = LogManager.getLogger();
    private final EntityInsentient b;
    private final Predicate<EntityLiving> c;
    private final PathfinderGoalNearestAttackableTarget.DistanceComparator d;
    private EntityLiving e;
    private final Class<? extends EntityLiving> f;

    public PathfinderGoalNearestAttackableTargetInsentient(EntityInsentient entityinsentient, Class<? extends EntityLiving> oclass) {
        this.b = entityinsentient;
        this.f = oclass;
        if (entityinsentient instanceof EntityCreature) {
            PathfinderGoalNearestAttackableTargetInsentient.a.warn("Use NearestAttackableTargetGoal.class for PathfinerMob mobs!");
        }

        this.c = new Predicate<EntityLiving>() {
            public boolean a(@Nullable EntityLiving entityliving) {
                if (entityliving == null) {
                    return false;
                }
                double d0 = PathfinderGoalNearestAttackableTargetInsentient.this.f();

                if (entityliving.isSneaking()) {
                    d0 *= 0.800000011920929D;
                }

                return !entityliving.isInvisible() && (!((double) entityliving.g(PathfinderGoalNearestAttackableTargetInsentient.this.b) > d0) && PathfinderGoalTarget.a(PathfinderGoalNearestAttackableTargetInsentient.this.b, entityliving, false, true));
            }

            public boolean apply(@Nullable EntityLiving entity) {
                return this.a(entity);
            }
        };
        this.d = new PathfinderGoalNearestAttackableTarget.DistanceComparator(entityinsentient);
    }

    public boolean a() {
        double d0 = this.f();
        List<EntityLiving> list = this.b.world.a(this.f, this.b.getBoundingBox().grow(d0, 4.0D, d0), this.c);

        list.sort(this.d);
        if (list.isEmpty()) {
            return false;
        } else {
            this.e = list.get(0);
            return true;
        }
    }

    public boolean b() {
        EntityLiving entityliving = this.b.getGoalTarget();

        if (entityliving == null) {
            return false;
        } else if (!entityliving.isAlive()) {
            return false;
        } else {
            double d0 = this.f();

            return !(this.b.h(entityliving) > d0 * d0) && (!(entityliving instanceof EntityPlayer) || !((EntityPlayer) entityliving).playerInteractManager.isCreative());
        }
    }

    public void c() {
        this.b.setGoalTarget(this.e, EntityTargetEvent.TargetReason.CLOSEST_ENTITY, true);
        super.c();
    }

    public void d() {
        this.b.setGoalTarget((EntityLiving) null);
        super.c();
    }

    protected double f() {
        AttributeInstance attributeinstance = this.b.getAttributeInstance(GenericAttributes.FOLLOW_RANGE);

        return attributeinstance == null ? 16.0D : attributeinstance.getValue();
    }
}
