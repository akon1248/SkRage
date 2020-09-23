package com.akon.skrage.utils.exceptionsafe;

import java.util.Objects;
import java.util.function.BiPredicate;

@FunctionalInterface
public interface ThrowsBiPredicate<T, U, X extends Throwable> {

    boolean test(T t, U u) throws X;

    default ThrowsBiPredicate<T, U, ? extends Throwable> and(ThrowsBiPredicate<? super T, ? super U, ? extends Throwable> other) {
        Objects.requireNonNull(other);
        return (t, u) -> this.test(t, u) && other.test(t, u);
    }

    default ThrowsBiPredicate<T, U, X> and(BiPredicate<? super T, ? super U> other) {
        Objects.requireNonNull(other);
        return (t, u) -> this.test(t, u) && other.test(t, u);
    }

    default ThrowsBiPredicate<T, U, X> negate() {
        return (t, u) -> !this.test(t, u);
    }

    default ThrowsBiPredicate<T, U, ? extends Throwable> or(ThrowsBiPredicate<? super T, ? super U, ? extends Throwable> other) {
        Objects.requireNonNull(other);
        return (t, u) -> this.test(t, u) || other.test(t, u);
    }

    default ThrowsBiPredicate<T, U, X> or(BiPredicate<? super T, ? super U> other) {
        Objects.requireNonNull(other);
        return (t, u) -> this.test(t, u) || other.test(t, u);
    }

}
