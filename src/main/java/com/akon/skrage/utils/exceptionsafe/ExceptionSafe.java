package com.akon.skrage.utils.exceptionsafe;

import com.akon.skrage.utils.LogUtil;
import org.jetbrains.annotations.Nullable;

import java.io.Closeable;
import java.io.IOException;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class ExceptionSafe {

    public static final Consumer<Throwable> PRINT_STACK_TRACE = LogUtil::logThrowable;
    public static final Supplier<CatchableConsumer<Closeable, IOException>> CLOSE = () -> consumer(Closeable::close);

    public static <T, U, X extends Throwable> CatchableBiConsumer<T, U, X> biConsumer(ThrowsBiConsumer<T, U, X> throwsBiConsumer) {
        return new CatchableBiConsumer<>(throwsBiConsumer);
    }

    public static <T, U, R, X extends Throwable> CatchableBiFunction<T, U, R, X> biFunction(ThrowsBiFunction<T, U, R, X> throwsBiFunction) {
        return new CatchableBiFunction<>(throwsBiFunction);
    }

    public static <T, U, X extends Throwable> CatchableBiPredicate<T, U, X> biPredicate(ThrowsBiPredicate<T, U, X> throwsBiPredicate) {
        return new CatchableBiPredicate<>(throwsBiPredicate);
    }

    public static <T, X extends Throwable> CatchableConsumer<T, X> consumer(ThrowsConsumer<T, X> throwsConsumer) {
        return new CatchableConsumer<>(throwsConsumer);
    }

    public static <T, R, X extends Throwable> CatchableFunction<T, R, X> function(ThrowsFunction<T, R, X> throwsFunction) {
        return new CatchableFunction<>(throwsFunction);
    }

    public static <T, X extends Throwable> CatchablePredicate<T, X> predicate(ThrowsPredicate<T, X> throwsPredicate) {
        return new CatchablePredicate<>(throwsPredicate);
    }

    public static <X extends Throwable> CatchableRunnable<X> runnable(ThrowsRunnable<X> throwsRunnable) {
        return new CatchableRunnable<>(throwsRunnable);
    }

    public static <T, X extends Throwable> CatchableSupplier<T, X> supplier(ThrowsSupplier<T, X> throwsSupplier) {
        return new CatchableSupplier<>(throwsSupplier);
    }

    static void closeResource(@Nullable Object obj) {
        Optional.ofNullable(obj).filter(AutoCloseable.class::isInstance).map(AutoCloseable.class::cast).ifPresent(consumer(AutoCloseable::close));
    }

}
