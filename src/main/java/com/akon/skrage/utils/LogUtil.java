package com.akon.skrage.utils;

import com.akon.skrage.SkRage;
import lombok.experimental.UtilityClass;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.regex.Pattern;

@UtilityClass
public class LogUtil {

    private final Pattern NEW_LINE = Pattern.compile("\r?\n|\r");

    public String getFullMessage(Throwable throwable) {
        StringWriter writer = new StringWriter();
        throwable.printStackTrace(new PrintWriter(writer));
        return writer.toString();
    }

    public void logThrowable(Throwable throwable) {
        for (String line: NEW_LINE.split(getFullMessage(throwable))) {
            SkRage.getInstance().getLogger().warning(line);
        }
    }
}
