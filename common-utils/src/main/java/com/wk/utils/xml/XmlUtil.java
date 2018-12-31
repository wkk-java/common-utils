package com.wk.utils.xml;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * xml工具类
 * @author wk
 *
 */
public class XmlUtil {
	
	/**
	 * @param xmlFilePath XML文件路径
	 * @return XML键值对
	 * @throws Exception 异常
	 */
	public static Map<String, String> getXmlData(String xmlFilePath) throws Exception {
		Map<String, String> xmlMapData = new HashMap<String, String>();
		SAXReader reader = new SAXReader();
        org.dom4j.Document document = reader.read(new File(xmlFilePath));
        Element root = document.getRootElement();
        getXmlData(root, xmlMapData);
        return xmlMapData;
	}
	
	/**
     * @param root XML父节点
     * @param xmlDataMap map
     * @return XML数据，map结构
     * @throws Exception 异常
     */
	private static Map<String, String> getXmlData(Element root, Map<String, String> xmlDataMap) {
        @SuppressWarnings("unchecked")
		Iterator<Element> eleIt = root.elementIterator();
        while (eleIt.hasNext()) {
            Element element =  eleIt.next();
            if (element.isTextOnly()) {
                //key转小写，便于后续取值容错
                xmlDataMap.put(element.getUniquePath().toLowerCase(), element.getTextTrim());
            } else {
                getXmlData(element, xmlDataMap);
            }
        }
        return xmlDataMap;
    }
    
    /**
     * @param args 参数
     * @throws Exception 异常
     */
    public static void main(String[] args) throws Exception{
    	String xmlFile = "C:\\Users\\admin\\Desktop\\暂存文档\\福州征信查询结果\\34082619750215004X_1_0\\check.html";
		Map<String, String> xmlMapData = XmlUtil.getXmlData(xmlFile);
		System.out.println(xmlMapData);
	}
    
}
