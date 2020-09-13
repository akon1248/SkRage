package com.akon.skrage.skript.syntaxes.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

@Description({"プレイヤーのインベントリを更新します"})
public class EffUpdateInventory extends Effect {

    static {
        Skript.registerEffect(EffUpdateInventory.class, "update %player%'s inventory", "update inventory of %player%");
    }

    private Expression<Player> players;

    @Override
    protected void execute(Event e) {
        Optional.ofNullable(this.players).ifPresent(expr -> expr.getSingle(e).updateInventory());
    }

    @Override
    public String toString(@Nullable Event e, boolean debug) {
        return "";
    }

    @Override
    public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
        this.players = (Expression<Player>)exprs[0];
        return true;
    }
}
