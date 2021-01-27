package ru.job4j.switcher;

public class MasterSlaveBarrier {
    boolean flag = true;

    public synchronized void tryMaster() {
        try {
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        flag = true;
    }
    
    public synchronized void trySlave() {
        notify();
    }
    
    public synchronized void doneMaster() {
        notify();
    }
    
    public synchronized void doneSlave() {
        try {
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        flag = false;
    }
}