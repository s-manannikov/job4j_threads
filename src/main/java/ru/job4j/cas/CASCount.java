package ru.job4j.cas;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
public class CASCount<T> {
    private final AtomicReference<Integer> count = new AtomicReference<>(0);

    public void increment() {
        int value;
        if (count.get() == null) {
            throw new UnsupportedOperationException("Count is not impl.");
        }
        do {
            value = count.get();
        } while (!count.compareAndSet(value, ++value));
    }

    public int get() {
        if (count.get() == null) {
            throw new UnsupportedOperationException("Count is not impl.");
        }
        return count.get();
    }
}