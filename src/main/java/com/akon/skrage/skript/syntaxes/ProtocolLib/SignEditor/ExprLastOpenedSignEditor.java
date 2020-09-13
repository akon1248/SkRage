package com.akon.skrage.skript.syntaxes.ProtocolLib.SignEditor;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.akon.skrage.utils.signeditor.SignEditor;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

@Description("open sign editorの構文を使って最後に開かれたSignEditorを取得します")
public class ExprLastOpenedSignEditor extends SimpleExpression<SignEditor> {

    static {
        if (Bukkit.getPluginManager().isPluginEnabled("ProtocolLib")) {
            Skript.registerExpression(ExprLastOpenedSignEditor.class, SignEditor.class, ExpressionType.SIMPLE, "last opened sign[ ]editor");
        }
    }

    @Nullable
    @Override
    protected SignEditor[] get(Event e) {
        return Optional.ofNullable(EffOpenSignEditor.getLastOpen()).map(signEditor -> new SignEditor[]{signEditor}).orElse(null);
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends SignEditor> getReturnType() {
        return SignEditor.class;
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        return true;
    }
}
