package com.lh.producerconsumerlib;

import com.lh.producerconsumerlib.synchronizedImpl.ConsumerThread;
import com.lh.producerconsumerlib.synchronizedImpl.ProducerThread;
import com.lh.producerconsumerlib.synchronizedImpl.Resource;

/**
 * 生产者生产数据到缓冲区中，消费者从缓冲区中取数据。
 * 如果缓冲区已经满了，则生产者线程阻塞；
 * 如果缓冲区为空，那么消费者线程阻塞。
 *
 * @author LuoHao
 *         Created on 2018/3/3 17:12
 */
public class Test {

    public static void main(String[] args) {
        // 生产者-消费者模式的三种实现方式

        // 方式一：synchronized、wait和notify
        // 测试
        // 资源是公共的同一个资源容器对象
        Resource mResource = new Resource();

        ProducerThread p = new ProducerThread("pThread", mResource);
        ProducerThread p2 = new ProducerThread("pThread2", mResource);
        ProducerThread p3 = new ProducerThread("pThread3", mResource);

        ConsumerThread c = new ConsumerThread("cThread", mResource);
        ConsumerThread c2 = new ConsumerThread("cThread2", mResource);
        ConsumerThread c3 = new ConsumerThread("cThread3", mResource);

        p.start();
//        p2.start();
//        p3.start();
        c.start();
        c2.start();
        c3.start();
    }

}
