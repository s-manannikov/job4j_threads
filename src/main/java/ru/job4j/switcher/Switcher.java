package ru.job4j.switcher;

public class Switcher {
    private static MasterSlaveBarrier barrier = new MasterSlaveBarrier();

    public static void main(String[] args) throws InterruptedException {
        Thread first = new Thread(
                () -> {
                    while (true) {
                        if (barrier.flag) {
                            barrier.tryMaster();
                            System.out.println("Thread A");
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            barrier.doneMaster();
                        }
                    }
                });
        Thread second = new Thread(
                () -> {
                    while (true) {
                        if (!barrier.flag) {
                            System.out.println("Thread B");
                        }
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        barrier.trySlave();
                        barrier.doneSlave();
                    }
                });
        first.start();
        second.start();
        first.join();
        second.join();
    }
}