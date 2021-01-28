package ru.job4j.switcher;

public class MasterSlaveBarrier {
    boolean flag = false;

    public synchronized void tryMaster() {
        while (flag) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    public synchronized void trySlave() {
        while (!flag) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    public synchronized void doneMaster() {
        if (!flag) {
            notify();
        }
        flag = true;
    }
    
    public synchronized void doneSlave() {
        if (flag) {
            notify();
        }
        flag = false;
    }
}