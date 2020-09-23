package com.akon.skrage.utils.exceptionsafe;

import java.util.Objects;
import java.util.function.BiConsumer;

@FunctionalInterface
public interface ThrowsBiConsumer<T, U, X extends Throwable> {

    void accept(T t, U u) throws X;

    default ThrowsBiConsumer<T, U, ? extends Throwable> andThen(ThrowsBiConsumer<? super T, ? super U, ? extends Throwable> after) {
        Objects.requireNonNull(after);
        return (l, r) -> {
            this.accept(l, r);
            after.accept(l, r);
        };
    }

    default ThrowsBiConsumer<T, U, X> andThen(BiConsumer<? super T, ? super U> after) {
        Objects.requireNonNull(after);
        return (l, r) -> {
            this.accept(l, r);
            after.accept(l, r);
        };
    }

}
