package ru.job4j.pool;

import org.junit.Test;

import java.util.concurrent.CopyOnWriteArrayList;

import static org.junit.Assert.*;

public class ThreadPoolTest {

    @Test
    public void work() {
        final CopyOnWriteArrayList<String> buffer = new CopyOnWriteArrayList<>();
        final ThreadPool pool = new ThreadPool(10);
        for (int i = 0; i < 10; i++) {
            String s = "task" + i;
            pool.work(() -> buffer.add(s));
        }
        pool.shutdown();
        assertEquals(buffer.size(), 10);
    }
}