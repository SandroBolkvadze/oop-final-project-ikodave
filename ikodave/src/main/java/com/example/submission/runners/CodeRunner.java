package com.example.submission.runners;

import org.mdkt.compiler.InMemoryJavaCompiler;

import java.lang.reflect.Method;
import java.util.concurrent.*;

import static java.lang.reflect.Modifier.isStatic;

public class CodeRunner {
    private static final int NUM_THREADS = 20;

    private final ExecutorService pool = Executors.newFixedThreadPool(NUM_THREADS);

    public Object runWithTimeout(
            String className,
            String sourceCode,
            String methodName,
            Object[] args,
            Class<?>[] paramTypes,
            long timeoutMillis) throws TimeoutException, ExecutionException, InterruptedException {

        Class<?> tmpCls = null;
        try {
            tmpCls = InMemoryJavaCompiler.newInstance().compile(className, sourceCode);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Class<?> cls = tmpCls;
        Future<Object> future = pool.submit(() -> {
            try {
                Method method = cls.getMethod(methodName, paramTypes);

                Object instance = null;
                if (!isStatic(method.getModifiers())) {
                    instance = cls.getDeclaredConstructor().newInstance();
                }
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
