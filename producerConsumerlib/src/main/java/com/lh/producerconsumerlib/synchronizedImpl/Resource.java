package com.lh.producerconsumerlib.synchronizedImpl;

/**
 * 公共资源
 * <p>
 * 生产者生产数据到缓冲区中，消费者从缓冲区中取数据。
 * 如果缓冲区已经满了，则生产者线程阻塞；
 * 如果缓冲区为空，那么消费者线程阻塞。
 *
 * @author LuoHao
 *         Created on 2018/3/3 17:14
 */
public class Resource {
    /**
     * 当前资源数据
     */
    private int num = 0;

    /**
     * 资源池中允许存放的资源数目
     */
    private int size = 10;

    /**
     * 向资源池中添加资源
     */
    public synchronized void add() {
        if (num < size) {
            // 当前资源池中资源数未满
            num++;
            System.out.println("生产者：" + Thread.currentThread().getName() +
                    " 生产一件资源，当前资源有 " + num + " 个");

            // 通知等待的消费者
            notifyAll();
        } else {
            // 当前资源池中已有10个资源了
            try {
                // 生产者进入等待状态，并释放锁
                wait();
                System.out.println("生产者：" + Thread.currentThread().getName() +
                        " 线程进入等待状态，并释放了锁");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized void remove() {
        if (num > 0) {
            num--;
            System.out.println("消费者：" + Thread.currentThread().getName() +
                    " 消耗一件资源，当前线程池中有资源有 " + num + " 个");
            // 通知生产者生产资源
            notifyAll();
        } else {
            try {
                // 没有资源，消费者进入等待状态
                wait();
                System.out.println("消费者：" + Thread.currentThread().getName() +
                        " 线程进入等待状态");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
