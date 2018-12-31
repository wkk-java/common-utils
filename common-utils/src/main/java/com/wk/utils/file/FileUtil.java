package com.wk.utils.file;

import java.io.BufferedInputStream;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * 文件工具
 * 
 * @author wk
 *
 */
public class FileUtil {

	/**
	 * 获取一个临时文件夹.
	 * 
	 * @return 临时文件夹
	 * @throws Exception 异常
	 */
	public static File getNewTempFileDir(String prefixName) throws Exception {
		Path tmpPath = Files.createTempDirectory(prefixName);
		return tmpPath.toFile();
	}

	/**
	 * @param fileDir 文件夹
	 */
	public static void cleanDirectory(File fileDir) {
		if(fileDir == null || !fileDir.exists()) {
			return;
		}
		if(fileDir.isDirectory() && fileDir.list().length > 0) {
			//遍历下层文件(夹)删除
			final File[] files = fileDir.listFiles();
			for(File file : files) {
				//递归删除，删除文件夹内文件，最后删除空文件夹及单文件
				cleanDirectory(file);
			}
		}
		fileDir.delete();
	}

	/**
	 * 获取编码.
	 * 
	 * @param bis
	 * @return 编码
	 * @throws Exception 异常
	 */
	private static String getCharset(BufferedInputStream bis) throws Exception {
		int p = (bis.read() << 8) + bis.read();
		bis.close();
		String code = null;
		switch (p) {
		case 0xefbb:
			code = "UTF-8";
			break;
		case 0xfffe:
			code = "Unicode";
			break;
		case 0xfeff:
			code = "UTF-16BE";
			break;
		default:
			code = "GBK";
		}
		return code;
	}
	
	/**
	 * 测试主方法
	 * @param args 参数
	 * @throws Exception 异常
	 */
	public static void main(String[] args) throws Exception {
//		File fileDir = new File("D:\\000");
//		System.out.println(fileDir.list().length);
//		FileUtil.cleanDirectory(fileDir);
		File file = FileUtil.getNewTempFileDir(null);
		System.out.println(file.getAbsolutePath());
	}
}
