package com.akon.skrage.skript.classes;

import ch.njol.skript.classes.Parser;
import ch.njol.skript.lang.ParseContext;

public class UnparsableParser<T> extends Parser<T> {

    @Override
    public boolean canParse(ParseContext context) {
        return false;
    }

    @Override
    public T parse(String s, ParseContext context) {
        return null;
    }

    @Override
    public String toString(T o, int flags) {
        return o.toString();
    }

    @Override
    public String toVariableNameString(T o) {
        return o.toString();
    }

    @Override
    public String getVariableNamePattern() {
        return ".+";
    }

}
