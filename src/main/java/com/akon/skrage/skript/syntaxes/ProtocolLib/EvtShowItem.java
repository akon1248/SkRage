package com.akon.skrage.skript.syntaxes.ProtocolLib;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser;
import com.akon.skrage.event.ItemStackShowEvent;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

public class EvtShowItem extends SkriptEvent {

    static {
        Skript.registerEvent("show item", EvtShowItem.class, ItemStackShowEvent.class, "(sen(d|t)|show) item[stack]")
            .description("アイテムがプレイヤーに表示されるとき")
            .examples(
                "on show item:",
                "    set {_nbt} to nbt compound of shown item",
                "    {_nbt} is set",
                "    set {_nbtlist} to tag key \"AttributeModifiers\" of {_nbt}",
                "    loop convert nbt list {_nbtlist} to list:",
                "        set {_modifiernbt} to loop-value",
                "        tag key \"AttributeName\" of {_modifiernbt} is \"generic.attackDamage\"",
                "        tag key \"Slot\" of {_modifiernbt} is \"mainhand\"",
                "        tag key \"Operation\" of {_modifiernbt} is 0",
                "        set {_amount} to tag key \"Amount\" of {_modifiernbt}",
                "        add {_amount} to {_sum}",
                "    {_sum} is set",
                "    set {_item} to shown item",
                "    set tag key \"AttributeModifiers\" of nbt compound of {_item} to nbt literal []",
                "    set tag key \"TrueNBT\" of nbt compound of {_item} to {_nbt}",
                "    set lore of {_item} to \"\" and \"&c攻撃力: &6%{_sum}%\"",
                "    set shown item to {_item}",
                "on receive creative item:",
                "    set {_nbt} to nbt compound of received item",
                "    set {_truenbt} to tag key \"TrueNBT\" of {_nbt}",
                "    {_truenbt} is set",
                "    set nbt compound of received item to {_truenbt}"
            );
    }

    @Override
    public boolean init(Literal<?>[] args, int matchedPattern, SkriptParser.ParseResult parseResult) {
        return true;
    }

    @Override
    public boolean check(Event e) {
        return e instanceof ItemStackShowEvent;
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "";
    }
}
