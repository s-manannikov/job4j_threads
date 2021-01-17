package ru.job4j.wait;

import org.junit.Test;

import static org.junit.Assert.*;

public class SimpleBlockingQueueTest {

    @Test
    public void whenOffer4ThenPoll2() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(3);
        Thread producer = new Thread(() -> {
            queue.offer(1);
            queue.offer(2);
            queue.offer(3);
            queue.offer(4);
        });
        Thread consumer = new Thread(() -> {
            queue.poll();
            queue.poll();
        });
        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
        assertEquals(queue.getQueue().size(), 2);
    }

    @Test
    public void whenOffer2ThenPoll2() throws InterruptedException {
        SimpleBlockingQueue<Integer> queue = new SimpleBlockingQueue<>(1);
        Thread producer = new Thread(() -> {
            queue.offer(1);
            queue.offer(2);
        });
        Thread consumer = new Thread(() -> {
            queue.poll();
            queue.poll();
        });
        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
        assertEquals(queue.getQueue().size(), 0);
    }
}