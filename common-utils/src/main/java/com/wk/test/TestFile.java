package com.wk.test;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class TestFile {
	public static void main(String[] args) throws Exception {
		File tempFile = File.createTempFile("temp", Long.toString(System.nanoTime()));
		if (!tempFile.delete()) {
			throw new Exception("创建临时文件夹，执行delete失败！");
		}
		if (!tempFile.mkdir()) {
			throw new Exception("创建临时文件夹，执行mkdir失败！");
		}
		File docFile = new File(tempFile.getAbsolutePath() + File.separatorChar + "test.txt");
		try {
			System.out.println("getAbsolutePath:" + tempFile.getAbsolutePath());
			System.out.println("getCanonicalPath:" + tempFile.getCanonicalPath());
			docFile.createNewFile();

			String txt = "这是测试内容啊！！！";
			OutputStream fos = new FileOutputStream(docFile);
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			bos.write(txt.getBytes());
			bos.close();

			BufferedReader br = new BufferedReader(new FileReader(docFile));
			StringBuffer singleDocStringBuffer = new StringBuffer();
			String line;
			while ((line = br.readLine()) != null) {
				singleDocStringBuffer.append(line).append("\n");
			}
			System.out.println("original text:" + singleDocStringBuffer.toString());
			System.out.println("getAbsolutePath:" + tempFile.getAbsolutePath());
			System.out.println("getCanonicalPath:" + tempFile.getCanonicalPath());

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			Files.deleteIfExists(tempFile.toPath());
		}
	}
}
