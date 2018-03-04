package com.lh.myhandlerlib;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * 简易的消息队列
 * 没有延时操作等，这里只是实现其主要思路
 * 延时操作源码里面是通过当前时间与延迟的对比处理的，可自行查阅源码
 *
 * @author LuoHao
 *         Created on 2018/2/25 13:28
 */
public class MessageQueue {

    /**
     * 消息队列
     */
    private static final int COUNT = 50;

    /**
     * 使用阻塞队列BlockingQueue解决生产者消费者
     * <p>
     * 主要作用是效仿android源码native层的唤醒和堵塞消息队列的问题
     * android源码使用的native方法nativePollOnce（阻塞），nativeWake（唤醒），这里使用另一种java并发提供的阻塞队列类替换
     */
    private final BlockingQueue<Message> mMessageQueue;

    public MessageQueue() {
        mMessageQueue = new ArrayBlockingQueue<>(COUNT);
    }

    /**
     * enqueue a message
     * <p>
     * should be called in {@link Handler#sendMessage(Message)}
     *
     * @param msg message
     * @author LuoHao
     * Created on 2018/2/26 17:20
     */
    public void enqueueMessage(Message msg) {
        try {
            mMessageQueue.put(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取下一个Message对象
     * <p>
     * 消息循环时读取
     *
     * @author LuoHao
     * Created on 2018/2/25 14:33
     */
    public Message next() {
        try {
            return mMessageQueue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
