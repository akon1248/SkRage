package com.akon.skrage.skript.syntaxes.sections;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptEventHandler;
import ch.njol.skript.command.Commands;
import ch.njol.skript.command.ScriptCommand;
import ch.njol.skript.config.Node;
import ch.njol.skript.config.SectionNode;
import ch.njol.skript.lang.*;
import ch.njol.skript.lang.function.Function;
import ch.njol.skript.lang.function.Functions;
import ch.njol.skript.lang.function.ScriptFunction;
import ch.njol.skript.log.SkriptLogger;
import ch.njol.util.Kleenean;
import com.akon.skrage.SkRage;
import com.akon.skrage.utils.ReflectionUtil;
import com.akon.skrage.utils.exceptionsafe.ExceptionSafe;
import com.google.common.collect.Sets;
import lombok.AccessLevel;
import lombok.Getter;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.stream.Stream;

@Getter
public abstract class CustomSection extends Condition {

	private Trigger parentTrigger;
	private Conditional triggerSection;
	private SectionNode sectionNode;
	@Getter(AccessLevel.NONE)
	private final boolean error;

	public CustomSection() {
		Node node = SkriptLogger.getNode();
		if (node instanceof SectionNode) {
			this.sectionNode = (SectionNode)node;
			this.error = (StringUtils.startsWithIgnoreCase(node.getKey(), "if ") || StringUtils.startsWithIgnoreCase(node.getKey(), "else if "));
		} else {
			this.error = false;
		}
	}

	public static void register(Class<? extends CustomSection> clazz, String... patterns) {
		Skript.registerCondition(clazz, patterns);
	}

	@Override
	public final boolean check(Event e) {
		TriggerItem item = Optional.ofNullable(this.triggerSection).map(CustomSection::getFirst).orElse(this.getNext());
		return this.execute(e, item);
	}

	@Override
	public String toString(@Nullable Event e, boolean debug) {
		return "";
	}

	@Override
	public final boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
		if (this.error) {
			return false;
		}
		Bukkit.getScheduler().runTask(SkRage.getInstance(), () -> {
			if (this.triggerSection != null) {
				return;
			}
			setupCustomSections();
		});
		return this.initialize(exprs, matchedPattern, isDelayed, parseResult);
	}

	public abstract boolean initialize(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult);

	public abstract boolean execute(Event e, TriggerItem item);

	private static void setupCustomSections() {
		getAllTriggers().forEach(ExceptionSafe.<Trigger, ReflectiveOperationException>consumer(trigger -> {
			checkItem(trigger, getFirst(trigger), Sets.newHashSet());
		}).onCatch(ExceptionSafe.PRINT_STACK_TRACE));
	}

	protected static TriggerItem getFirst(TriggerSection section) {
		return (TriggerItem)ReflectionUtil.DEFAULT.getField(section, "first");
	}

	protected static TriggerItem getLast(TriggerSection section) {
		return (TriggerItem)ReflectionUtil.DEFAULT.getField(section, "last");
	}

	protected static Condition getCondition(Conditional conditional) {
		return (Condition)ReflectionUtil.DEFAULT.getField(conditional, "cond");
	}

	private static void checkItem(Trigger trigger, TriggerItem item, HashSet<TriggerItem> checkedItems) {
		if (item == null || checkedItems.contains(item)) {
			return;
		}
		checkedItems.add(item);
		if (item instanceof Conditional) {
			Condition condition = getCondition((Conditional)item);
			if (condition instanceof CustomSection) {
				((CustomSection)condition).parentTrigger = trigger;
				((CustomSection)condition).triggerSection = (Conditional)item;
			}
		}
		if (item instanceof TriggerSection) {
			checkItem(trigger, getFirst((TriggerSection)item), checkedItems);
		}
		if (item instanceof Loop) {
			checkItem(trigger, ((Loop)item).getActualNext(), checkedItems);
		} else if (item instanceof While) {
			checkItem(trigger, ((While)item).getActualNext(), checkedItems);
		}
		checkItem(trigger, item.getNext(), checkedItems);
	}

	private static Stream<Trigger> getAllTriggers() {
		return Stream.of(
			((Map<Class<? extends Event>, List<Trigger>>)ReflectionUtil.DEFAULT.getStaticField(SkriptEventHandler.class, "triggers"))
				.values()
				.stream()
				.flatMap(List::stream),
			((List<Trigger>)ReflectionUtil.DEFAULT.getStaticField(SkriptEventHandler.class, "selfRegisteredTriggers")).stream(),
			((Map<String, ScriptCommand>)ReflectionUtil.DEFAULT.getStaticField(Commands.class, "commands"))
				.values()
				.stream()
				.map(ExceptionSafe.function(command -> (Trigger)ReflectionUtil.DEFAULT.getField(command, "trigger"))
					.caught(ExceptionSafe.PRINT_STACK_TRACE)
					.andThen(optional -> optional.orElse(null))
				),
			((Map<String, ?>)ReflectionUtil.DEFAULT.getStaticField(Functions.class, "functions"))
				.values()
				.stream()
				.map(ExceptionSafe.function(functionData -> (Function<?>)ReflectionUtil.DEFAULT.getField(functionData, "function"))
					.caught(ExceptionSafe.PRINT_STACK_TRACE)
					.andThen(optional -> optional.orElse(null))
				)
				.filter(ScriptFunction.class::isInstance)
				.map(ExceptionSafe.function(function -> (Trigger)ReflectionUtil.DEFAULT.getField(function, "trigger"))
					.caught(ExceptionSafe.PRINT_STACK_TRACE)
					.andThen(optional -> optional.orElse(null))
				)
		).flatMap(stream -> stream).filter(Objects::nonNull);
	}

}
