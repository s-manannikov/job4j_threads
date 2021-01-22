package ru.job4j.pool;

import org.junit.Test;

import static org.junit.Assert.*;

public class ParallelSearchTest {

    @Test
    public void whenFound() {
        Integer[] array = new Integer[100];
        for (int i = 0; i < 100; i++) {
            array[i] = i;
        }
        ParallelSearch<Integer> p = new ParallelSearch<>(array, 77);
        assertEquals(p.find(), 77);
    }

    @Test
    public void whenNotFound() {
        Integer[] array = new Integer[100];
        for (int i = 0; i < 100; i++) {
            array[i] = i;
        }
        ParallelSearch<Integer> p = new ParallelSearch<>(array, 777);
        assertEquals(p.find(), -1);
    }
}