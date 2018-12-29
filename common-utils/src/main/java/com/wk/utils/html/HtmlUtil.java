package com.wk.utils.html;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.List;

import javax.xml.soap.Node;

import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import lombok.extern.log4j.Log4j;

@Log4j
public class HtmlUtil {
	/**
	 * 复写HTML.
	 * 
	 * @param file    文件路径
	 * @param imgPath 图片路径
	 * @param charset 编码
	 * @throws Exception 异常
	 */
	private void rewriteHtml(String file, String imgPath, String charset) throws Exception {
		try {
			// 设置编码
			charset = (charset == null || "null".equalsIgnoreCase(charset.trim()) || charset.trim().length() == 0)
					? "UTF-8"
					: charset;
			BufferedReader reader = new BufferedReader(new FileReader(new File(file)));
			// 读取html文本
			String text = IOUtils.toString(reader);
			// 替换图片路径
			String htmlTxt = replaceHtmlImgSrc(text, imgPath);
			BufferedWriter writer = new BufferedWriter(new FileWriter(new File(file)));
			IOUtils.write(htmlTxt.getBytes(), writer, charset);
			// writer未实现autocloseable，必须手动关闭
			writer.close();
			writer.flush();
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			throw new Exception("复写HTML失败！", ex);
		}
	}

	/**
	 * 替换图片路径.
	 * 
	 * @param html       html文本
	 * @param imgSrcDest 目标图片引用路径
	 * @return html文本
	 */
	private static String replaceHtmlImgSrc(String html, String imgSrcDest) {
		Document doc = Jsoup.parse(html, "utf-8");
		Elements imgs = doc.getElementsByTag("img");
		// 遍历img元素
		for (Element img : imgs) {
			String src = img.attr("src");
			// 地址为空或不为图片
			if (src == null || "".equals(src.trim()) || "null".equalsIgnoreCase(src.trim())) {
				continue;
			}
			img.attr("src", imgSrcDest);
		}
		return doc.html();
	}

	public static String getHtmlText(String htmlFile, String charset) throws Exception {
		// 设置编码
		charset = (charset == null || "null".equalsIgnoreCase(charset.trim()) || charset.trim().length() == 0) ? "UTF-8"
				: charset;
		BufferedReader reader = new BufferedReader(new FileReader(new File(htmlFile)));
		// 读取html文本
		return IOUtils.toString(reader);
	}

	public static void main(String[] args) throws Exception{
		String html = "d:\\credit.html";
		Document doc = Jsoup.parse(getHtmlText(html, null), null);
		Elements tables = doc.getElementsByTag("table");
		org.jsoup.nodes.Node trs = tables.get(3).childNode(1).childNode(2);
		String name = trs.childNode(1).childNode(1).childNode(0).childNode(0).childNode(0).toString().trim();
		String idCard = trs.childNode(5).childNode(1).childNode(0).childNode(0).childNode(0).toString().trim();
		
		System.out.println(name);
		System.out.println(idCard);
		
		
		String checkHtml = "d:\\黄道远.html";
		Document checkDoc = Jsoup.parse(getHtmlText(checkHtml, null), null);
		String checkName = checkDoc.getElementById("result_name1").childNode(0).toString().trim();
		String checkidCard = checkDoc.getElementById("result_id1").childNode(0).toString().trim();
		System.out.println(checkName);
		System.out.println(checkidCard);
	}

}
