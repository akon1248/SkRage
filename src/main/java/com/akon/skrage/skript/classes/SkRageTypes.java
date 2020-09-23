package com.akon.skrage.skript.classes;


import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.EnumSerializer;
import ch.njol.skript.registrations.Classes;
import com.akon.skrage.utils.anvilgui.AnvilGUI;
import com.akon.skrage.utils.damagesource.DamageSourceBuilder;
import com.akon.skrage.utils.signeditor.SignEditor;
import com.akon.skrage.utils.skin.Skin;
import org.apache.logging.log4j.spi.StandardLevel;
import org.bukkit.Bukkit;
import org.bukkit.event.entity.EntityDamageEvent;

public class SkRageTypes {
	
	static {
		Classes.registerClass(new ClassInfo<>(EntityDamageEvent.DamageModifier.class, "damagemodifier")
			.user("damage ?modifiers?")
			.name("Damage Modifier")
			.usage("absorption, armor, base, blocking, hard hat, magic, resistance")
			.parser(new EnumParser<>())
			.serializer(new EnumSerializer<>(EntityDamageEvent.DamageModifier.class)));
		Classes.registerClass(new ClassInfo<>(StandardLevel.class, "loglevel")
			.user("log ?levels?")
			.name("Log Level")
			.usage("off, fatal, error, warn, info, debug, trace, all")
			.parser(new EnumParser<>())
			.serializer(new EnumSerializer<>(StandardLevel.class)));
		Classes.registerClass(new ClassInfo<>(DamageSourceBuilder.class, "damagesource")
			.user("damage ?sources?")
			.name("Damage Source")
			.parser(new UnparsableParser<>()));
		Classes.registerClass(new ClassInfo<>(AnvilGUI.class, "anvilinv")
			.user("anvil ?invs?")
			.name("Anvil GUI")
			.parser(new UnparsableParser<>()));
		Classes.registerClass(new ClassInfo<>(Skin.class, "skin")
			.user("skins?")
			.name("Skin")
			.parser(new UnparsableParser<>()));
		Classes.registerClass(new ClassInfo<>(SignEditor.class, "signeditor")
			.user("sign ?editors?")
			.name("Sign Editor")
			.parser(new UnparsableParser<>()));
	}
}
