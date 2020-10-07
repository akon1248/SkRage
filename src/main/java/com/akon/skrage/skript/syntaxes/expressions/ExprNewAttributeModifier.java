package com.akon.skrage.skript.syntaxes.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

@Description({"新しくAttributeModifierを作成します"})
public class ExprNewAttributeModifier extends SimpleExpression<AttributeModifier> {

	static {
		Skript.registerExpression(ExprNewAttributeModifier.class, AttributeModifier.class, ExpressionType.COMBINED, "new attribute[ ]modifier with uuid %string% [and] name %string% [and] amount %number% [and] operation (0¦add number|1¦add scalar|2¦multiply scalar one)");
	}

	private Expression<String> uuid;
	private Expression<String> name;
	private Expression<Number> amount;
	private AttributeModifier.Operation operation;

	@Nullable
	@Override
	protected AttributeModifier[] get(Event e) {
		if (this.name != null && this.amount != null) {
			UUID uuid;
			if (this.uuid != null) {
				try {
					String str = this.uuid.getSingle(e);
					if (str == null) {
						return null;
					}
					uuid = UUID.fromString(str);
				} catch (IllegalArgumentException ex) {
					return null;
				}
			} else {
				uuid = UUID.randomUUID();
			}
			return new AttributeModifier[]{new AttributeModifier(uuid, this.name.getSingle(e), this.amount.getSingle(e).doubleValue(), this.operation)};
		}
		return null;
	}

	@Override
	public boolean isSingle() {
		return true;
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
		this.uuid = (Expression<String>)exprs[0];
		this.name = (Expression<String>)exprs[1];
		this.amount = (Expression<Number>)exprs[2];
		this.operation = AttributeModifier.Operation.values()[parseResult.mark];
		return true;
	}
}
