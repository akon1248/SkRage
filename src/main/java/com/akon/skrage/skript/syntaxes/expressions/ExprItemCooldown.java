package com.akon.skrage.skript.syntaxes.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.aliases.ItemType;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;

@Description({"アイテムのエンダーパールのようなクールダウンを設定します"})
public class ExprItemCooldown extends SimpleExpression<Number> {

	static {
		Skript.registerExpression(ExprItemCooldown.class, Number.class, ExpressionType.COMBINED, "cool[]down of %itemtype% of %player%");
	}

	private Expression<ItemType> item;
	private Expression<Player> player;

	@Override
	protected Number[] get(Event e) {
		if (this.item != null && this.player != null) {
			return new Number[]{this.player.getSingle(e).getCooldown(this.item.getSingle(e).getRandom().getType())};
		}
		return null;
	}

	@Override
	public boolean isSingle() {
		return true;
	}

	@Override
	public Class<? extends Number> getReturnType() {
		return Number.class;
	}

	@Override
	public String toString(Event e, boolean debug) {
		return "";
	}

	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
		this.item = (Expression<ItemType>)exprs[0];
		this.player = (Expression<Player>)exprs[1];
		return true;
	}

	@Override
	public Class<?>[] acceptChange(Changer.ChangeMode mode) {
		if (mode == Changer.ChangeMode.ADD || mode == Changer.ChangeMode.DELETE || mode == Changer.ChangeMode.REMOVE || mode == Changer.ChangeMode.RESET || mode == Changer.ChangeMode.SET) {
			return CollectionUtils.array(Number.class);
		}
		return null;
	}

	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode) {
		if (this.item != null && this.player != null) {
			Material material = this.item.getSingle(e).getRandom().getType();
			Player player = this.player.getSingle(e);
			int i = ((Number) delta[0]).intValue();
			switch (mode) {
				case ADD:
					player.setCooldown(material, Math.max(0, player.getCooldown(material) + i));
					return;
				case REMOVE:
					player.setCooldown(material, Math.max(0, player.getCooldown(material) - i));
					return;
				case SET:
					player.setCooldown(material, Math.max(0, i));
					return;
				case DELETE:
				case RESET:
					player.setCooldown(material, 0);
			}
		}
	}

}
