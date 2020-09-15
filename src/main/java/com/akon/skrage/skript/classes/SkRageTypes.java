package com.akon.skrage.skript.classes;


import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.EnumSerializer;
import ch.njol.skript.classes.Parser;
import ch.njol.skript.lang.ParseContext;
import ch.njol.skript.registrations.Classes;
import com.akon.skrage.skript.syntaxes.expressions.ExprDamageModifier;
import com.akon.skrage.classes.EnumParser;
import com.akon.skrage.utils.anvilgui.AnvilGUI;
import com.akon.skrage.utils.signeditor.SignEditor;
import com.akon.skrage.utils.skin.Skin;
import org.apache.logging.log4j.spi.StandardLevel;
import org.bukkit.event.entity.EntityDamageEvent.DamageModifier;

public class SkRageTypes {
	
	static {
		Classes.registerClass(new ClassInfo<>(DamageModifier.class, "damagemodifier")
			.user("damage ?modifiers?")
			.name("Damage Modifier")
			.usage("absorption, armor, base, blocking, hard hat, magic, resistance")
			.defaultExpression(new ExprDamageModifier())
			.parser(new EnumParser<>())
			.serializer(new EnumSerializer<>(DamageModifier.class)));
		Classes.registerClass(new ClassInfo<>(StandardLevel.class, "loglevel")
			.user("log ?levels?")
			.name("Log Level")
			.usage("off, fatal, error, warn, info, debug, trace, all")
			.parser(new EnumParser<>())
			.serializer(new EnumSerializer<>(StandardLevel.class)));
		Classes.registerClass(new ClassInfo<>(AnvilGUI.class, "anvilinv")
			.user("anvil ?invs?")
			.name("Anvil GUI")
			.parser(new Parser<AnvilGUI>() {

				@Override
				public boolean canParse(ParseContext context) {
					return false;
				}

				@Override
				public String toString(AnvilGUI o, int flags) {
					return o.toString();
				}

				@Override
				public String toVariableNameString(AnvilGUI o) {
					return o.toString();
				}

				@Override
				public String getVariableNamePattern() {
					return ".+";
				}

			}));
		Classes.registerClass(new ClassInfo<>(Skin.class, "skin")
			.user("skins?")
			.name("Skin")
			.parser(new Parser<Skin>() {

				@Override
				public boolean canParse(ParseContext context) {
					return false;
				}

				@Override
				public Skin parse(String s, ParseContext context) {
					return null;
				}

				@Override
				public String toString(Skin o, int flags) {
					return o.toString();
				}

				@Override
				public String toVariableNameString(Skin o) {
					return o.toString();
				}

				@Override
				public String getVariableNamePattern() {
					return ".+";
				}

			}));
		Classes.registerClass(new ClassInfo<>(SignEditor.class, "signeditor")
			.user("sign ?editors?")
			.name("Sign Editor")
			.parser(new Parser<SignEditor>() {

				@Override
				public boolean canParse(ParseContext context) {
					return false;
				}

				@Override
				public SignEditor parse(String s, ParseContext context) {
					return null;
				}

				@Override
				public String toString(SignEditor o, int flags) {
					return o.toString();
				}

				@Override
				public String toVariableNameString(SignEditor o) {
					return o.toString();
				}

				@Override
				public String getVariableNamePattern() {
					return ".+";
				}

			}));
	}
}
