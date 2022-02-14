package com.akon.skrage.skript.syntaxes.ProtocolLib.Skin;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import com.akon.skrage.utils.skin.Skin;
import org.bukkit.event.Event;

@Description({"valueとsignatureから新しいスキンを作成します"})
public class ExprNewSkin extends SimpleExpression<Skin> {

	static {
		Skript.registerExpression(ExprNewSkin.class, Skin.class, ExpressionType.COMBINED, "new [player] skin [with] value %string% signature %string%");
	}

	private Expression<String> value;
	private Expression<String> signature;

	@Override
	protected Skin[] get(Event e) {
		if (this.value != null && this.signature != null) {
			return new Skin[]{new Skin(this.value.getSingle(e), this.signature.getSingle(e))};
		}
		return null;
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
	public String toString(Event e, boolean debug) {
		return "";
	}

	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
		this.value = (Expression<String>)exprs[0];
		this.signature = (Expression<String>)exprs[1];
		return true;
	}

}
