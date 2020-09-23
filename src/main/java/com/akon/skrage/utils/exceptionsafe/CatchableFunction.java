package com.akon.skrage.utils.exceptionsafe;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class CatchableFunction<T, R, X extends Throwable> implements Function<T, R>, Catchable<Function<? super X, R>, Consumer<? super T>, Function<T, Optional<R>>> {

    private final ThrowsFunction<T, R, X> throwsFunction;
    private Function<T, Optional<R>> caught;

    @Override
    public Function<T, Optional<R>> caught(@Nullable Function<? super X, R> c, @Nullable Consumer<? super T> f) {
        return  t -> {
            try {
                return Optional.ofNullable(this.throwsFunction.apply(t));
            } catch (Throwable ex) {
                return Optional.ofNullable(c).map(func -> func.apply((X)ex));
            } finally {
                Optional.ofNullable(f).ifPresent(consumer -> consumer.accept(t));
                ExceptionSafe.closeResource(t);
            }
        };
    }

    public Function<T, Optional<R>> caught(@Nullable Consumer<? super X> c, @Nullable Consumer<T> f) {
        return this.caught(Optional.ofNullable(c).map(consumer -> (Function<? super X, R>)t -> {
            consumer.accept(t);
            return null;
        }).orElse(null), f);
    }

    public Function<T, Optional<R>> caught(@Nullable Consumer<? super X> c) {
        return this.caught(c, null);
    }

    public Optional<R> applyOptional(T t) {
        return Optional.ofNullable(this.caught).orElse(this.caught = this.caught()).apply(t);
    }

    @Nullable
    @Override
    public R apply(T t) {
        return this.applyOptional(t).orElse(null);
    }
}
