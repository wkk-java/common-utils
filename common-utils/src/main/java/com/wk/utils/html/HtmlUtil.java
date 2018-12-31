package com.wk.utils.html;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import lombok.extern.log4j.Log4j;

/**
 * @author wk
 *
 */
@Log4j
public class HtmlUtil {
	/**
	 * 复写HTML.
	 * 
	 * @param htmlFile    文件
	 * @param htmlIdAndValue html元素id及对应新值
	 * @param charset 编码
	 * @throws Exception 异常
	 */
	private void reWriteHtml(File htmlFile, Map<String, String> htmlIdAndValue, String charset) throws Exception{
		try {
			Document document = Jsoup.parse(htmlFile, charset);
			//遍历设置根据id查找元素并设置新值
			htmlIdAndValue.forEach((id, value) -> {
				Element element = document.getElementById(id);
				if(element.tag().isKnownTag()) {
					String tagName = element.tagName().toLowerCase();
					if("img".equals(tagName)) {
						element.attr("src", value);
					} else {
						element.val(value);
						element.text(value);
					}
				}
			});
			BufferedWriter writer = new BufferedWriter(new FileWriter(htmlFile));
			IOUtils.write(document.html().getBytes(), writer, charset);
			// writer未实现autocloseable，必须手动关闭
			writer.close();
			writer.flush();
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			throw new Exception("复写HTML失败！", ex);
		}
	}

	/**
	 * @param args 参数
	 * @throws Exception 异常
	 */
	public static void main(String[] args) throws Exception{
		String checkHtml = "d:\\黄道远.html";
		Document checkDoc = Jsoup.parse(new File(checkHtml), null);
		String checkName = checkDoc.getElementById("result_name1").childNode(0).toString().trim();
		String checkidCard = checkDoc.getElementById("result_id1").childNode(0).toString().trim();
		System.out.println(checkName);
		System.out.println(checkidCard);
	}

}
