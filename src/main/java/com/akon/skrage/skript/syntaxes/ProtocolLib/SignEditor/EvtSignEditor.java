package com.akon.skrage.skript.syntaxes.ProtocolLib.SignEditor;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import com.akon.skrage.utils.signeditor.SignEditor;
import com.akon.skrage.utils.signeditor.SignEditorDoneEvent;
import com.akon.skrage.utils.signeditor.SignEditorEvent;
import com.akon.skrage.utils.signeditor.SignEditorOpenEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

public class EvtSignEditor extends SkriptEvent {

    static {
        if (Bukkit.getPluginManager().isPluginEnabled("ProtocolLib")) {
            Skript.registerEvent("sign editor open", EvtSignEditor.class, SignEditorOpenEvent.class, "sign[ ]editor open").description("SignEditorが開かれたとき(通常の看板では発動しません)");
            Skript.registerEvent("sign editor done", EvtSignEditor.class, SignEditorDoneEvent.class, "sign[ ]editor done").description("プレイヤーがSignEditorでの編集を終えた時(通常の看板では発動しません)");
            EventValues.registerEventValue(SignEditorEvent.class, SignEditor.class, new Getter<SignEditor, SignEditorEvent>() {

                @Nullable
                @Override
                public SignEditor get(SignEditorEvent arg) {
                    return arg.getSignEditor();
                }

            }, 0);
        }
    }

    @Override
    public boolean init(Literal<?>[] args, int matchedPattern, SkriptParser.ParseResult parseResult) {
        return true;
    }

    @Override
    public boolean check(Event e) {
        return e instanceof SignEditorEvent;
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "";
    }
}
