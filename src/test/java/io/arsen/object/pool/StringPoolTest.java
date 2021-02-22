package io.arsen.object.pool;

import io.arsen.object.pool.config.ObjectPoolConfig;
import io.arsen.object.pool.objectfactory.ObjectFactory;
import io.arsen.object.pool.validation.DefaultObjectValidator;
import org.junit.Test;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

public class StringPoolTest {

    private static final int POOL_MIN_SIZE = 0;

    private static final int POOL_MAX_SIZE = 1;

    @Test
    public void testObjectPool() throws InterruptedException {
        final AtomicReference<String> atomicReference = new AtomicReference<>();

        final StringPool stringPool = new StringPool();

        final Thread t1 = new Thread(() -> {
            System.out.println("t1 starting");
            atomicReference.set(stringPool.get());
            System.out.println("t1 finishing");
        });
        t1.start();

        Thread.sleep(5000);

        final Thread t2 = new Thread(() -> {
            System.out.println("t2 starting");
            final String str = stringPool.get();
            System.out.println("t2 finishing");

        });
        t2.start();

        Thread.sleep(5000);

        System.out.println("releasing object to the pool");
        stringPool.release(atomicReference.get());

        Thread.sleep(5000);

    }

    private static class StringPool extends AbstractObjectPool<String> {

        StringPool() {
            super(ObjectPoolConfig.of(POOL_MIN_SIZE, POOL_MAX_SIZE), new StringFactory(), new DefaultObjectValidator<>());
        }
    }

    private static class StringFactory implements ObjectFactory<String> {

        @Override
        public String createObject() {
            return UUID.randomUUID().toString();
        }
    }
}