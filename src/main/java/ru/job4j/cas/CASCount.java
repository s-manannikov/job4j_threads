package ru.job4j.cas;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
public class CASCount<T> {
    private final AtomicInteger count = new AtomicInteger(0);

    public void increment() {
        int value;
        do {
            value = count.get();
        } while (!count.compareAndSet(value, ++value));
    }

    public int get() {
        return count.get();
    }
}