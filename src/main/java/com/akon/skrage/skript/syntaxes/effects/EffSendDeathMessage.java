package com.akon.skrage.skript.syntaxes.effects;

import ch.njol.skript.Skript;
import ch.njol.skript.doc.Description;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.util.Kleenean;
import com.akon.skrage.utils.NMSUtil;
import net.md_5.bungee.api.chat.BaseComponent;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Event;

import java.util.Arrays;

@Description({"エンティティの死亡メッセージを送信します"})
public class EffSendDeathMessage extends Effect {

	static {
		Skript.registerEffect(EffSendDeathMessage.class, "send death message of %livingentity% to %commandsenders%");
	}

	private Expression<LivingEntity> entity;
	private Expression<CommandSender> sender;

	@Override
	protected void execute(Event e) {
		if (this.entity != null && this.sender != null) {
			BaseComponent[] msg = NMSUtil.getDeathMessage(this.entity.getSingle(e));
			Arrays.stream(this.sender.getAll(e)).forEach(commandSender -> commandSender.spigot().sendMessage(msg));
		}
	}

	@Override
	public String toString(Event e, boolean debug) {
		return "";
	}

	@Override
	public boolean init(Expression<?>[] exprs, int matchedPattern, Kleenean isDelayed, SkriptParser.ParseResult parseResult) {
		this.entity = (Expression<LivingEntity>)exprs[0];
		this.sender = (Expression<CommandSender>)exprs[1];
		return true;
	}

}
