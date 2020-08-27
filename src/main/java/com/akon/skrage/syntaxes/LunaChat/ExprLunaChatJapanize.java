package com.akon.skrage.syntaxes.LunaChat;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.github.ucchyocean.lc.LunaChat;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;

@Description({"LunaChatのAPIを使用して文字列をローマ字変換します"})
public class ExprLunaChatJapanize extends SimpleExpression<String> {

    private Expression<String> string;

    static {
        if (Bukkit.getPluginManager().isPluginEnabled("LunaChat")) Skript.registerExpression(ExprLunaChatJapanize.class, String.class, ExpressionType.COMBINED, "[(lunachat|lc)] japanize %string%");
    }

    @Override
    protected String[] get(Event e) {
        if (this.string != null) {
            return new String[]{this.string.getSingle(e).length() == this.string.getSingle(e).getBytes().length ? LunaChat.getInstance().getLunaChatAPI().japanize(this.string.getSingle(e), LunaChat.getInstance().getLunaChatConfig().getJapanizeType()) : this.string.getSingle(e)};
        } else {
            return null;
        }
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends String> getReturnType() {
        return String.class;
    }

    @Override
    public String toString(Event e, boolean debug) {
        return "";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        this.string = (Expression<String>)exprs[0];
        return true;
    }
}
