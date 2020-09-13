package com.akon.skrage.skript.syntaxes.expressions;

import ch.njol.skript.Skript;
import ch.njol.skript.classes.Changer;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.ExpressionType;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import ch.njol.util.coll.CollectionUtils;
import com.akon.skrage.utils.NMSUtil;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Wither;
import org.bukkit.event.Event;

public class ExprWitherHeadTarget extends SimpleExpression<Entity> {

	static {
		Skript.registerExpression(ExprWitherHeadTarget.class, Entity.class, ExpressionType.COMBINED, "(0¦right|1¦left) head target of %livingentity%", "%livingentity%'s (0¦right|1¦left) head target");
	}

	private Expression<LivingEntity> entity;
	private int mark;

	@Override
	protected Entity[] get(Event e) {
		if (this.entity != null) {
			LivingEntity maybeWither = this.entity.getSingle(e);
			if (maybeWither instanceof Wither) {
				return new Entity[]{NMSUtil.getWitherHeadTarget((Wither)maybeWither, this.mark)};
			}
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
		this.entity = (Expression<LivingEntity>)exprs[0];
		this.mark = parseResult.mark;
		return true;
	}

	@Override
	public Class<?>[] acceptChange(Changer.ChangeMode mode) {
		if (mode == Changer.ChangeMode.DELETE || mode == Changer.ChangeMode.RESET || mode == Changer.ChangeMode.SET) {
			return CollectionUtils.array(LivingEntity.class);
		}
		return null;
	}

	@Override
	public void change(Event e, Object[] delta, Changer.ChangeMode mode) {
		if (this.entity != null) {
			LivingEntity maybeWither = this.entity.getSingle(e);
			if (maybeWither instanceof Wither) {
				if (mode == Changer.ChangeMode.DELETE || mode == Changer.ChangeMode.RESET) {
					NMSUtil.setWitherHeadTarget((Wither)maybeWither, this.mark, null);
				} else if (mode == Changer.ChangeMode.SET) {
					if (delta[0] instanceof LivingEntity) {
						NMSUtil.setWitherHeadTarget((Wither)maybeWither, this.mark, (LivingEntity)delta[0]);
					}
				}
			}
		}
	}

}
