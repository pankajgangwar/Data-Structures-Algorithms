package com.mission.google.concurrency;

import java.util.LinkedList;
import java.util.Queue;

public class ThreadDemo {

    public static void main(String[] args) {
        ProducerConsumer producerConsumer = new ProducerConsumer();

        Thread producer = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    producerConsumer.produce();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        Thread consumer = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    producerConsumer.consume();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        //producer.start();
       // consumer.start();

        try {
            producer.join();
            consumer.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        /*Thread.currentThread().setPriority(Thread.MAX_PRIORITY);
        YieldSample yieldDemo = new YieldSample();
        yieldDemo.setPriority(Thread.MIN_PRIORITY);
        yieldDemo.start();

        for (int i = 0; i < 5; i++) {
            System.out.println(Thread.currentThread().getName() + " in control");
        }*/

        ResourceLock lock = new ResourceLock();
        ThreadA threadA = new ThreadA(lock); // Prints 0
        ThreadB threadB = new ThreadB(lock); // Prints even
        ThreadC threadC = new ThreadC(lock); // Prints odd

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
                            System.out.print("0 \t");
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
                            System.out.print(++resourceLock.flag + "\t");

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
                            System.out.print(++resourceLock.flag + "\t");
                            System.out.println();
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

    public static class ProducerConsumer  {
        LinkedList<Integer> q = new LinkedList<>();
        int capacity = 2;
        public void produce() throws InterruptedException {
            int value = 0;
            while (true){
                synchronized (this){
                    while (q.size() ==  capacity){
                        wait();
                    }
                    System.out.println("Producer producing " + value);
                    q.offer(value++);
                    notify();
                    Thread.sleep(1000);
                }
            }
        }

        public void consume() throws InterruptedException {
            while (true){
                synchronized (this){
                    while (q.isEmpty()){
                        wait();
                    }
                    int consumed = q.pollFirst();
                    System.out.println("Consumer consumed item = " + consumed);
                    notify();
                    Thread.sleep(1000);
                }
            }
        }
    }

}
