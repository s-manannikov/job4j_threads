package ru.job4j.cas;

import org.junit.Test;

import java.util.stream.IntStream;

import static org.junit.Assert.*;

public class CASCountTest {

    @Test
    public void whenIncrementAndGet() {
        CASCount<Integer> count = new CASCount<>();
        for (int i = 0; i <= 100; i++) {
            new Thread(count::increment).start();
        }
        assertEquals(count.get(), 100);
    }

    @Test
    public void when2Threads() throws InterruptedException {
        CASCount<Integer> count = new CASCount<>();
        Thread t1 = new Thread(() -> {
            IntStream.range(0, 100).forEach(i -> count.increment());
        });
        Thread t2 = new Thread(() -> {
            IntStream.range(0, 100).forEach(i -> count.increment());
        });
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        assertEquals(count.get(), 200);
    }
}