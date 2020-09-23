package com.akon.skrage.utils.exceptionsafe;

import java.util.Objects;
import java.util.function.Function;

@FunctionalInterface
public interface ThrowsFunction<T, R, X extends Throwable> {

    R apply(T t) throws X;

    default <V> ThrowsFunction<V, R, ? extends Throwable> compose(ThrowsFunction<? super V, ? extends T, ? extends Throwable> before) {
        Objects.requireNonNull(before);
        return v -> this.apply(before.apply(v));
    }

    default <V> ThrowsFunction<V, R, X> compose(Function<? super V, ? extends T> before) {
        Objects.requireNonNull(before);
        return v -> this.apply(before.apply(v));
    }

    default <V> ThrowsFunction<T, V, ? extends Throwable> andThen(ThrowsFunction<? super R, ? extends V, ? extends Throwable> after) {
        Objects.requireNonNull(after);
        return t -> after.apply(this.apply(t));
    }

    default <V> ThrowsFunction<T, V, X> andThen(Function<? super R, ? extends V> after) {
        Objects.requireNonNull(after);
        return t -> after.apply(this.apply(t));
    }

}
