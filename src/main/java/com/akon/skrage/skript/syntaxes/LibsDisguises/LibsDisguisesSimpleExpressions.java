package com.akon.skrage.skript.syntaxes.LibsDisguises;

import com.akon.skrage.skript.syntaxes.ExpressionFactory;
import me.libraryaddict.disguise.disguisetypes.Disguise;
import me.libraryaddict.disguise.disguisetypes.watchers.SlimeWatcher;
import org.bukkit.Bukkit;

import java.util.Optional;

public class LibsDisguisesSimpleExpressions {

    static {
        if (Bukkit.getPluginManager().isPluginEnabled("LibsDisguises")) {
            ExpressionFactory.registerExpression("ExprDisguiseSlimeSize", "[slime] disguise size", 0b011, Disguise.class, Number.class, disguise -> Optional.of(disguise.getWatcher()).filter(SlimeWatcher.class::isInstance).map(watcher -> ((SlimeWatcher) watcher).getSize()).orElse(0), (disguise, number) -> Optional.of(disguise.getWatcher()).filter(SlimeWatcher.class::isInstance).ifPresent(watcher -> ((SlimeWatcher) watcher).setSize(number.intValue())), "スライムのDisguiseのサイズ");
        }
    }

}
