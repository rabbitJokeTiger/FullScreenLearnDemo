package com.lh.producerconsumerlib.synchronizedImpl;

/**
 * 消费者线程
 *
 * @author LuoHao
 *         Created on 2018/3/3 17:35
 */
public class ConsumerThread extends Thread {

    private Resource mResource;

    public ConsumerThread(Resource mResource) {
        this.mResource = mResource;
    }

    public ConsumerThread(String name, Resource mResource) {
        super(name);
        this.mResource = mResource;
    }

    @Override
    public void run() {
        // super.run()
        while (true) {
            // 不断的取走资源
            mResource.remove();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
