package com.lh.myhandlerlib;

/**
 * 简易的消息循环Looper
 *
 * @author LuoHao
 *         Created on 2018/2/25 13:28
 */
public class Looper {

    /**
     * 消息队列
     */
    public MessageQueue mQueue;

    /**
     * ThreadLocal类用来提供线程内部的局部变量。
     * 这种变量在多线程环境下访问(通过get或set方法访问)时能保证各个线程里的变量相对独立于其他线程内的变量
     * 提供线程内部的局部变量，在本线程内随时随地可取，隔离其他线程
     * <p>
     * ThreadLocal解决多线程的并发问题
     * 每个线程维护一个 ThreadLocalMap 的映射表，
     * 映射表的 key 是 ThreadLocal 实例本身，value 是要存储的副本变量。
     * ThreadLocal 实例本身并不存储值，它只是提供一个在当前线程中找到副本值的 key
     * <p>
     * 每个Thread维护一个ThreadLocalMap映射表，这个映射表的key是ThreadLocal实例本身，
     * value是真正需要存储的Object
     */
    private static final ThreadLocal<Looper> sThreadLocal = new ThreadLocal<>();

    private Looper() {
        mQueue = new MessageQueue();
    }

    /**
     * Lopper 初始化
     *
     * @author LuoHao
     * Created on 2018/2/25 14:04
     */
    public static void prepare() {
        if (sThreadLocal.get() != null) {
            sThreadLocal.remove();
            throw new RuntimeException("Only one Looper may be created per Thread");
        }
        sThreadLocal.set(new Looper());
    }

    public static Looper myLooper() {
        return sThreadLocal.get();
    }

    /**
     * 提供系统动力，让消息队列开始循环工作
     *
     * @author LuoHao
     * Created on 2018/2/25 14:07
     */
    public static void loop() {
        final Looper me = myLooper();
        if (me == null) {
            sThreadLocal.remove();
            throw new RuntimeException("No Looper; Looper.prepare() wasn't called on this Thread");
        }
        final MessageQueue queue = me.mQueue;
        // 死循环开始遍历消息并分发
        for (; ; ) {
            // might block
            Message msg = queue.next();
            if (msg == null) {
                // No message indicates that the message queue is quitting.
                System.out.println(" No message indicates that the message queue is quitting.");
                return;
            }
            msg.target.dispatchMessage(msg);
        }
    }
}
