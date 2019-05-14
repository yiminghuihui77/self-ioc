package com.minghui.ioc.xml;

import java.util.HashMap;
import java.util.Map;

/**
 * @author minghui.y BG358486
 * @create 2019-05-13 16:58
 **/
public abstract class AbstractBeanDefinition implements SelfBeanDefinition {

    /**
     * bean的标识
     */
    private String id;
    /**
     * 类的全限定名
     */
    private String classPath;
    /**
     * 属性map
     */
    private Map<String, String> propertyMap = new HashMap<String, String>();

    public void setId(String id) {
        this.id = id;
    }

    public void setClassPath(String classPath) {
        this.classPath = classPath;
    }

    public String getId() {
        return id;
    }

    public String getClassPath() {
        return classPath;
    }

    public Map<String, String> getPropertyMap() {
        return propertyMap;
    }

    public void addProperty(String key, String value) {
        this.propertyMap.put(key, value);
    }
}
