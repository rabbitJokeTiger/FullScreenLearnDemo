package com.lh.myhandlerlib;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 手写android 底层Handler机制实现框架的测试
 *
 * @author LuoHao
 *         Created on 2018/2/26 16:27
 */
public class HandlerFrameTest {

    /**
     * 线程数量
     */
    private static final int THREAD_COUNT = 10;

    private static ThreadFactory namedThreadFactory = new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "common-thread-pool");
        }
    };

    /**
     * Common Thread Pool
     * <p>
     * 手动创建线程池 建议不直接使用Executors.newCachedThreadPool()
     */
    private static ExecutorService commonThreadPool = new ThreadPoolExecutor(5, 200,
            0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(1024),
            namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());

    private static ThreadFactory namedThreadFactory2 = new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r, "single-thread-pool");
        }
    };

    /**
     * Single Thread Pool
     * <p>
     * 手动创建线程池代替直接显示new Thread的问题
     */
    private static ExecutorService singleThreadPool = new ThreadPoolExecutor(1, 1
            , 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>(1024)
            , namedThreadFactory2, new ThreadPoolExecutor.AbortPolicy());

    public static void main(String[] args) {
        // 手写android 底层Handler机制实现框架
        Looper.prepare();

        final Handler myHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                System.out.println("handle msg: thread:" + Thread.currentThread().getId() +
                        " Msg: " + msg.toString());
            }
        };

        // 开启多线程
        singleThreadPool.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("singleThreadPool function thread name: " +
                        Thread.currentThread().getName());
                for (int i = 0; i < THREAD_COUNT; i++) {
                    commonThreadPool.execute(new Runnable() {
                        @Override
                        public void run() {
                            System.out.println("commonThreadPool function thread name: " +
                                    Thread.currentThread().getName());
                            Message msg = new Message(UUID.randomUUID().toString());
                            System.out.println("send Msg: thread: " + Thread.currentThread().getId() +
                                    " Msg: " + msg.toString());
                            myHandler.sendMessage(msg);
                        }
                    });
//                    try {
//                        Thread.sleep(1000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                }
            }
        });

        // new Thread().start()

        Looper.loop();

        // 异常
        throw new RuntimeException("Main thread loop unexpectedly exited");
    }

}
