package com.akon.skrage.mmextension;

import ch.njol.skript.lang.function.Function;
import ch.njol.skript.lang.function.Functions;
import io.lumine.xikage.mythicmobs.io.MythicLineConfig;
import io.lumine.xikage.mythicmobs.skills.INoTargetSkill;
import io.lumine.xikage.mythicmobs.skills.SkillMechanic;
import io.lumine.xikage.mythicmobs.skills.SkillMetadata;
import org.bukkit.entity.Entity;

public class SkriptFunctionMechanics extends SkillMechanic implements INoTargetSkill {

    private final String funcName;

    public SkriptFunctionMechanics(String skill, MythicLineConfig mlc) {
        super(skill, mlc);
        this.funcName = mlc.getString(new String[]{"f", "func", "function"});
    }

    @Override
    public boolean cast(SkillMetadata skillMetadata) {
        if (this.funcName != null) {
            Function<?> function = Functions.getFunction(this.funcName);
            Class<?> clazz;
            Entity entity;
            if (function != null && function.getParameters().length <= 1 && Entity.class.isAssignableFrom((clazz = function.getParameter(0).getType().getC())) && clazz.isInstance(entity = skillMetadata.getCaster().getEntity().getBukkitEntity())) {
                function.execute(new Object[][]{new Object[]{entity}});
            }
        }
        return false;
    }
}
