package com.example.submission.BackupRunners;

import org.mdkt.compiler.InMemoryJavaCompiler;

import java.lang.reflect.Method;
import java.util.concurrent.*;

import static java.lang.reflect.Modifier.isStatic;

public class InMemoryCodeRunner {
    private static final int NUM_THREADS = 20;

    private final ExecutorService pool = Executors.newFixedThreadPool(NUM_THREADS);

    private final InMemoryJavaCompiler compiler = InMemoryJavaCompiler.newInstance();

    public Object runWithTimeout(
            String className,
            String sourceCode,
            String methodName,
            Object[] args,
            Class<?>[] paramTypes,
            long timeoutMillis) throws TimeoutException, ExecutionException, InterruptedException {

        Class<?> tmpCls = null;
        try {
            tmpCls = compiler.compile(className, sourceCode);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        final Class<?> cls = tmpCls;
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
