package com.wonders.hms.exception;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class LambdaException {
    @FunctionalInterface
    public interface Consumer_WithExceptions<T, E extends Exception> {
        void accept(T t) throws E;
    }

    @FunctionalInterface
    public interface BiConsumer_WithExceptions<T, U, E extends Exception> {
        void accept(T t, U u) throws E;
    }

    @FunctionalInterface
    public interface Function_WithExceptions<T, R, E extends Exception> {
        R apply(T t) throws E;
    }

    @FunctionalInterface
    public interface Supplier_WithExceptions<T, E extends Exception> {
        T get() throws E;
    }

    @FunctionalInterface
    public interface Runnable_WithExceptions<E extends Exception> {
        void run() throws E;
    }

    public static <T, E extends Exception> Consumer<T> rethrow(Consumer_WithExceptions<T, E> consumer) {
        return t -> {
            try {
                consumer.accept(t);
            } catch (Exception e) {
                throwAsUnchecked(e);
            }
        };
    }

    public static <T, U, E extends Exception> BiConsumer<T, U> rethrow(BiConsumer_WithExceptions<T, U, E> biConsumer) {
        return (t, u) -> {
            try {
                biConsumer.accept(t, u);
            } catch (Exception e) {
                throwAsUnchecked(e);
            }
        };
    }

    public static <T, R, E extends Exception> Function<T, R> rethrow(Function_WithExceptions<T, R, E> function) {
        return t -> {
            try {
                return function.apply(t);
            } catch (Exception e) {
                throwAsUnchecked(e);
                return null;
            }
        };
    }

    public static <T, E extends Exception> Supplier<T> rethrow(Supplier_WithExceptions<T, E> supplier) {
        return () -> {
            try {
                return supplier.get();
            } catch (Exception e) {
                throwAsUnchecked(e);
                return null;
            }
        };
    }

    public static <R, E extends Exception> Runnable rethrow(Runnable_WithExceptions<E> runnable) {
        return () -> {
            try {
                runnable.run();
            } catch (Exception e) {
                throwAsUnchecked(e);
            }
        };
    }

    @SuppressWarnings("unchecked")
    private static <E extends Throwable> void throwAsUnchecked(Exception e) throws E {
        throw (E) e;
    }

}
