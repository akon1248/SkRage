package com.akon.skrage.syntaxes.LunaChat;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import com.github.ucchyocean.lc.LunaChat;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

@Description({"LunaChatのAPIを使用してプレイヤーのローマ字変換が有効かどうかを取得します"})
public class ExprLunaChatJapanizeMode extends SimpleExpression<Boolean> {

    private Expression<Player> player;

    static {
        if (Bukkit.getPluginManager().isPluginEnabled("LunaChat")) Skript.registerExpression(ExprLunaChatJapanizeMode.class, Boolean.class, ExpressionType.COMBINED, "[(lunachat|lc)] %player%'s japanize [mode]", "[(lunachat|lc)] japanize [mode] of %player%");
    }

    @Override
    protected Boolean[] get(Event e) {
        if (this.player != null) {
            return new Boolean[]{LunaChat.getInstance().getLunaChatAPI().isPlayerJapanize(player.getSingle(e).getName())};
        } else {
            return null;
        }
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends Boolean> getReturnType() {
        return Boolean.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        this.player = (Expression<Player>)exprs[0];
        return true;
    }

    @Override
    public void change(Event e, Object[] delta, Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.SET) {
            LunaChat.getInstance().getLunaChatAPI().setPlayersJapanize(this.player.getSingle(e).getName(), (Boolean)delta[0]);
        }
    }

    @Override
    public Class<?>[] acceptChange(Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.SET) {
            return CollectionUtils.array(Boolean.class);
        }
        return null;
    }
}
