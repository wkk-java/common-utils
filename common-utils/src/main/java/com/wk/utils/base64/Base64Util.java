package com.wk.utils.base64;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Base64;

import org.apache.commons.io.IOUtils;

public class Base64Util {
	public static void main(String[] args) throws Exception{
		String response = IOUtils.toString(new FileInputStream(new File("C:\\Users\\admin\\Desktop\\新建文件夹//response.txt")));
		byte[] bytes = Base64.getDecoder().decode(response);
		IOUtils.write(bytes, new FileOutputStream(new File("d://response.xlsx")));
		
		String dt = LocalDate.now().toString();
		System.out.println(dt);
		System.out.println(LocalDateTime.now().toString());
	}
}
