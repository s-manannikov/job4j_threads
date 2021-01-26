package ru.job4j;

public class Switcher {

    public void threadSwitcher() throws InterruptedException {
        Thread first = new Thread(
                () -> {
                    while (true) {
                        synchronized (this) {
                            System.out.println("Thread A");
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            try {
                                wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            notify();
                        }
                    }
                });
        Thread second = new Thread(
                () -> {
                    while (true) {
                        synchronized (this) {
                            System.out.println("Thread B");
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            notify();
                            try {
                                wait();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
        first.start();
        second.start();
        first.join();
        second.join();
    }

    public static void main(String[] args) throws InterruptedException {
        Switcher s = new Switcher();
        s.threadSwitcher();
    }
}