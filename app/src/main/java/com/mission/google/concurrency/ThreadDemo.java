package com.mission.google.concurrency;

public class ThreadDemo {

    public static void main(String[] args) {
        Producer producer = new Producer();
        Consumer consumer = new Consumer();

        //producer.start();
        //consumer.start();

        /*Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
        YieldSample yieldDemo = new YieldSample();
        yieldDemo.setPriority(Thread.MIN_PRIORITY);
        yieldDemo.start();

        for (int i = 0; i < 5; i++) {
            System.out.println(Thread.currentThread().getName() + " in control");
        }*/

        ResourceLock lock = new ResourceLock();
        ThreadA threadA = new ThreadA(lock);
        ThreadB threadB = new ThreadB(lock);
        ThreadC threadC = new ThreadC(lock);

        threadA.start();
        threadB.start();
        threadC.start();
    }
    public static class ResourceLock {
        public volatile int flag = 0;
        public volatile boolean isZero = true;
    }

    public static class ThreadA extends Thread{
        ResourceLock resourceLock;
        public ThreadA(ResourceLock lock){
            resourceLock = lock;
        }

        @Override
        public void run() {
            printZero();
        }

        public void printZero(){
            synchronized (resourceLock){
                for (int i = 0; i < 100; i++) {
                    try {
                        if (!resourceLock.isZero) {
                            resourceLock.wait();
                        }
                        if (resourceLock.isZero) {
                            System.out.print("0");
                            Thread.sleep(500);
                            resourceLock.isZero = false;
                            resourceLock.notifyAll();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }


    public static class ThreadB extends Thread{
        ResourceLock resourceLock;
        public ThreadB(ResourceLock lock){
            resourceLock = lock;
        }

        @Override
        public void run() {
            printEven();
        }

        public void printEven(){
            synchronized (resourceLock){
                for (int i = 0; i < 100; i++) {
                    try {
                        if (resourceLock.isZero || resourceLock.flag %2 != 0) {
                            resourceLock.wait();
                        }
                        if (!resourceLock.isZero && resourceLock.flag %2 == 0) {
                            System.out.print(++resourceLock.flag);
                            Thread.sleep(500);
                            resourceLock.isZero = true;
                            resourceLock.notifyAll();
                        }

                    }catch (InterruptedException ex){
                        ex.printStackTrace();
                    }
                }
            }
        }
    }

    public static class ThreadC extends Thread {
        ResourceLock resourceLock;

        public ThreadC(ResourceLock lock) {
            resourceLock = lock;
        }

        @Override
        public void run() {
            printOdd();
        }

        public void printOdd() {
            synchronized (resourceLock) {
                for (int i = 0; i < 100; i++) {
                    try {
                        if (resourceLock.isZero || resourceLock.flag %2 != 1) {
                            resourceLock.wait();
                        }
                        if (!resourceLock.isZero && resourceLock.flag %2 == 1) {
                            System.out.print(++resourceLock.flag);
                            Thread.sleep(500);
                            resourceLock.isZero = true;
                            resourceLock.notifyAll();
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    static class YieldSample extends Thread {
        @Override
        public void run() {
            for (int i = 1; i < 6; i++) {
                Thread.yield();
                System.out.println(" YieldSample in control");
            }
        }
    }

    static class Producer extends Thread {

        @Override
        public void run() {
            for (int i = 1; i < 6; i++) {
                System.out.println("I'm producer .. producing item  " + i);
            }
        }
    }

    static class Consumer extends Thread {

        @Override
        public void run() {
            for (int i = 1; i < 6; i++) {
                System.out.println("I'm consumer .. Consuming item  " + i);
            }
        }
    }

}
