package com.akon.skrage.utils.exceptionsafe;

import org.jetbrains.annotations.Nullable;

public interface Catchable<C, F, R> {

    R caught(@Nullable C c, @Nullable F f);

    default R caught(@Nullable C c) {
        return this.caught(c, null);
    }

    default R caught() {
        return this.caught(null, null);
    }

}
