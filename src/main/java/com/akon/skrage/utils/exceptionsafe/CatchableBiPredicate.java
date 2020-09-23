package com.akon.skrage.utils.exceptionsafe;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Predicate;

@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class CatchableBiPredicate<T, U, X extends Throwable> implements BiPredicate<T, U>, Catchable<Predicate<? super X>, BiConsumer<? super T, ? super U>, BiPredicate<T, U>> {

    private final ThrowsBiPredicate<T, U, X> throwsBiPredicate;
    private BiPredicate<T, U> caught;

    @Override
    public BiPredicate<T, U> caught(@Nullable Predicate<? super X> c, @Nullable BiConsumer<? super T, ? super U> f) {
        return (t, u) -> {
            try {
                return this.throwsBiPredicate.test(t, u);
            } catch (Throwable ex) {
                return Optional.ofNullable(c).map(predicate -> predicate.test((X)ex)).orElse(false);
            } finally {
                Optional.ofNullable(f).ifPresent(biConsumer -> biConsumer.accept(t, u));
                ExceptionSafe.closeResource(t);
                ExceptionSafe.closeResource(u);
            }
        };
    }

    public BiPredicate<T, U> caught(@Nullable Consumer<? super X> c, @Nullable BiConsumer<T, U> f) {
        return this.caught(Optional.ofNullable(c).map(consumer -> (Predicate<X>)t -> {
            consumer.accept(t);
            return false;
        }).orElse(null), f);
    }

    public BiPredicate<T, U> caught(@Nullable Consumer<? super X> c) {
        return this.caught(c, null);
    }

    @Override
    public boolean test(T t, U u) {
        return Optional.ofNullable(this.caught).orElse(this.caught = this.caught()).test(t, u);
    }
}
