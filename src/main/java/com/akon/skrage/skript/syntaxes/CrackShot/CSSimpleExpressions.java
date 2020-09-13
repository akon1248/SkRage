package com.akon.skrage.skript.syntaxes.CrackShot;

import com.akon.skrage.skript.syntaxes.ExpressionFactory;
import com.shampaggon.crackshot.CSDirector;
import com.shampaggon.crackshot.events.WeaponDamageEntityEvent;
import com.shampaggon.crackshot.events.WeaponReloadEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.TNTPrimed;

public class CSSimpleExpressions {

    static {
        if (Bukkit.getPluginManager().isPluginEnabled("CrackShot")) {
            ExpressionFactory.registerExpression("ExprBulletWeaponName", "[(crackshot|cs)] weapon [internal[ ]]name", 0b111, Entity.class, String.class, entity -> {
                if (entity instanceof Projectile && entity.hasMetadata("projParentNode")) {
                    return entity.getMetadata("projParentNode").get(0).asString();
                } else if (entity instanceof TNTPrimed && entity.hasMetadata("CS_Potex")) {
                    return entity.getMetadata("CS_Potex").get(0).asString();
                }
                return null;
            }, null, "CrackShotの弾丸もしくは爆弾から武器の登録名を取得します");
            ExpressionFactory.registerExpression("ExprCSDamage", "(crackshot|cs) [weapon] damage", 0, WeaponDamageEntityEvent.class, Number.class, WeaponDamageEntityEvent::getDamage, (event, number) -> event.setDamage(number.doubleValue()), "CrackShot DamageイベントでEntityの受けた防具等によって軽減される前のダメージ");
            ExpressionFactory.registerExpression("ExprCSDamager", "(crackshot|cs) (attacker|damager)", 0, WeaponDamageEntityEvent.class, Entity.class, WeaponDamageEntityEvent::getDamager, null, "CrackShot Damageイベントでエンティティにダメージを与えたエンティティ");
            ExpressionFactory.registerExpression("ExprCSWeaponDisplayName", "weapon display[ ]name", 0b110, String.class, String.class, str -> ((CSDirector)Bukkit.getServer().getPluginManager().getPlugin("CrackShot")).getString(str + ".Item_Information.Item_Name"), null, "CrackShotの武器の表示名");
            ExpressionFactory.registerExpression("ExprReloadDuration", "reload duration", 0, WeaponReloadEvent.class, Number.class, WeaponReloadEvent::getReloadDuration, (event, number) -> event.setReloadDuration(number.intValue()), "CrackShot Reload Startイベントでの武器のリロード時間");
        }
    }

}
