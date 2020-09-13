package com.akon.skrage.skript.syntaxes.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_12_R1.CraftWorld;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.Event;

@Description({"エンティティを作成しますがスポーンはしません","一部のエンティティを除いてほぼすべてのエンティティを作成できます","スポーンさせるためにはSkRageの別の構文を用いてください"})
public class ExprNewEntity extends SimpleExpression<Entity> {

	static {
		Skript.registerExpression(ExprNewEntity.class, Entity.class, ExpressionType.COMBINED, "new entity %entitytype%");
	}

	private Expression<EntityType> type;

	@Override
	protected Entity[] get(Event e) {
		if (this.type != null) {
			World world = Bukkit.getWorlds().get(0);
			return new Entity[]{((CraftWorld)world).createEntity(new Location(world, 0, 0, 0), this.type.getSingle(e).getEntityClass()).getBukkitEntity()};
		}
		return null;
	}

	@Override
	public boolean isSingle() {
		return true;
	}

	@Override
	public Class<? extends Entity> getReturnType() {
		return Entity.class;
	}

	@Override
	public String toString(Event e, boolean debug) {
		return "";
	}

	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
		this.type = (Expression<EntityType>)exprs[0];
		return true;
	}

}
