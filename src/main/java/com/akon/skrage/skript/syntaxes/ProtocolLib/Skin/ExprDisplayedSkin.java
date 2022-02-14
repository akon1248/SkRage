package com.akon.skrage.skript.syntaxes.ProtocolLib.Skin;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import com.akon.skrage.utils.skin.Skin;
import com.akon.skrage.utils.skin.SkinManager;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

@Description({"プレイヤーに表示されているスキン"})
public class ExprDisplayedSkin extends SimpleExpression<Skin> {

    private Expression<Player> player;

    static {
        Skript.registerExpression(ExprDisplayedSkin.class, Skin.class, ExpressionType.COMBINED, "%player%'s displayed [player] skin", "displayed [player] skin of %player%");
    }

    @Override
    protected Skin[] get(Event event) {
        return new Skin[]{this.player == null ? null : SkinManager.getDisplayedSkin(this.player.getSingle(event))};
    }

    @Override
    public boolean isSingle() {
        return true;
    }

    @Override
    public Class<? extends Skin> getReturnType() {
        return Skin.class;
    }

    @Override
    public String toString(Event event, boolean b) {
        return "";
    }

    @Override
    public boolean init(Expression<?>[] expression, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        this.player = (Expression<Player>) expression[0];
        return true;
    }

    @Override
    public void change(Event event, Object[] delta, Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.SET) {
            if (delta[0] instanceof Skin) {
                SkinManager.changeSkin(this.player.getSingle(event), (Skin)delta[0]);
            }
        } else if (mode == Changer.ChangeMode.RESET) {
            SkinManager.resetSkin(this.player.getSingle(event));
        }
    }

    @Override
    public Class<?>[] acceptChange(Changer.ChangeMode mode) {
        if (mode == Changer.ChangeMode.SET || mode == Changer.ChangeMode.RESET) {
            return CollectionUtils.array(Skin.class);
        }
        return null;
    }
}
