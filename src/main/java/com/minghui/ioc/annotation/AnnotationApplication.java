package com.minghui.ioc.annotation;

import com.minghui.tools.ClassScanUtils;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 注解版上下文
 *
 * @author minghui.y BG358486
 * @create 2019-05-14 10:47
 **/
public class AnnotationApplication {

    private String packagePath;

    private ConcurrentHashMap<String, Object> beanMap;

    public AnnotationApplication(String packagePath) throws Exception {
        this.packagePath = packagePath;
        beanMap = new ConcurrentHashMap<String, Object>();
        //初始化
        initContext();
        //遍历所有的bean，为其属性赋值
        initAttribute();
    }

    private void initContext() throws Exception {
        //获取包路径下的所有Class
        List<Class<?>> classList = ClassScanUtils.getClasses(packagePath);
        //初始化beanMap
        for (Class<?> clazz : classList) {
            //获取类上的注解
            SelfComponent component = clazz.getAnnotation(SelfComponent.class);
            if (component != null) {
                //获取类名
                String className = clazz.getSimpleName();
                //获取注解上的属性
                String beanId = component.value();
                beanMap.put(StringUtils.isNotEmpty(beanId) ? beanId : toLowerCase(className), clazz.newInstance());
            }
        }

    }

    /**
     * 将类名首字母小写
     * @param className
     * @return
     */
    private String toLowerCase(String className) {
        if (Character.isLowerCase(className.charAt(0))) {
            return className;
        }
        return (new StringBuilder()).append(Character.toLowerCase(className.charAt(0))).append(className.substring(1)).toString();
    }

    /**
     * 根据beanId获取bean
     * @param beanId
     * @return
     * @throws Exception
     */
    public Object getBeanById(String beanId) throws Exception {
        if (StringUtils.isEmpty(beanId)) {
            throw new RuntimeException("入参beanId不能为空！");
        }
        if (beanMap.get(beanId) == null) {
            throw new RuntimeException("class not found!");
        }

        return beanMap.get(beanId);
    }

    /**
     * 为一个对象属性赋值
     */
    private void initAttribute() throws IllegalAccessException {
        for (Object object : beanMap.values()) {
            Class<?> clazz = object.getClass();
            //获取类的所有属性
            Field[] fields = clazz.getDeclaredFields();
            //遍历属性，为有SelfResource注解的属性赋值
            for (Field field : fields) {
                SelfResource selfResource = field.getAnnotation(SelfResource.class);
                if (selfResource != null) {
                    //获取属性名称
                    String attrName = field.getName();
                    //根据属性名称，从beanMap中查找bean
                    Object bean = beanMap.get(attrName);
                    //为当前对象的当前属性赋值
                    field.setAccessible(true);
                    field.set(object, bean);
                }
            }

        }
    }


}
