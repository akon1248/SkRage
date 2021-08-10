package com.akon.skrage.skript.syntaxes.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.util.Color;
import ch.njol.util.Kleenean;
import com.akon.skrage.utils.customstatuseffect.CSEManager;
import com.akon.skrage.utils.customstatuseffect.CSEType;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

@Description({"CustomStatusEffectを登録/削除します"})
public class EffRegisterCSE extends Effect {

	static {
		Skript.registerEffect(EffRegisterCSE.class, "register (cse|custom[ ]status[ ]effect) type with id %string% [and] name %string% [and] color (%-color%|%-number%[, %-number%, %-number%])", "unregister (cse|custom[ ]status[ ]effect) type [id] %string%");
	}

	private Expression<String> id;
	private Expression<String> name;
	private Expression<Color> color;
	private Expression<Number> red;
	private Expression<Number> green;
	private Expression<Number> blue;
	private boolean unregister;

	@Override
	protected void execute(Event e) {
		Optional.ofNullable(this.id).map(expr -> expr.getSingle(e)).ifPresent(id -> {
			if (this.unregister) {
				CSEManager.unregister(id);
			} else {
				if (this.name != null) {
					if (CSEManager.get(id) != null) {
						CSEManager.unregister(id);
					}
					org.bukkit.Color bukkitColor = Optional.ofNullable(this.color).map(expr -> expr.getSingle(e)).map(Color::getBukkitColor).orElseGet(() -> this.green == null ? org.bukkit.Color.fromRGB(this.red.getSingle(e).intValue()) : org.bukkit.Color.fromRGB(this.red.getSingle(e).intValue(), this.green.getSingle(e).intValue(), this.blue.getSingle(e).intValue()));
					CSEManager.register(new CSEType(id, this.name.getSingle(e), bukkitColor));
				}
			}
		});
	}

	@Override
	public String toString(@Nullable Event e, boolean debug) {
		return "";
	}

	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
		this.unregister = matchedPattern == 1;
		this.id = (Expression<String>)exprs[0];
		if (!this.unregister) {
			this.name = (Expression<String>)exprs[1];
			if (exprs[2] != null) {
				this.color = (Expression<Color>)exprs[2];
			} else {
				this.red = (Expression<Number>)exprs[3];
				this.green = (Expression<Number>) exprs[4];
				this.blue = (Expression<Number>) exprs[5];
			}
		}
		return true;
	}
}
