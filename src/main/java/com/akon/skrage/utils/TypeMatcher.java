package com.akon.skrage.utils;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class TypeMatcher<T, R> {

    private final T obj;
    private boolean matched;
    private R result;

    public static <T, R> TypeMatcher<T, R> matching(T obj) {
        return new TypeMatcher<>(obj);
    }

    public <U extends T> TypeMatcher<T, R> match(Class<U> clazz, Consumer<? super U> action) {
        return this.match(clazz, null, action);
    }

    public <U extends T> TypeMatcher<T, R> match(Class<U> clazz, @Nullable Predicate<? super U> predicate, Consumer<? super U> action) {
        return this.matchAndReturn(clazz, predicate, obj -> {
            action.accept(obj);
            return null;
        });
    }

    public <U extends T> TypeMatcher<T, R> matchAndReturn(Class<U> clazz, Function<? super U, ? extends R> func) {
        return this.matchAndReturn(clazz, null, func);
    }

    public <U extends T> TypeMatcher<T, R> matchAndReturn(Class<U> clazz, @Nullable Predicate<? super U> predicate, Function<? super U, ? extends R> func) {
        if (this.matched) return this;
        if (clazz.isInstance(this.obj) && Optional.ofNullable(predicate).map(p -> p.test(clazz.cast(this.obj))).orElse(true)) {
            this.matched = true;
            this.result = func.apply(clazz.cast(this.obj));
        }
        return this;
    }

    public R defVal(Function<? super T, ? extends R> func) {
        return this.matched ? this.result : func.apply(this.obj);
    }

    public R defVal(Supplier<? extends R> supplier) {
        return this.defVal(obj -> supplier.get());
    }

    public R defNull() {
        return this.defVal(() -> null);
    }

    public R def(Consumer<? super T> action) {
        return this.defVal(obj -> {
            action.accept(obj);
            return null;
        });
    }

    public R def(Runnable action) {
        return this.def(obj -> action.run());
    }

}