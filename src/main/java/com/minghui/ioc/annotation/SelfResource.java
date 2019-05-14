package com.minghui.ioc.annotation;

import java.lang.annotation.*;

/**
 * 自定义注入注解
 *
 * @author minghui.y BG358486
 * @create 2019-05-14 14:28
 **/
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface SelfResource {
}
