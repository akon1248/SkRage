package com.akon.skrage.utils.exceptionsafe;

import java.util.Objects;
import java.util.function.Function;

@FunctionalInterface
public interface ThrowsBiFunction<T, U, R, X extends Throwable> {

    R apply(T t, U u) throws X;

    default <V> ThrowsBiFunction<T, U, V, ? extends Throwable> andThen(ThrowsFunction<? super R, ? extends V, ? extends Throwable> after) {
        Objects.requireNonNull(after);
        return (t, u) -> after.apply(this.apply(t, u));
    }

    default <V> ThrowsBiFunction<T, U, V, X> andThen(Function<? super R, ? extends V> after) {
        Objects.requireNonNull(after);
        return (t, u) -> after.apply(this.apply(t, u));
    }

}
