package com.akon.skrage.skript.syntaxes.sections;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.TriggerItem;
import ch.njol.skript.util.Timespan;
import ch.njol.util.Kleenean;
import com.akon.skrage.SkRage;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;

import java.util.Optional;

public class SecRunLater extends CustomSection {

	static {
		CustomSection.register(SecRunLater.class, "(execute|run) (1¦async|) [(code|section)] in %timespan%");
	}

	private Expression<Timespan> delay;
	private boolean async;

	@Override
	public boolean initialize(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
		this.delay = (Expression<Timespan>)exprs[0];
		this.async = parseResult.mark == 1;
		return true;
	}

	@Override
	public boolean execute(Event e, TriggerItem item) {
		Optional.ofNullable(this.getTriggerSection()).map(CustomSection::getLast).ifPresent(i -> i.setNext(null));
		long ticks = Optional.ofNullable(this.delay).map(expr -> expr.getSingle(e)).map(Timespan::getTicks_i).orElse(0L);
		if (this.async) {
			Bukkit.getScheduler().runTaskLaterAsynchronously(SkRage.getInstance(), () -> TriggerItem.walk(item, e), ticks);
		} else {
			Bukkit.getScheduler().runTaskLater(SkRage.getInstance(), () -> TriggerItem.walk(item, e), ticks);
		}
		return false;
	}
}
