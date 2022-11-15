package com.xjh.jvm.study;

import java.util.concurrent.TimeUnit;

/**
 * @description: TODO
 * @author: Mr.Xu
 * @createDate: 2022-11-12 17:22
 * @version: 1.0
 */
public class ThreadDeadLock {

    public static void main(String[] args) throws InterruptedException {

        testDeadLock1 testDeadLock1 = new testDeadLock1();
        testDeadLock2 testDeadLock2 = new testDeadLock2();
        Thread thread1 = new Thread(()->{
            try {
                synchronized (testDeadLock1){
                    testDeadLock1.lockOne();
                    System.out.println("线程一加锁one，准备加锁two");
                    TimeUnit.SECONDS.sleep(1);

                    synchronized (testDeadLock2){
                        testDeadLock1.lockOne();
                        System.out.println("线程一加锁two");
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"threadLockOne");



        Thread thread2 = new Thread(()->{
            try {
                synchronized (testDeadLock2){
                    testDeadLock2.lockTwo();
                    System.out.println("线程二加锁two，准备加锁one");
                    TimeUnit.SECONDS.sleep(1);

                    synchronized (testDeadLock1){
                        testDeadLock1.lockOne();
                        System.out.println("线程二加锁one");
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"threadLockTwo");

        thread1.start();
        thread2.start();

        TimeUnit.SECONDS.sleep(5);

        //发现还未执行完成，进入死锁，使用 jstack -l [pid] 查看死锁线程在哪里发生
        //    -F  to force a thread dump. Use when jstack <pid> does not respond (process is hung)：线程挂起使用
        //    -m  to print both java and native frames (mixed mode)：查看jvm帧和本地方法帧
        //    -l  long listing. Prints additional information about locks：查看死锁线程信息
        System.out.println("执行结束");
    }




}

class testDeadLock1{
    public void lockOne() throws InterruptedException {
        String name = Thread.currentThread().getName();
        System.out.println("当前线程name = " + name + "已进入lockOne方法");
    }
}

class testDeadLock2{
    public void lockTwo() throws InterruptedException {
        String name = Thread.currentThread().getName();
        System.out.println("当前线程name = " + name + "已进入lockTwo方法");
    }
}