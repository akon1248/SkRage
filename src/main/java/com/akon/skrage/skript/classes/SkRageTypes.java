package com.akon.skrage.skript.classes;


import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.classes.EnumSerializer;
import ch.njol.skript.classes.Serializer;
import ch.njol.skript.registrations.Classes;
import ch.njol.yggdrasil.Fields;
import com.akon.skrage.utils.anvilgui.AnvilGUI;
import com.akon.skrage.utils.customstatuseffect.CSEType;
import com.akon.skrage.utils.customstatuseffect.CustomStatusEffect;
import com.akon.skrage.utils.damagesource.DamageSourceBuilder;
import com.akon.skrage.utils.signeditor.SignEditor;
import com.akon.skrage.utils.skin.Skin;
import me.dpohvar.powernbt.PowerNBT;
import me.dpohvar.powernbt.api.NBTCompound;
import me.dpohvar.powernbt.api.NBTList;
import org.apache.logging.log4j.spi.StandardLevel;
import org.bukkit.Bukkit;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.event.entity.EntityDamageEvent;

import java.io.NotSerializableException;
import java.io.StreamCorruptedException;

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
			.name("Damage Source"));
		Classes.registerClass(new ClassInfo<>(AnvilGUI.class, "anvilinv")
			.user("anvil ?invs?")
			.name("Anvil GUI"));
		Classes.registerClass(new ClassInfo<>(Skin.class, "playerskin")
			.user("player ?skins?")
			.name("Player Skin"));
		Classes.registerClass(new ClassInfo<>(SignEditor.class, "signeditor")
			.user("sign ?editors?")
			.name("Sign Editor"));
		Classes.registerClass(new ClassInfo<>(CSEType.class, "customstatuseffecttype")
			.user("(cse|custom ?status ?effect) ?types?")
			.name("Custom Status Effect Type"));
		Classes.registerClass(new ClassInfo<>(CustomStatusEffect.class, "customstatuseffect")
			.user("(cse|custom ?status ?effect)s?")
			.name("Custom Status Effect"));
		Classes.registerClass(new ClassInfo<>(AttributeInstance.class, "attributeinstance")
			.user("attribute ?instances?")
			.name("Attribute Instance"));
		Classes.registerClass(new ClassInfo<>(AttributeModifier.class, "attributemodifier")
			.user("attribute ?modifier")
			.name("Attribute Modifier"));
		if (!Bukkit.getPluginManager().isPluginEnabled("MundoSK")) {
			Classes.registerClass(new ClassInfo<>(Throwable.class, "throwable")
				.user("throwables?")
				.user("Throwable"));
			Classes.registerClass(new ClassInfo<>(StackTraceElement.class, "stacktraceelement")
				.user("stack ?trace ?elements?")
				.user("Stack Trace Element"));
		}
		if (Bukkit.getPluginManager().isPluginEnabled("PowerNBT")) {
			Classes.registerClass(new ClassInfo<>(NBTCompound.class, "nbtcompound")
				.user("nbt ?compounds?")
				.name("NBT Compound")
				.description("NBTを表す型")
				.serializer(new Serializer<NBTCompound>() {

					@Override
					public Fields serialize(NBTCompound o) {
						Fields fields = new Fields();
						fields.putObject("mojangson", o.getHandle().toString());
						return fields;
					}

					@Override
					public void deserialize(NBTCompound o, Fields f) {
						assert false;
					}

					@Override
					protected NBTCompound deserialize(Fields fields) throws StreamCorruptedException, NotSerializableException {
						try {
							return (NBTCompound)PowerNBT.getApi().parseMojangson((String)fields.getObject("mojangson"));
						} catch (ClassCastException ex) {
							throw new NotSerializableException(NBTCompound.class.getName());
						}
					}

					@Override
					public boolean mustSyncDeserialization() {
						return false;
					}

					@Override
					protected boolean canBeInstantiated() {
						return false;
					}

				}));
			Classes.registerClass(new ClassInfo<>(NBTList.class, "nbtlist")
				.user("nbt ?lists?")
				.name("NBT List")
				.serializer(new Serializer<NBTList>() {

					@Override
					public Fields serialize(NBTList o) {
						Fields fields = new Fields();
						fields.putObject("mojangson", o.getHandle().toString());
						return fields;
					}

					@Override
					public void deserialize(NBTList o, Fields f) {
						assert false;
					}

					@Override
					protected NBTList deserialize(Fields fields) throws StreamCorruptedException, NotSerializableException {
						try {
							return (NBTList)PowerNBT.getApi().parseMojangson((String)fields.getObject("mojangson"));
						} catch (ClassCastException ex) {
							throw new NotSerializableException(NBTList.class.getName());
						}
					}

					@Override
					public boolean mustSyncDeserialization() {
						return false;
					}

					@Override
					protected boolean canBeInstantiated() {
						return false;
					}

				}));
		}
	}
}
