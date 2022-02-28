package com.akon.skrage.skript.syntaxes.Factions;

import com.akon.skrage.skript.syntaxes.ExpressionFactory;
import com.massivecraft.factions.entity.MPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class FactionsSimpleExpressions {

    static {
        if (Bukkit.getPluginManager().isPluginEnabled("Factions")) {
            ExpressionFactory.registerExpression("ExprPowerOfPlayer", "[faction] power", 3, Player.class, Number.class, player -> MPlayer.get(player).getPower(), (player, power) -> MPlayer.get(player).setPower(power.doubleValue()), "プレイヤーのpowerを取得します");
            ExpressionFactory.registerExpression("ExprMaxPowerOfPlayer", "[faction] max power", 3, Player.class, Number.class, player -> MPlayer.get(player).getPowerMax(), (player, power) -> MPlayer.get(player).setPower(power.doubleValue()), "プレイヤーの最大powerを取得します");
            ExpressionFactory.registerExpression("ExprBoostedPowerOfPlayer", "[faction] boosted power", 3, Player.class, Number.class, player -> MPlayer.get(player).getPowerBoost(), (player, power) -> MPlayer.get(player).setPower(power.doubleValue()), "プレイヤーのブーストpowerを取得します");
        }
    }

}
