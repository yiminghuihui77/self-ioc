package com.minghui.ioc.annotation;

import java.lang.annotation.*;

/**
 * 组件注解
 *
 * @author minghui.y BG358486
 * @create 2019-05-14 10:54
 **/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface SelfComponent {
    String value();
}
