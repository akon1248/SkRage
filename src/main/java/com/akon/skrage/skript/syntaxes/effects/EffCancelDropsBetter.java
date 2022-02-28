package com.akon.skrage.skript.syntaxes.effects;

import ch.njol.skript.ScriptLoader;
import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.expressions.ExprDrops;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.akon.skrage.utils.TypeMatcher;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

@Description({"アイテムドロップをキャンセルします"})
public class EffCancelDropsBetter extends Effect {

    static {
        Skript.registerExpression(ExprDrops.class, ItemStack.class, ExpressionType.SIMPLE, "(cancel|clear|delete) drops better");
    }

    @Override
    protected void execute(Event event) {
        TypeMatcher.matching(event)
                .match(EntityDeathEvent.class, e -> e.getDrops().clear())
                .match(BlockBreakEvent.class, e -> e.setDropItems(false))
                .def(() -> {});
    }

    @Override
    public String toString(@Nullable Event event, boolean b) {
        return "";
    }

    @Override
    public boolean init(Expression<?>[] expressions, int matched, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        if (!ScriptLoader.isCurrentEvent(EntityDeathEvent.class) && !ScriptLoader.isCurrentEvent(BlockBreakEvent.class)) {
            Skript.error("on breakまたはon deathイベントでのみこの構文は使用可能です");
            return false;
        }
        return true;
    }
}
