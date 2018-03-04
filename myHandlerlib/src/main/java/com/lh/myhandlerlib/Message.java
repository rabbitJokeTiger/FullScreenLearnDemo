package com.lh.myhandlerlib;

/**
 * 消息Message
 *
 * @author LuoHao
 *         Created on 2018/2/25 13:28
 */

public class Message {
    /**
     * 需要发送的Handler
     */
    public Handler target;

    public String what;

    public Message(String what) {
        this.what = what;
    }

    @Override
    public String toString() {
        return "what: " + what;
    }
}
