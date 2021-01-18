package ru.job4j.cas;

import org.junit.Test;

import java.util.stream.IntStream;

import static org.junit.Assert.*;

public class CASCountTest {

    @Test
    public void whenIncrementAndGet() {
        CASCount<Integer> count = new CASCount<>();
        IntStream.rangeClosed(0, 100).forEach(i -> new Thread(count::increment).start());
        assertEquals(count.get(), 100);
    }
}