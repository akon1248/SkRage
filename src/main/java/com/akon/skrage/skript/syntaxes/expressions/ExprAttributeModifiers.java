package com.akon.skrage.skript.syntaxes.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.doc.Description;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Optional;

@Description({"AttributeInstanceに追加されているAttributeModifier"})
public class ExprAttributeModifiers extends SimpleExpression<AttributeModifier> {

	static {
		Skript.registerExpression(ExprAttributeModifiers.class, AttributeModifier.class, ExpressionType.COMBINED, "attribute[ ]modifier[s] of %attributeinstance%", "%attributeinstance%'s attribute[ ]modifier[s]");
	}

	private Expression<AttributeInstance> attribute;

	@Nullable
	@Override
	protected AttributeModifier[] get(Event e) {
		if (this.attribute != null) {
			return attribute.getSingle(e).getModifiers().toArray(new AttributeModifier[0]);
		}
		return null;
	}

	@Override
	public boolean isSingle() {
		return false;
	}

	@Override
	public Class<? extends AttributeModifier> getReturnType() {
		return AttributeModifier.class;
	}

	@Override
	public String toString(@Nullable Event e, boolean debug) {
		return "";
	}

	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
		this.attribute = (Expression<AttributeInstance>)exprs[0];
		return true;
	}

	@Nullable
	@Override
	public Class<?>[] acceptChange(Changer.ChangeMode mode) {
		switch (mode) {
			case ADD:
			case SET:
			case REMOVE:
			case REMOVE_ALL:
			case DELETE:
				return CollectionUtils.array(AttributeModifier.class);
		}
		return null;
	}

	@Override
	public void change(Event e, @Nullable Object[] delta, Changer.ChangeMode mode) {
		Optional.ofNullable(this.attribute).map(expr -> expr.getSingle(e)).ifPresent(attr -> {
			switch (mode) {
				case SET:
					attr.getModifiers().forEach(attr::removeModifier);
				case ADD:
					Optional.ofNullable(delta).ifPresent(arr -> Arrays.stream(arr).map(AttributeModifier.class::cast).forEach(attr::addModifier));
					break;
				case REMOVE:
				case REMOVE_ALL:
					Optional.ofNullable(delta).ifPresent(arr -> Arrays.stream(arr).map(AttributeModifier.class::cast).forEach(attr::removeModifier));
					break;
				case DELETE:
					attr.getModifiers().forEach(attr::removeModifier);
			}
		});
	}
}
