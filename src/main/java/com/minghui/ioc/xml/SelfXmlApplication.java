package com.minghui.ioc.xml;

import com.minghui.exception.BeanNotDefineException;
import com.minghui.tools.XmlAnalysisUtils;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Xml上下文
 *
 * @author minghui.y BG358486
 * @create 2019-05-10 13:51
 **/
public class SelfXmlApplication {

    private String xmlPath;

    private ConcurrentHashMap<String, SelfBeanDefinition> map = new ConcurrentHashMap<String, SelfBeanDefinition>();

    public SelfXmlApplication(String xmlPath) throws DocumentException {
        this.xmlPath = xmlPath;
        analysisXmlFile();
    }

    /**
     * 解析XML文件
     * @throws DocumentException
     */
    private void analysisXmlFile() throws DocumentException {
        //创建SAX解析器
        SAXReader saxReader = new SAXReader();
        //读取XML资源文件
        Document document = saxReader.read(XmlAnalysisUtils.getXmlInputStream(xmlPath));
        //获取根节点
        Element root = document.getRootElement();
        Iterator<Element> iterable = root.elementIterator();
        //遍历子节点
        while (iterable.hasNext()) {
            Element element = iterable.next();
           DefaultBeanDefinition beanDefinition = new DefaultBeanDefinition();
            //遍历属性，获取id、class
            List<Attribute> attributes = element.attributes();
            for (Attribute attribute : attributes) {
                if ("id".equals(attribute.getName())) {
                    beanDefinition.setId(attribute.getValue());
                }
                if ("class".equals(attribute.getName())) {
                    beanDefinition.setClassPath(attribute.getValue());
                }
            }
            //遍历子元素，获取属性name、age、sex、address
            Iterator<Element> subIterator = element.elementIterator();
            while (subIterator.hasNext()) {
                Element subElement = subIterator.next();
                beanDefinition.addProperty(subElement.getName(), subElement.getTextTrim());
            }
            //存储BeanDefinition
            map.put(beanDefinition.getId(), beanDefinition);
        }
    }

    /**
     * 通过bean id 获取实例
     * @param id
     * @return
     * @throws Exception
     */
    public Object getBeanById(String id) throws Exception {
        SelfBeanDefinition beanDefinition = map.get(id);
        if (beanDefinition == null) {
           throw new BeanNotDefineException("XML文件中未定义该bean:" + id);
        }
        //获取类的全限定名
        String classPath = beanDefinition.getClassPath();
        Map<String, String> propertyMap = ((DefaultBeanDefinition) beanDefinition).getPropertyMap();
        //反射生成实例
        Class<?> clazz = Class.forName(classPath);
        Object object = clazz.newInstance();

        for (String key : propertyMap.keySet()) {
            //根据属性名生成Field
            Field field = clazz.getDeclaredField(key);
            //忽略访问限制
            field.setAccessible(true);
            //获取属性的类型
            System.out.println("属性名："+ key + "---类型：" + field.getType());
            //这里存在一个问题：map存的是String的值，当类的属性非String时，报错
            field.set(object, propertyMap.get(key));
        }

        return object;
    }
}
