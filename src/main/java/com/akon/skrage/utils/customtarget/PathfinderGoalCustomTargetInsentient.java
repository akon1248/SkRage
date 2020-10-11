package com.akon.skrage.utils.customtarget;

import com.google.common.base.Predicate;
import net.minecraft.server.v1_12_R1.*;
import org.bukkit.event.entity.EntityTargetEvent;

import java.util.List;

//Spigotビルド時に生成されるソースコードからの移植
public class PathfinderGoalCustomTargetInsentient extends PathfinderGoal implements CustomTarget {

    private final EntityInsentient mob;
    private final Predicate<EntityLiving> predicate;
    private final PathfinderGoalNearestAttackableTarget.DistanceComparator sorter;
    private EntityLiving target;

    public PathfinderGoalCustomTargetInsentient(EntityInsentient entityinsentient, Predicate<? super EntityLiving> predicate) {
        this.mob = entityinsentient;
        this.predicate = entityliving -> {
            if (entityliving == null) {
                return false;
            }
            double d0 = PathfinderGoalCustomTargetInsentient.this.getFollowRange();

            if (entityliving.isSneaking()) {
                d0 *= 0.800000011920929D;
            }

            return !entityliving.isInvisible() && ((predicate == null || predicate.apply(entityliving)) && !((double)entityliving.g(PathfinderGoalCustomTargetInsentient.this.mob) > d0) && PathfinderGoalTarget.a(PathfinderGoalCustomTargetInsentient.this.mob, entityliving, false, true));
        };
        this.sorter = new PathfinderGoalNearestAttackableTarget.DistanceComparator(entityinsentient);
    }

    @Override
    public boolean a() {
        double d0 = this.getFollowRange();
        List<EntityLiving> list = this.mob.world.a(EntityLiving.class, this.mob.getBoundingBox().grow(d0, 4.0D, d0), this.predicate);

        list.sort(this.sorter);
        if (list.isEmpty()) {
            return false;
        } else {
            this.target = list.get(0);
            return true;
        }
    }

    @Override
    public boolean b() {
        EntityLiving entityliving = this.mob.getGoalTarget();

        if (entityliving == null) {
            return false;
        } else if (!entityliving.isAlive()) {
            return false;
        } else {
            double d0 = this.getFollowRange();

            return !(this.mob.h(entityliving) > d0 * d0) && (!(entityliving instanceof EntityPlayer) || !((EntityPlayer) entityliving).playerInteractManager.isCreative());
        }
    }

    @Override
    public void c() {
        this.mob.setGoalTarget(this.target, EntityTargetEvent.TargetReason.CLOSEST_ENTITY, true);
        super.c();
    }

    @Override
    public void d() {
        this.mob.setGoalTarget(null);
        super.c();
    }

    protected double getFollowRange() {
        AttributeInstance attributeinstance = this.mob.getAttributeInstance(GenericAttributes.FOLLOW_RANGE);

        return attributeinstance == null ? 16.0D : attributeinstance.getValue();
    }
}
