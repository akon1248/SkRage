package com.akon.skrage.skript.syntaxes.ProtocolLib.SignEditor;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.akon.skrage.utils.signeditor.SignEditorDoneEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

@Description({"sign editor doneイベントでプレイヤーが入力した文字列を取得します"})
public class ExprSignEditorLine extends SimpleExpression<String> {

    static {
        Skript.registerExpression(ExprSignEditorLine.class, String.class, ExpressionType.COMBINED, "sign[ ]editor (line|text) %number%");
    }

    private Expression<Number> line;

    @Nullable
    @Override
    protected String[] get(Event e) {
        if (this.line != null) {
            int lineNum = this.line.getSingle(e).intValue();
            if (lineNum <= 4 && lineNum >= 1) {
                return new String[]{((SignEditorDoneEvent)e).getLine(lineNum-1)};
            }
        }
        return null;
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
    public String toString(@Nullable Event e, boolean debug) {
        return "";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        if (!ScriptLoader.isCurrentEvent(SignEditorDoneEvent.class)) {
            Skript.error("この構文はsign editor doneイベントでのみ使用可能です");
            return false;
        }
        this.line = (Expression<Number>)exprs[0];
        return true;
    }
}
