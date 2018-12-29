package com.wk.utils.pdf;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.html2pdf.attach.impl.DefaultTagWorkerFactory;
import com.itextpdf.html2pdf.css.apply.impl.DefaultCssApplierFactory;
import com.itextpdf.html2pdf.resolver.font.DefaultFontProvider;
import com.itextpdf.layout.font.FontProvider;

import lombok.extern.log4j.Log4j;

/**
 * html转PDF.
 * 
 * @author admin
 *
 */
@Log4j
public class PdfUtil {

	/**
	 * 测试主方法.
	 * 
	 * @param args 参数
	 * @throws Exception 异常
	 */
	public static void main(String[] args) throws Exception{
		String file = "C:\\Users\\admin\\AppData\\Local\\Temp\\temp2648125457475979090523743234113996\\350426196509160019_1_0\\credit.html";
//		file = file.replaceAll("\\", File.separatorChar);
		PdfUtil.html2Pdf(file, null);
		System.out.println("done");
    }

	/**
	 * html文件转换为pdf文件.
	 * 
	 * @param htmlFilePath html路径
	 * @param charset      编码(非必须指定)
	 * @return pdf文件路径
	 * @throws Exception 异常
	 */
	private static String html2Pdf(String htmlFilePath, String charset) throws Exception {
		File htmlSource = new File(htmlFilePath);
		if (!htmlSource.exists()) {
			throw new Exception("html源文件不存在！");
		}
		if (!htmlSource.isFile()) {
			throw new Exception("html源文件非合法文件！");
		}
		String pdfDestPath = String.format("%scredit.pdf", htmlSource.getParent() + File.separatorChar);
		// pdf目标文件
		File pdfDest = new File(pdfDestPath);
		if (pdfDest.exists()) {
			pdfDest.delete();
		}
		ConverterProperties converterProperties = new ConverterProperties();
		// 加载本地图片时有效
		converterProperties.setBaseUri(htmlSource.getParent());
		// Custom HTML parsing
		converterProperties.setTagWorkerFactory(DefaultTagWorkerFactory.getInstance()); 
		// Custom CSS parsing
		converterProperties.setCssApplierFactory(DefaultCssApplierFactory.getInstance());
		//设置编码
		if (charset != null && !"".equals(charset) && !"null".equalsIgnoreCase(charset)) {
			converterProperties.setCharset(charset);
		}
		// 加载字体
		FontProvider dfp = new DefaultFontProvider(true, true, true);
		//设置字体，防止中文显示出错
		converterProperties.setFontProvider(dfp);
		HtmlConverter.convertToPdf(new FileInputStream(htmlSource), new FileOutputStream(pdfDest), converterProperties);
		return pdfDestPath;
	}
	
}