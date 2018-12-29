package com.wk.test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.jsoup.Jsoup;

public class Test {
	
	/**
	 * @param root xml父节点
	 * @param xmlDataMap map
	 * @return xml数据，map结构
	 * @throws Exception 异常
	 */
	private static Map<String, String> getXmlData(Element root,Map<String, String> xmlDataMap) throws Exception {
		Iterator<Element> eleIt = root.elementIterator();
        while (eleIt.hasNext()) {
            Element element =  eleIt.next();
            if(element.isTextOnly()) {
            	xmlDataMap.put(element.getName().toLowerCase(), element.getTextTrim());
            } else {
            	getXmlData(element, xmlDataMap);
            }
        }
        return xmlDataMap;
	}
	   /**
     * 替换图片路径，并返回身份证号及姓名.
     * @param xmlFile xml文件
     * @param htmlText html文件
     * @param imgSrcDest 图片路径
     * @return 姓名及身份证号
     * @throws Exception 异常
     */
    public static Map<String, String> processCheckHtml(File xmlFile, String htmlText, String imgSrcDest, String charset) throws Exception {
        SAXReader reader = new SAXReader();
        org.dom4j.Document document = reader.read(xmlFile);
        
        Element root = document.getRootElement();
        Map<String, String> dataMap = new HashMap<>();
        Test.getXmlData(root, dataMap);
        dataMap.forEach((key,value) -> {System.out.println(key+":"+value);});
        
        org.jsoup.nodes.Document checkDoc = Jsoup.parse(htmlText);
        checkDoc.getElementById("result_id1").text(dataMap.get("id1"));
        checkDoc.getElementById("result_name1").text(dataMap.get("name1"));
        checkDoc.getElementById("result_issueoffice1").text(dataMap.get("issueoffice1"));
        checkDoc.getElementById("result_checkresult1").text(dataMap.get("checkresult1"));
        checkDoc.getElementById("result_photo1").attr("src", imgSrcDest);

        if (dataMap.get("querydate") != null) {
            checkDoc.getElementById("result_querydate1").text(dataMap.get("querydate"));
        }
        if (dataMap.get("queryusername") != null) {
            checkDoc.getElementById("result_queryname1").text(dataMap.get("queryusername"));
        }
        String checkResultHtmlPath = xmlFile.getParent() + File.separatorChar + "check_result.html";
        File htmlFile = new File(checkResultHtmlPath);
        if (htmlFile.exists()) {
            htmlFile.delete();
        }
        htmlFile.createNewFile();
        write(checkDoc.html(), htmlFile, charset);
        dataMap.put("checkResultHtmlPath", checkResultHtmlPath);
        return dataMap;
    }


    /**
     * 写入文本到文件.
     * @param text 文本
     * @param file 文件
     * @param charset 编码
     * @throws Exception 异常
     */
    private static void write(String text, File file, String charset) throws Exception {
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        IOUtils.write(text.getBytes(charset), writer, charset);
        //writer未实现autocloseable，必须手动关闭
        writer.close();
    }
    
    public static void main(String[] args) throws Exception {
      File xmlFile = new File("C:\\Users\\admin\\Desktop\\福州贷后管理征信查询\\35010519750218003X_1_0\\check.html");
      String htmlText ="<!doctype html><html><head><meta http-equiv='Content-Type' content='text/html;charset=UTF-8'><style> .style1{padding-left: 5px;	font-size: 12px;}.style2{	font-size: 30px;}body{	line-height: 12pt;	margin-top: 0px;	font-family: '??'; color: #000000;	margin-left: 0px;	font-size: 9pt;}table{	font-family: '??'; font-size: 9pt;}body{	font-family: '??'; font-size: 9pt;}td{	font-family: '??'; font-size: 9pt;}tr{	font-family: '??'; font-size: 9pt;}div{	font-family: '??'; font-size: 9pt;}</style>	</head>	<body>		<table class='style1' border='1' cellspacing='0' cellpadding='0' width='100%' bgcolor='#fafafa' align='center'><tbody><tr><td colspan='3'><div class='style2' align='center'>单笔核对结果</div></td></tr><tr><td height='25'>时间：</td><td id='result_querydate1'>2018-12-13 15:20:49</td><td rowspan='6'><img id='result_photo1' border='0' name='result_photo1' src='http://10.0.14.83/idcheck/photos/20181213/310110198210263595.jpg' width='125' height='180' _extended='true' /></img><//img></td></tr><tr><td height='25'>柜员：</td><td id='result_queryname1'>张勇</td></tr><tr><td height='25'>原录入姓名：</td><td><div id='result_name1' _extended='true'>李时珍</div></td></tr><tr><td height='25'>原录入身份证号码：</td><td><div id='result_id1' _extended='true'>110101199003073335</div></td></tr><tr><td height='25'>核对结果：</td><td><div id='result_checkresult1' _extended='true'>哈哈</div></td></tr><tr><td height='25'>签发机关：</td><td><div id='result_issueoffice1' _extended='true'>哈哈</div></td></tr></tbody></table>	</body></html>";
      String imgSrcDest = "aldsfaldsnflkasmdlfkmdsaf.jpg";
      Test.processCheckHtml(xmlFile,htmlText,imgSrcDest,"UTF-8");
  }
}
