package com.mission.google.concurrency;

import java.util.function.IntConsumer;

public class LeetCodeConcurrency {

    /* https://leetcode.com/problems/print-zero-even-odd/ */
    class ZeroEvenOdd {
        private int n;
        private int sem = 0;
        public ZeroEvenOdd(int n) {
            this.n = n;
        }

        // printNumber.accept(x) outputs "x", where x is an integer.
        public synchronized void zero(IntConsumer printNumber) throws InterruptedException {
            for(int i = 0; i < n; ++i){
                while(sem != 0)
                    wait();

                printNumber.accept(0);
                sem = i%2 == 0 ? 1 : 2;
                notifyAll();
            }
        }

        public synchronized void even(IntConsumer printNumber) throws InterruptedException {
            for(int i = 2; i <= n; i += 2){
                while(sem != 2)
                    wait();

                printNumber.accept(i);
                sem = 0;
                notifyAll();
            }
        }

        public synchronized void odd(IntConsumer printNumber) throws InterruptedException {
            for(int i = 1; i <= n; i += 2){
                while(sem != 1)
                    wait();

                printNumber.accept(i);
                sem = 0;
                notifyAll();
            }
        }
    }
    /* https://leetcode.com/problems/print-in-order/ */
    class Foo {
        int flag;
        public Foo() {
            flag = 1;
        }

        public synchronized void first(Runnable printFirst) throws InterruptedException {
            while(flag != 1)
                wait();
            // printFirst.run() outputs "first". Do not change or remove this line.
            printFirst.run();
            flag = 2;
            notifyAll();
        }

        public synchronized void second(Runnable printSecond) throws InterruptedException {
            while(flag != 2)
                wait();
            // printSecond.run() outputs "second". Do not change or remove this line.
            printSecond.run();
            flag = 3;
            notifyAll();
        }

        public synchronized void third(Runnable printThird) throws InterruptedException {
            while(flag != 3)
                wait();
            // printThird.run() outputs "third". Do not change or remove this line.
            printThird.run();
            flag = 3;
            notifyAll();
        }
    }
}
