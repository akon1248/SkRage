package com.akon.skrage.utils.damagesource;

import com.akon.skrage.SkRage;
import com.akon.skrage.utils.NMSUtil;
import com.akon.skrage.utils.ReflectionUtil;
import lombok.AccessLevel;
import lombok.Getter;
import net.minecraft.server.v1_12_R1.*;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftLightningStrike;
import org.bukkit.craftbukkit.v1_12_R1.event.CraftEventFactory;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.UUID;
import java.util.WeakHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Getter
public class DamageSourceBuilder {

    private static final AttributeModifier KNOCKBACK_MODIFIER = new AttributeModifier(UUID.fromString("236315ac-f7ea-430b-8a09-9b3336bc4cc4"), "DamageSource Knockback Modifier", 1, AttributeModifier.Operation.ADD_NUMBER);
    private static final WeakHashMap<DamageSource, DamageSourceBuilder> DAMAGE_SOURCE_MAP = new WeakHashMap<>();
    public static DamageSourceBuilder currentDamage;

    public static final DamageSourceBuilder FIRE = new DamageSourceBuilder(DamageSource.FIRE);
    public static final DamageSourceBuilder LIGHTNING = new DamageSourceBuilder(DamageSource.LIGHTNING);
    public static final DamageSourceBuilder BURN = new DamageSourceBuilder(DamageSource.BURN);
    public static final DamageSourceBuilder LAVA = new DamageSourceBuilder(DamageSource.LAVA);
    public static final DamageSourceBuilder HOT_FLOOR = new DamageSourceBuilder(DamageSource.HOT_FLOOR);
    public static final DamageSourceBuilder STUCK = new DamageSourceBuilder(DamageSource.STUCK);
    public static final DamageSourceBuilder CRAMMING = new DamageSourceBuilder(DamageSource.CRAMMING);
    public static final DamageSourceBuilder DROWN = new DamageSourceBuilder(DamageSource.DROWN);
    public static final DamageSourceBuilder STARVE = new DamageSourceBuilder(DamageSource.STARVE);
    public static final DamageSourceBuilder CACTUS = new DamageSourceBuilder(DamageSource.CACTUS);
    public static final DamageSourceBuilder FALL = new DamageSourceBuilder(DamageSource.FALL);
    public static final DamageSourceBuilder FLY_INTO_WALL = new DamageSourceBuilder(DamageSource.FLY_INTO_WALL);
    public static final DamageSourceBuilder OUT_OF_WORLD = new DamageSourceBuilder(DamageSource.OUT_OF_WORLD);
    public static final DamageSourceBuilder GENERIC = new DamageSourceBuilder(DamageSource.GENERIC);
    public static final DamageSourceBuilder MAGIC = new DamageSourceBuilder(DamageSource.MAGIC);
    public static final DamageSourceBuilder WITHER = new DamageSourceBuilder(DamageSource.WITHER);
    public static final DamageSourceBuilder ANVIL = new DamageSourceBuilder(DamageSource.ANVIL);
    public static final DamageSourceBuilder FALLING_BLOCK = new DamageSourceBuilder(DamageSource.FALLING_BLOCK);
    public static final DamageSourceBuilder DRAGON_BREATH = new DamageSourceBuilder(DamageSource.DRAGON_BREATH);
    public static final DamageSourceBuilder FIREWORKS = new DamageSourceBuilder(DamageSource.t);
    
    @Getter(AccessLevel.NONE)
    private DamageSource damageSource;
    private String type;
    private boolean ignoreArmor;
    private boolean ignoreCreativeMode;
    private boolean starvation;
    private float exhaustion;
    private boolean fire;
    private boolean isProjectile;
    private boolean difficultyScaled;
    private boolean magic;
    private boolean explosion;
    private boolean sweep;
    private boolean thorns;
    private boolean ignoreNoDamageTicks;
    private boolean preventKnockback;
    private Entity entity;
    private Entity projectile;

    //ビルドが完了した状態のインスタンスを作成する
    private DamageSourceBuilder(DamageSource damageSource) {
        this.type = damageSource.translationIndex;
        this.entity = Optional.of(damageSource).map(DamageSource::getEntity).map(net.minecraft.server.v1_12_R1.Entity::getBukkitEntity).orElse(null);
        if (damageSource instanceof EntityDamageSourceIndirect) {
            this.projectile = Optional.of(damageSource).map(DamageSource::i).map(net.minecraft.server.v1_12_R1.Entity::getBukkitEntity).orElse(null);
        }
        this.ignoreArmor = damageSource.ignoresArmor();
        this.ignoreCreativeMode = damageSource.ignoresInvulnerability();
        this.starvation = damageSource.isStarvation();
        this.exhaustion = damageSource.getExhaustionCost();
        this.fire = damageSource.o();
        this.isProjectile = damageSource.a();
        this.difficultyScaled = damageSource.r();
        this.magic = damageSource.isMagic();
        this.explosion = damageSource.isExplosion();
        this.sweep = damageSource.isSweep();
        this.damageSource = damageSource;
        DAMAGE_SOURCE_MAP.put(damageSource, this);
    }

    public DamageSourceBuilder(String type, Entity entity, Entity projectile) {
        this.type = type;
        this.entity = entity;
        this.projectile = projectile;
    }

    public DamageSourceBuilder(String type, Entity entity) {
        this(type, entity, null);
    }

    public DamageSourceBuilder(String type) {
        this(type, null, null);
    }

    public DamageSourceBuilder setType(String type) {
        this.checkBuilt();
        this.type = type;
        return this;
    }

    public DamageSourceBuilder setIgnoreArmor(boolean ignoreArmor) {
        this.checkBuilt();
        this.ignoreArmor = ignoreArmor;
        return this;
    }

    public DamageSourceBuilder setIgnoreCreativeMode(boolean ignoreCreativeMode) {
        this.checkBuilt();
        this.ignoreCreativeMode = ignoreCreativeMode;
        return this;
    }

    public DamageSourceBuilder setStarvation(boolean starvation) {
        this.checkBuilt();
        this.starvation = starvation;
        return this;
    }

    public DamageSourceBuilder setExhaustion(float exhaustion) {
        this.checkBuilt();
        this.exhaustion = exhaustion;
        return this;
    }

    public DamageSourceBuilder setFire(boolean fire) {
        this.checkBuilt();
        this.fire = fire;
        return this;
    }

    public DamageSourceBuilder setProjectile(boolean projectile) {
        this.checkBuilt();
        isProjectile = projectile;
        return this;
    }

    public DamageSourceBuilder setDifficultyScaled(boolean difficultyScaled) {
        this.checkBuilt();
        this.difficultyScaled = difficultyScaled;
        return this;
    }

    public DamageSourceBuilder setMagic(boolean magic) {
        this.checkBuilt();
        this.magic = magic;
        return this;
    }

    public DamageSourceBuilder setExplosion(boolean explosion) {
        this.checkBuilt();
        this.explosion = explosion;
        return this;
    }

    public DamageSourceBuilder setSweep(boolean sweep) {
        this.checkBuilt();
        this.sweep = sweep;
        return this;
    }

    public DamageSourceBuilder setThorns(boolean thorns) {
        this.checkBuilt();
        this.thorns = thorns;
        return this;
    }

    public DamageSourceBuilder setIgnoreNoDamageTicks(boolean ignoreNoDamageTicks) {
        this.checkBuilt();
        this.ignoreNoDamageTicks = ignoreNoDamageTicks;
        return this;
    }

    public DamageSourceBuilder setPreventKnockback(boolean preventKnockback) {
        this.checkBuilt();
        this.preventKnockback = preventKnockback;
        return this;
    }

    public DamageSourceBuilder setEntity(Entity entity) {
        this.checkBuilt();
        this.entity = entity;
        return this;
    }

    public DamageSourceBuilder setProjectile(Entity projectile) {
        this.checkBuilt();
        this.projectile = projectile;
        return this;
    }

    private DamageSource build() {
        this.checkBuilt();
        try {
            DamageSource damageSource;
            if (this.projectile != null) {
                damageSource = new EntityDamageSourceIndirect(this.type, ((CraftEntity)this.projectile).getHandle(), Optional.ofNullable(this.entity).map(entity -> ((CraftEntity)entity).getHandle()).orElse(null));
            } else if (this.entity != null) {
                damageSource = new EntityDamageSource(this.type, ((CraftEntity)this.entity).getHandle());
            } else {
                damageSource = ReflectionUtil.invokeConstructor(DamageSource.class, new Class[]{String.class}, new Object[]{this.type});
            }
            if (this.ignoreArmor) {
                ReflectionUtil.invokeMethod(damageSource, "setIgnoreArmor");
            }
            if (this.ignoreCreativeMode) {
                ReflectionUtil.invokeMethod(damageSource, "l");
            }
            if (this.starvation) {
                ReflectionUtil.invokeMethod(damageSource, "m");
            }
            if (this.fire) {
                ReflectionUtil.invokeMethod(damageSource, "setExplosion");
            }
            if (this.isProjectile) {
                damageSource.b();
            }
            if (this.difficultyScaled) {
                damageSource.q();
            }
            if (this.magic) {
                damageSource.setMagic();
            }
            if (this.explosion) {
                damageSource.d();
            }
            if (this.sweep) {
                damageSource.sweep();
            }
            if (this.thorns && damageSource instanceof EntityDamageSource) {
                ((EntityDamageSource)damageSource).x();
            }
            ReflectionUtil.setField(damageSource, "y", this.exhaustion);
            DAMAGE_SOURCE_MAP.put(damageSource, this);
            this.damageSource = damageSource;
            return damageSource;
        } catch (ReflectiveOperationException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public boolean buildAndDamage(Entity entity, float damage) {
        Optional<LivingEntity> livingEntity = Optional.of(entity).filter(LivingEntity.class::isInstance).map(ent -> ((LivingEntity)ent));
        AtomicInteger noDamageTicks = new AtomicInteger(-1);
        Optional<AttributeInstance> knockbackAttribute = livingEntity.map(ent -> {
            if (this.ignoreNoDamageTicks) {
                noDamageTicks.set(ent.getNoDamageTicks());
                ent.setNoDamageTicks(0);
            }
            return ent;
        }).filter(attribute -> this.preventKnockback).map(ent -> ent.getAttribute(Attribute.GENERIC_KNOCKBACK_RESISTANCE));
        knockbackAttribute.ifPresent(attribute -> attribute.addModifier(KNOCKBACK_MODIFIER));
        currentDamage = this;
        boolean result;
        if (this.damageSource == null) {
            this.build();
        }
        Block blockDamage = CraftEventFactory.blockDamage;
        net.minecraft.server.v1_12_R1.Entity entityDamage = CraftEventFactory.entityDamage;
        Location loc = entity.getLocation();
        if (this == LIGHTNING) {
            CraftEventFactory.entityDamage = ((CraftLightningStrike)NMSUtil.createEntity(entity.getWorld(), EntityType.LIGHTNING)).getHandle();
            CraftEventFactory.entityDamage.setPositionRotation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
        } else if (this == HOT_FLOOR || this == CACTUS) {
            CraftEventFactory.blockDamage = loc.getBlock();
        } else if (this == ANVIL || this == FALLING_BLOCK) {
            CraftEventFactory.entityDamage = new EntityFallingBlock(((CraftWorld)loc.getWorld()).getHandle(), loc.getX(), loc.getY(), loc.getZ(), (this == ANVIL ? Blocks.ANVIL : Blocks.SAND).fromLegacyData(0));
        }
        try {
            result = ((CraftEntity)entity).getHandle().damageEntity(this.damageSource, damage);
        } catch (IllegalStateException ex) {
            result = false;
            SkRage.getInstance().getLogger().warning(ex.getMessage());
        }
        CraftEventFactory.blockDamage = blockDamage;
        CraftEventFactory.entityDamage = entityDamage;
        currentDamage = null;
        livingEntity.ifPresent(ent -> Optional.of(noDamageTicks.get()).filter(i -> i > -1).ifPresent(ent::setNoDamageTicks));
        knockbackAttribute.ifPresent(attribute -> attribute.removeModifier(KNOCKBACK_MODIFIER));
        return result;
    }

    @Nullable
    public static DamageSourceBuilder getBuilder(DamageSource damageSource) {
        if (!DAMAGE_SOURCE_MAP.containsKey(damageSource)) {
            new DamageSourceBuilder(damageSource);
        }
        return DAMAGE_SOURCE_MAP.get(damageSource);
    }

    private void checkBuilt() {
        if (this.damageSource != null) {
            throw new IllegalStateException("既にこのインスタンスはビルドが完了しています");
        }
    }

}
