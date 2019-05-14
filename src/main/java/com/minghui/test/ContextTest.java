package com.minghui.test;

import com.minghui.ioc.annotation.AnnotationApplication;
import com.minghui.ioc.annotation.HelloService;
import com.minghui.ioc.xml.SelfXmlApplication;
import com.minghui.model.Student;

/**
 * xml文件解析测试
 *
 * @author minghui.y BG358486
 * @create 2019-05-10 10:27
 **/
class ContextTest {

    public static void main(String[] args) throws Exception {
        //XML版测试
//        SelfXmlApplication application =  new SelfXmlApplication("student.xml");
//        Student student1 = (Student) application.getBeanById("zhangsan");
//        System.out.println(student1);

        //注解版测试
        AnnotationApplication annotationApplication = new AnnotationApplication("com.minghui");
        HelloService helloService = (HelloService) annotationApplication.getBeanById("helloService");
        helloService.sayHello();
        helloService.speak();
    }
}
