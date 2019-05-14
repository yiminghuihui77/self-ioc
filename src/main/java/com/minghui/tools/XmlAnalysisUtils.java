package com.minghui.tools;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

/**
 * xml文件解析工具
 *
 * @author minghui.y BG358486
 * @create 2019-05-10 10:30
 **/
public class XmlAnalysisUtils {


    public static void main(String[] args) throws DocumentException {
        //创建爱你SAX解析器
        SAXReader saxReader = new SAXReader();
        //读取XML资源文件
        Document document = saxReader.read(getXmlInputStream("student.xml"));
        //获取根节点
        Element root = document.getRootElement();
        //打印XML树
        printXmlTree(root);

    }

    /**
     * 根据文件路径，从项目路径获取字节输入流
     * 递归调用
     * @param xmlPath
     * @return
     */
    public static InputStream getXmlInputStream(String xmlPath) {
        return XmlAnalysisUtils.class.getClassLoader().getResourceAsStream(xmlPath);
    }

    public static void printXmlTree(Element root) {
        //打印当前节点
        System.out.println("节点名称：" + root.getName());

        List<Attribute> attributes = root.attributes();
        for (Attribute attribute : attributes) {
            System.out.println("属性：" + attribute.getName() + "--值：" + attribute.getValue());
        }

        String value = root.getTextTrim();
        if (StringUtils.isNotEmpty(value)) {
            System.out.println("value：" + value);
        }

        //遍历子节点
        Iterator<Element> iterator = root.elementIterator();
        while (iterator.hasNext()) {
            printXmlTree(iterator.next());
        }

    }
}
