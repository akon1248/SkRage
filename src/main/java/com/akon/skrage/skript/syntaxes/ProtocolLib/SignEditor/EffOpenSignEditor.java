package com.akon.skrage.skript.syntaxes.ProtocolLib.SignEditor;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.akon.skrage.utils.signeditor.SignEditor;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

@Description({"プレイヤーに看板のSignEditorを開かせます"})
public class EffOpenSignEditor extends Effect {

    static {
        if (Bukkit.getPluginManager().isPluginEnabled("ProtocolLib")) {
            Skript.registerEffect(EffOpenSignEditor.class, "open sign[ ]editor named %string% with (line|text) 1 %string% (line|text) 2 %string% (line|text) 3 %string% (line|text) 4 %string% (to|for) %player%");
        }
    }

    @Getter
    private static SignEditor lastOpen;

    private Expression<String> name;
    private Expression<String> line1;
    private Expression<String> line2;
    private Expression<String> line3;
    private Expression<String> line4;
    private Expression<Player> player;

    @Override
    protected void execute(Event e) {
        if (this.name != null && this.player != null) {
            SignEditor signEditor = new SignEditor(this.name.getSingle(e), this.getString(this.line1, e), this.getString(this.line2, e), this.getString(this.line3, e), this.getString(this.line4, e));
            if (signEditor.open(this.player.getSingle(e))) {
                lastOpen = signEditor;
            }
        }
    }

    private String getString(Expression<String> strExpr, Event e) {
        return Optional.ofNullable(strExpr).map(expr -> expr.getSingle(e)).orElse(null);
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        this.name = (Expression<String>)exprs[0];
        this.line1 = (Expression<String>)exprs[1];
        this.line2 = (Expression<String>)exprs[2];
        this.line3 = (Expression<String>)exprs[3];
        this.line4 = (Expression<String>)exprs[4];
        this.player = (Expression<Player>)exprs[5];
        return true;
    }
}
