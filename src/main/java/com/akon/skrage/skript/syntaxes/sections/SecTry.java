package com.akon.skrage.skript.syntaxes.sections;

import ch.njol.skript.lang.Conditional;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.TriggerItem;
import ch.njol.util.Kleenean;
import com.akon.skrage.utils.ReflectionUtil;
import com.akon.skrage.utils.UncheckedReflectiveOperationException;
import org.bukkit.event.Event;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

public class SecTry extends CustomSection {

	static {
		CustomSection.register(SecTry.class, "try");
	}

	private SecCatch catchSection;

	@Override
	public boolean initialize(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
		return true;
	}

	@Override
	public boolean execute(Event event, TriggerItem item) {
		if (this.getTriggerSection() == null) {
			return false;
		}
		if (this.catchSection == null) {
			this.catchSection = (SecCatch)Optional.ofNullable(this.getTriggerSection().getNext())
				.filter(Conditional.class::isInstance)
				.map(Conditional.class::cast)
				.map(CustomSection::getCondition)
				.filter(SecCatch.class::isInstance)
				.orElse(null);
		}
		try {
			while (item != null)
				item = (TriggerItem)ReflectionUtil.DEFAULT.invokeMethod(TriggerItem.class, item, "walk", new Class[]{Event.class}, new Object[]{event});
		} catch (UncheckedReflectiveOperationException e) {
			ReflectiveOperationException ex = e.getCause();
			if (ex instanceof InvocationTargetException) {
				if (this.catchSection != null && ex.getCause() != null) {
					this.catchSection.onCatch(event, ex.getCause());
				}
			}
		}
		return false;
	}
}
