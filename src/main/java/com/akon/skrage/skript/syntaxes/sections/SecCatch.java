package com.akon.skrage.skript.syntaxes.sections;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.TriggerItem;
import ch.njol.skript.lang.Variable;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;

import java.util.Optional;

public class SecCatch extends CustomSection {

	static {
		CustomSection.register(SecCatch.class, "catch in %object%");
	}

	private Variable<?> var;

	@Override
	public boolean initialize(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
		if (exprs[0] instanceof Variable && ((Variable<?>)exprs[0]).isLocal()) {
			this.var = (Variable<?>)exprs[0];
			return true;
		}
		Skript.error("ローカル変数のみ利用可能です");
		return false;
	}

	@Override
	public boolean execute(Event e, TriggerItem item) {
		return false;
	}

	public void onCatch(Event e, Throwable throwable) {
		Optional.ofNullable(this.getTriggerSection()).map(CustomSection::getFirst).ifPresent(item -> {
			this.var.change(e, new Object[]{throwable}, Changer.ChangeMode.SET);
			TriggerItem.walk(item, e);
		});
	}
}
