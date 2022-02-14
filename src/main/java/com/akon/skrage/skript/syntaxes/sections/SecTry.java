package com.akon.skrage.skript.syntaxes.sections;

import ch.njol.skript.lang.Conditional;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.TriggerItem;
import ch.njol.util.Kleenean;
import com.akon.skrage.utils.ReflectionUtil;
import org.bukkit.event.Event;

import java.lang.reflect.InvocationTargetException;
import java.util.Optional;

public class SecTry extends CustomSection {

	static {
		CustomSection.register(SecTry.class, "try");
	}

	@Override
	public boolean initialize(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
		return true;
	}

	@Override
	public boolean execute(Event e, TriggerItem item) {
		if (this.getTriggerSection() != null) {
			SecCatch catchSection = (SecCatch)Optional.ofNullable(this.getTriggerSection().getNext())
				.filter(Conditional.class::isInstance)
				.map(Conditional.class::cast)
				.map(CustomSection::getCondition)
				.filter(SecCatch.class::isInstance)
				.orElse(null);
			try {
				while (item != null)
					item = (TriggerItem)ReflectionUtil.invokeMethod(TriggerItem.class, item, "walk", new Class[]{Event.class}, new Object[]{e});
			} catch (InvocationTargetException err) {
				if (catchSection != null && err.getCause() != null) {
					catchSection.caught(e, err.getCause());
				}
			} catch (NoSuchMethodException | IllegalAccessException ex) {
				ex.printStackTrace();
			}
		}
		return false;
	}
}
