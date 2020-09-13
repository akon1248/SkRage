package com.akon.skrage.utils;

import ch.njol.skript.classes.Parser;
import ch.njol.skript.lang.ParseContext;

import java.lang.reflect.ParameterizedType;

public class EnumParser<T extends Enum<?>> extends Parser<T> {

	@Override
	public T parse(String s, ParseContext context) {
		if (s.matches("[^_A-Z]")) {
			try {
				Class<T> clazz = (Class<T>)((ParameterizedType)EnumParser.class.getGenericSuperclass()).getActualTypeArguments()[0];
				return (T)ReflectionUtil.getStaticField(clazz, s.toLowerCase().replace(" ", "_"));
			} catch (IllegalArgumentException | NoSuchFieldException | IllegalAccessException ignored) {}
		}
		return null;
	}

	@Override
	public String toString(T value, int flags) {
		return value.name().toLowerCase();
	}

	@Override
	public String toVariableNameString(T value) {
		return value.name();
	}

	@Override
	public String getVariableNamePattern() {
		return "[a-z0-9_-]+";
	}

}
