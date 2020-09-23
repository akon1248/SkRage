package com.akon.skrage.utils.exceptionsafe;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class CatchableBiFunction<T, U, R, X extends Throwable> implements BiFunction<T, U, R>, Catchable<Function<? super X, R>, BiConsumer<? super T, ? super U>, BiFunction<T, U, Optional<R>>> {

    private final ThrowsBiFunction<T, U, R, X> throwsBiFunction;
    private BiFunction<T, U, Optional<R>> caught;

    @Override
    public BiFunction<T, U, Optional<R>> caught(@Nullable Function<? super X, R> c, @Nullable BiConsumer<? super T, ? super U> f) {
        return (t, u) -> {
            try {
                return Optional.ofNullable(this.throwsBiFunction.apply(t, u));
            } catch (Throwable ex) {
                return Optional.ofNullable(c).map(func -> func.apply((X)ex));
            } finally {
                Optional.ofNullable(f).ifPresent(biConsumer -> biConsumer.accept(t, u));
                ExceptionSafe.closeResource(t);
                ExceptionSafe.closeResource(u);
            }
        };
    }

    public BiFunction<T, U, Optional<R>> caught(@Nullable Consumer<? super X> c, @Nullable BiConsumer<T, U> f) {
        return this.caught(Optional.ofNullable(c).map(consumer -> (Function<X, R>)t -> {
            consumer.accept(t);
            return null;
        }).orElse(null), f);
    }

    public BiFunction<T, U, Optional<R>> caught(@Nullable Consumer<? super X> c) {
        return this.caught(c, null);
    }

    public Optional<R> applyOptional(T t, U u) {
        return Optional.ofNullable(this.caught).orElse(this.caught = this.caught()).apply(t, u);
    }

    @Nullable
    @Override
    public R apply(T t, U u) {
        return this.applyOptional(t, u).orElse(null);
    }
}
