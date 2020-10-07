package com.akon.skrage.skript.syntaxes.events;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.registrations.EventValues;
import ch.njol.skript.util.Getter;
import com.akon.skrage.utils.customstatuseffect.*;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

public class EvtCustomStatusEffect extends SkriptEvent {

	static {
		Skript.registerEvent("cse apply", EvtCustomStatusEffect.class, CSEApplyEvent.class, "(cse|custom[ ]status[ ]effect) apply %string%").description("CustomStatusEffectが付与されたとき");
		Skript.registerEvent("cse remove", EvtCustomStatusEffect.class, CSERemoveEvent.class, "(cse|custom[ ]status[ ]effect) remove %string%").description("CustomStatusEffectの効果が切れたとき");
		Skript.registerEvent("cse tick", EvtCustomStatusEffect.class, CSETickEvent.class, "(cse|custom[ ]status[ ]effect) tick %string%");
		EventValues.registerEventValue(CSEEvent.class, CustomStatusEffect.class, new Getter<CustomStatusEffect, CSEEvent>() {

			@Nullable
			@Override
			public CustomStatusEffect get(CSEEvent arg) {
				return arg.getEffect();
			}

		}, 0);
	}

	private Literal<String> type;

	@Override
	public boolean init(Literal<?>[] args, int matchedPattern, SkriptParser.ParseResult parseResult) {
		this.type = (Literal<String>)args[0];
		return true;
	}

	@Override
	public boolean check(Event e) {
		return e instanceof CSEEvent && ((CSEEvent)e).getEffect().getType().getId().equals(this.type.getSingle(e));
	}

	@Override
	public String toString(@Nullable Event e, boolean debug) {
		return "";
	}
}
