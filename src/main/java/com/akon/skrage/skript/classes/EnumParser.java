package com.akon.skrage.skript.classes;

import ch.njol.skript.classes.Parser;
import ch.njol.skript.lang.ParseContext;
import com.akon.skrage.utils.ReflectionUtil;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.ParameterizedType;

public class EnumParser<T extends Enum<T>> extends Parser<T> {

	@SuppressWarnings("unchecked")
	@Override
	public T parse(String s, @NotNull ParseContext context) {
		if (s.matches("[^_A-Z]")) {
			Class<T> clazz = (Class<T>)((ParameterizedType)EnumParser.class.getGenericSuperclass()).getActualTypeArguments()[0];
			return (T) ReflectionUtil.DEFAULT.getStaticField(clazz, s.toLowerCase().replace(" ", "_"));
		}
		return null;
	}

	@Override
	public @NotNull String toString(T value, int flags) {
		return value.name().toLowerCase();
	}

	@Override
	public @NotNull String toVariableNameString(T value) {
		return value.name();
	}

	@Override
	public @NotNull String getVariableNamePattern() {
		return "[a-z0-9_-]+";
	}

}
