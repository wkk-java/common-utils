package com.wk.test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import org.apache.commons.io.IOUtils;
import org.dom4j.Document;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;

import lombok.extern.log4j.Log4j;

@Log4j
public class TestJavaBase {
	public static void main(String[] args) throws Exception {
//		String checkHtmlFile = "d://黄道远.html";
//		Document checkDoc = Jsoup.parse(new File(checkHtmlFile), "gbk");
//        String checkName = checkDoc.getElementById("result_name1").text().trim();
//        String checkidCard = checkDoc.getElementById("result_id1").text().trim();
//        System.out.println("checkName:" + checkName);
//        System.out.println("checkidCard:" + checkidCard);

		String xmlFile = "D:\\350102196105280391_1_0\\check.html";

		SAXReader reader = new SAXReader();
		Document document = reader.read(new File(xmlFile));
		String id = document.selectSingleNode("/CFX/MSG/ID1").getText();
		String name = document.selectSingleNode("/CFX/MSG/Name1").getText();
		Node photoNode = document.selectSingleNode("/CFX/MSG/Photo1");
		photoNode.setText("123123123.jpg");
		String xmltext = document.asXML();
		System.out.println(xmltext);
		BufferedWriter writer = new BufferedWriter(new FileWriter(xmlFile));
		IOUtils.write(xmltext, writer);
		writer.close();
		System.out.println(id);
		System.out.println(name);

//		TestJavaBase.overWriteXml(new File(xmlFile), "photo1", "123123.jpgj");
//		Document checkDoc = Jsoup.parse(new File(xmlFile), null);
//		String id = checkDoc.select("ID1").text().trim();
//		String name = checkDoc.select("Name1").text().trim();
//		String photo = checkDoc.select("Photo1").text().trim();
//		System.out.println(id);
//		System.out.println(name);
//		System.out.println(photo);
//		System.out.println(checkDoc.getElementsByTag("body").html());
	}

//	public static void overWriteXml(File xmlFile, String imgTag, String imgPath) throws Exception {
//        try {
//            Document checkDoc = Jsoup.parse(xmlFile, null);
//            checkDoc.attr(imgTag, imgPath);
//            //转换过程中会自动添加body等
//            String xmlText = checkDoc.getElementsByTag("body").html();
//            BufferedWriter writer = new BufferedWriter(new FileWriter(xmlFile));
//            IOUtils.write(xmlText.getBytes(), writer);
//            //writer未实现autocloseable，必须手动关闭
//            writer.close();
//        } catch (Exception ex) {
//            log.error(ex.getMessage(), ex);
//            throw new Exception("复写HTML失败！", ex);
//        }
//    }
}
