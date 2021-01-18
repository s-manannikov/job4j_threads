package ru.job4j.cas;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicReference;

import static org.junit.Assert.*;

public class NonBlockingCacheTest {

    @Test
    public void whenAdd() throws InterruptedException {
        NonBlockingCache cache = new NonBlockingCache();
        Thread t1 = new Thread(() -> {
            for (int i = 1; i < 101; i++) {
                cache.add(new Base(i, "name" + i));
            }
        });
        t1.start();
        Thread t2 = new Thread(() -> {
            for (int i = 101; i < 201; i++) {
                cache.add(new Base(i, "name" + i));
            }
        });
        t2.start();
        t1.join();
        t2.join();
        assertEquals(cache.map.size(), 200);
    }

    @Test
    public void whenUpdate() throws InterruptedException {
        NonBlockingCache cache = new NonBlockingCache();
        cache.add(new Base(1, "name"));
        Base base1 = new Base(1, "updated name1");
        Base base2 = new Base(1, "updated name2");
        Base base3 = new Base(1, "updated name3");
        Thread t1 = new Thread(() -> {
            try {
                cache.update(base1);
            } catch (OptimisticException e) {
                e.printStackTrace();
            }
        });
        Thread t2 = new Thread(() -> {
            try {
                cache.update(base2);
            } catch (OptimisticException e) {
                e.printStackTrace();
            }
        });
        Thread t3 = new Thread(() -> {
            try {
                cache.update(base3);
            } catch (OptimisticException e) {
                e.printStackTrace();
            }
        });
        t1.start();
        t2.start();
        t3.start();
        t1.join();
        t2.join();
        t3.join();
        assertEquals(cache.map.get(1).getVersion(), 1);
    }

    @Test
    public void whenDelete() throws InterruptedException {
        NonBlockingCache cache = new NonBlockingCache();
        Base base = new Base(1, "name1");
        Thread t1 = new Thread(() -> {
            cache.add(base);
        });
        Thread t2 = new Thread(() -> {
            cache.delete(base);
        });
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        assertNull(cache.map.get(1));
    }

    @Test
    public void whenThrowException() throws InterruptedException {
        AtomicReference<Exception> ex = new AtomicReference<>();
        Thread thread = new Thread(
                () -> {
                    try {
                        throw new RuntimeException("Throw Exception in Thread");
                    } catch (Exception e) {
                        ex.set(e);
                    }
                }
        );
        thread.start();
        thread.join();
        assertEquals(ex.get().getMessage(), "Throw Exception in Thread");
    }
}