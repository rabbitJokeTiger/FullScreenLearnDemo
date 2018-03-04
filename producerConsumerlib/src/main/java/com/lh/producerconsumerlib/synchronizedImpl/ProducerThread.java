package com.lh.producerconsumerlib.synchronizedImpl;

/**
 * 生产者线程
 *
 * @author LuoHao
 *         Created on 2018/3/3 17:31
 */
public class ProducerThread extends Thread {
    private Resource mResource;

    public ProducerThread(Resource mResource) {
        this.mResource = mResource;
    }

    public ProducerThread(String name, Resource mResource) {
        super(name);
        this.mResource = mResource;
    }

    @Override
    public void run() {
        // super.run()
        while (true) {
            // 不断的生产资源
            try {
                mResource.add();
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
