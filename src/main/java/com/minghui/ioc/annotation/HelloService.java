package com.minghui.ioc.annotation;

/**
 * @author minghui.y BG358486
 * @create 2019-05-14 11:22
 **/
@SelfComponent("helloService")
public class HelloService {

    @SelfResource
    private SpeakService speakService;

    public HelloService() {
    }

    public HelloService(SpeakService speakService) {
        this.speakService= speakService;
    }

    public SpeakService getSpeakService() {
        return speakService;
    }

    public void setSpeakService(SpeakService speakService) {
        this.speakService = speakService;
    }

    public void sayHello() {
        System.out.println("hello!!!!");
    }

    public void speak() {
        speakService.speak();
    }
}
