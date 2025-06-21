package com.example.submission;

import org.mdkt.compiler.InMemoryJavaCompiler;

import java.lang.reflect.Method;
import java.util.concurrent.*;

public class CodeRunner {
    private static final int NUM_THREADS = 10;

    private final ExecutorService pool = Executors.newFixedThreadPool(NUM_THREADS);

    public void shutdown() {
        pool.shutdown();
    }

    public Object runWithTimeout(
            String className,
            String sourceCode,
            String methodName,
            Object[] args,
            Class<?>[] paramTypes,
            long timeoutMillis) throws TimeoutException, ExecutionException, InterruptedException {

        Future<Object> future = pool.submit(() -> {
            try {
                Class<?> cls = InMemoryJavaCompiler.newInstance().compile(className, sourceCode);
                Object instance = cls.getDeclaredConstructor().newInstance();
                Method method = cls.getMethod(methodName, paramTypes);
                return method.invoke(instance, args);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        try {
            return future.get(timeoutMillis, TimeUnit.MILLISECONDS);
        } catch (TimeoutException te) {
            future.cancel(true);
            throw te;
        }
    }
}
