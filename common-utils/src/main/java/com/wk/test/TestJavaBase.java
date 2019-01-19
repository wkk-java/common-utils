package com.wk.test;

import lombok.extern.log4j.Log4j;

@Log4j
public class TestJavaBase {
	public static void main(String[] args) throws Exception {
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
