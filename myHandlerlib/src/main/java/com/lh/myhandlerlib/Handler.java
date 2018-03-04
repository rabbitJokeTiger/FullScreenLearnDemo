package com.lh.myhandlerlib;

/**
 * 简易的消息处理Handler
 * <p>
 * 手写android 底层Handler机制实现框架
 *
 * @author LuoHao
 *         Created on 2018/2/25 13:28
 */
public class Handler {

    /**
     * 消息队列
     */
    private final MessageQueue mQueue;

    /**
     * 消息循环
     */
    private final Looper mLooper;

    public Handler() {
        this(Looper.myLooper());
    }

    public Handler(Looper looper) {
        mLooper = looper;
        // 保证MessageQueue与Looper对应性
        mQueue = mLooper.mQueue;
    }

    /**
     * 发送消息
     *
     * @param msg message
     * @author LuoHao
     * Created on 2018/2/26 16:40
     */
    public final void sendMessage(Message msg) {
        // target是Handler本身
        // 多线程中保证Handler的一致性，并且这句是在主线程中调用的，
        // 当其调用自身的handleMessage()时才会有线程切换的效果
        msg.target = this;

        // enqueue a message
        mQueue.enqueueMessage(msg);
    }

    /**
     * 复写该方法 处理消息
     *
     * @author LuoHao
     * Created on 2018/2/26 16:44
     */
    public void handleMessage(Message msg) {

    }

    /**
     * 面向对象设计模式
     * 责任链调度原则
     *
     * @param msg message
     */
    public void dispatchMessage(Message msg) {
        handleMessage(msg);
    }
}
