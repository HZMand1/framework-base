package com.base.utils.thread;

/**
 * TODO 进程抢占资源导致线程不安全
 * 线程ab 都在等待锁住资源的机会
 *
 * @version V1.0
 * @author: 黄芝民
 * @date: 2020/3/31 10:23
 * @copyright XXX Copyright (c) 2020
 */
public class DiedThread {
    private static String resource_a = "resource_a";
    private static String resource_b = "resource_b";

    public static void main(String[] args) {
        deadLock();
    }

    private static void deadLock(){
        Thread threadA = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (resource_a){
                    System.out.println("threadA get resource a");
                    try {
                        Thread.sleep(2000);
                        synchronized (resource_b){
                            System.out.println("threadA get resource b");
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        Thread threadB = new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (resource_b){
                    System.out.println("threadB get resource b");
                    synchronized (resource_a){
                        System.out.println("threadB get resource a");
                    }
                }
            }
        });

        threadA.start();
        threadB.start();
    }
}
