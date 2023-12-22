package com.akon.skrage.utils.exceptionsafe;

import org.jetbrains.annotations.Nullable;

public interface Catchable<C, F, R> {

    R onCatch(@Nullable C c, @Nullable F f);

    default R onCatch(@Nullable C c) {
        return this.onCatch(c, null);
    }

    default R onCatch() {
        return this.onCatch(null, null);
    }

}
