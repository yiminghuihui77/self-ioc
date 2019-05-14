package com.minghui.ioc.annotation;

/**
 * @author minghui.y BG358486
 * @create 2019-05-14 14:30
 **/
@SelfComponent("speakService")
public class SpeakService {
    public void speak() {
        System.out.println("I am speaking...");
    }
}
