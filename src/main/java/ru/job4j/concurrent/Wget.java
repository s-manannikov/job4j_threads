package ru.job4j.concurrent;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class Wget implements Runnable {
    private final String url;
    private final String destination;
    private final int speed;

    public Wget(String url, String destination, int speed) {
        this.url = url;
        this.destination = destination;
        this.speed = speed;
    }

    @Override
    public void run() {
        try (BufferedInputStream in = new BufferedInputStream(new URL(url).openStream());
             FileOutputStream fileOutputStream = new FileOutputStream(destination)) {
            byte[] dataBuffer = new byte[1024];
            int bytesRead;
            long startTime = System.nanoTime();
            while ((bytesRead = in.read(dataBuffer, 0, 1024)) != -1) {
                long endTime = System.nanoTime();
                fileOutputStream.write(dataBuffer, 0, bytesRead);
                long elapsedTime = endTime - startTime / 1000000000;
                long finalTime = speed * 100L;
                if (elapsedTime < finalTime) {
                    Thread.sleep(finalTime - elapsedTime);
                }
            }
        } catch (
                IOException | InterruptedException e) {
            e.printStackTrace();
            Thread.currentThread().interrupt();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        String url = args[0];
        String destination = args[1];
        int speed = Integer.parseInt(args[2]);
        Thread wget = new Thread(new Wget(url, destination, speed));
        wget.start();
        wget.join();
    }
}