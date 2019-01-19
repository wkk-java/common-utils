package com.wk.test;

import java.io.File;

public class TestFile {

	public static void main(String[] args) throws Exception {
		File file = new File("C:\\Users\\admin\\Desktop\\11.jpg");

		System.out.println(file.length());
		System.out.println(file.length() / 1024);
		System.out.println(1024 * 116);
	}

}
