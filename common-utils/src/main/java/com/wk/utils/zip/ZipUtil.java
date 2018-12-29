package com.wk.utils.zip;

import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.FileHeader;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;

import org.apache.commons.io.IOUtils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * 压缩工具类
 */
public class ZipUtil {

	/**
	 * 主方法测试.
	 * @param args 参数
	 * @throws Exception 异常
	 */
	public static void main(String[] args) throws Exception {
		String zipFile = "d://test.zip";
		zipFile = "D:\\zxdownload\\test1.zip";
		processCreditFiles(zipFile, "baketechfin", "UTF-8");
		unZipWithPassword(zipFile, null,"baketechfin", "UTF-8");
		
		//测试新建一个带密码的zip
		ArrayList<File> files = new ArrayList<File>();
		File file = new File("d://test.txt");
		file.createNewFile();
		files.add(file);
		newZipFile("d://new.zip","123123123",files);
	}

	/**
	 * 获取一个临时文件夹.
	 * 
	 * @return 临时文件夹
	 * @throws Exception 异常
	 */
	public static String getNewTempFileDir() throws Exception {
		File createTempFileDir = File.createTempFile("temp", Long.toString(System.nanoTime()));
		if(!createTempFileDir.delete()) {
			throw new Exception("创建临时文件夹，执行delete失败！");
		}
		if(!createTempFileDir.mkdir()) {
			throw new Exception("创建临时文件夹，执行mkdir失败！");
		}
		return createTempFileDir.getAbsolutePath();
	}

	/**
	 * 解压带密码的zip文件到目录
	 * 
	 * @param zipFilePath zip文件路径
	 * @param password    密码
	 * @param charset     编码
	 * @return 解压目标根目标
	 * @throws Exception 异常
	 */
	public static String unZipWithPassword(String zipFilePath, String destFilePath, String password, String charset) throws Exception {
		File fileDir = new File(zipFilePath);
		if(!fileDir.exists()) {
			throw new Exception("zip源文件不存在！");
		}
		String fileDirDest = destFilePath;
		if(fileDirDest == null || "".equals(fileDirDest.trim()) || "".equalsIgnoreCase(fileDirDest.trim())){
			fileDirDest = getNewTempFileDir();
		}
		ZipFile zipFile = getZipFileWithPassword(zipFilePath, password, charset);
		zipFile.extractAll(fileDirDest);
		System.out.println("解压到:" + fileDirDest);
		return fileDirDest;
	}

	/**
	 * @param zipFilePath zip文件路径
	 * @param password 密码
	 * @param files 文件列表
	 * @return zip文件路径
	 * @throws Exception 异常
	 */
	public static String newZipFile(String zipFilePath, String password, ArrayList<File> files) throws Exception{
		try {
			// 创建zip文件
			ZipFile zipFile = new ZipFile(zipFilePath);
			// 初始化各类参数
			ZipParameters parameters = new ZipParameters();
			//设置压缩模式
			parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
			parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
			// 设置加密标志
			parameters.setEncryptFiles(true);
			// 设置aes加密
			parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_AES);
			parameters.setAesKeyStrength(Zip4jConstants.AES_STRENGTH_256);
			if(password !=null && !"".equals(password.trim()) && !"null".equalsIgnoreCase(password.trim())) {
				// 设置密码
				parameters.setPassword(password);
			}
			zipFile.addFiles(files, parameters);
			return zipFilePath;
		} catch (ZipException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 *
	 * @param zipFile zip对象
	 * @return 获取所有征信检查文件列表
	 * @throws Exception 异常
	 */
	protected static Map<String, List<String>> getAllFiles(ZipFile zipFile) throws Exception {
		try {
			long startTime = System.currentTimeMillis();
			Map<String, List<String>> allFiles = new LinkedHashMap<String, List<String>>();
			List<String> allFilesList = new ArrayList<String>();
			List<FileHeader> fileHeaderList = zipFile.getFileHeaders();
			if (fileHeaderList == null || fileHeaderList.isEmpty()) {
				throw new Exception("压缩包为空");
			}
			// 遍历读取文件列表
			for (int i = 0; i < fileHeaderList.size(); i++) {
				FileHeader fileHeader = fileHeaderList.get(i);
				if (fileHeader.isDirectory()) {
					allFiles.put(fileHeader.getFileName(), new ArrayList<String>());
				} else {
					allFilesList.add(fileHeader.getFileName());
				}
			}
			// 文件结构整理
			allFiles.forEach((key, value) -> {
				for (String filePathTemp : allFilesList) {
					if (filePathTemp.startsWith(key)) {
						allFiles.get(key).add(filePathTemp);
					}
				}
			});

			allFiles.forEach((key, value) -> {
				System.out.println(key + " : " + value.toString());
			});

			System.out.println("解压成功！耗时：" + (System.currentTimeMillis() - startTime) + "ms");
			return allFiles;
		} catch (Exception ex) {
			throw ex;
		}
	}

	/**
	 * 读取一个带密码的压缩文件.
	 * 
	 * @param zipFilePath 压缩文件
	 * @param password    密码
	 * @throws ZipException 异常
	 */
	public static ZipFile getZipFileWithPassword(String zipFilePath, String password, String charset)
			throws Exception {
		try {
			ZipFile zipFile = new ZipFile(zipFilePath);
			// 设置编码
			charset = (charset == null || "null".equalsIgnoreCase(charset.trim()) || charset.trim().length() == 0)
					? "UTF-8"
					: charset;
			// 设置编码格式
			zipFile.setFileNameCharset(charset);
			if (!zipFile.isValidZipFile()) {
				throw new ZipException("文件不合法或不存在");
			}
			// 检查是否需要密码
			if (zipFile.isEncrypted()) {
				zipFile.setPassword(password);
			}
			return zipFile;
		} catch (Exception ex) {
			throw ex;
		}
	}

	/**
	 * 处理征信文件，check.html图片路径替换,credit.html转pdf.
	 * @param zipFilePath zip文件路径
	 * @param password    zip文件密码
	 * @param charset     字节编码
	 * @throws Exception 异常
	 */
	public static void processCreditFiles(String zipFilePath, String password, String charset) throws Exception {
		try {
			// 设置编码
			charset = (charset == null || "null".equalsIgnoreCase(charset.trim()) || charset.trim().length() == 0)
					? "UTF-8"
					: charset;
			// 读取带密码的zip文件
			ZipFile zipFile = getZipFileWithPassword(zipFilePath, password, charset);
			// 获取所有文件列表
			Map<String, List<String>> allFiles = getAllFiles(zipFile);
			// 遍历处理文件夹列表
			for (Entry<String, List<String>> dirFiles : allFiles.entrySet()) {
				String dirPath = dirFiles.getKey();
				List<String> fileList = dirFiles.getValue();
				String checkHtmlFileName = dirPath + "check.html";
				String headJpgFileName = dirPath + "head.jpg";
				if (!fileList.contains(checkHtmlFileName) || !fileList.contains(headJpgFileName)) {
					// 若不存在check.html及head.jpg文件，则继续
					continue;
				}
				// 获取图片文件流对象
				InputStream headJpgInputStream = zipFile.getInputStream(zipFile.getFileHeader(headJpgFileName));
				// 转换为字节数组
				byte[] headJpgFileBytes = IOUtils.toByteArray(headJpgInputStream);
				// 上传dfs
				/*
				 * 
				 */
				// 获取图片文件流对象
				InputStream checkHtmlInputStream = zipFile.getInputStream(zipFile.getFileHeader(checkHtmlFileName));
				BufferedReader htmlReader = new BufferedReader(new InputStreamReader(checkHtmlInputStream, charset));
				// 单个文件文本
				StringBuffer singleDocStringBuffer = new StringBuffer();
				String line;
				while ((line = htmlReader.readLine()) != null) {
					singleDocStringBuffer.append(line).append("\n");
				}
				System.out.println("original text:\n" + singleDocStringBuffer.toString());
			}
		} catch (Exception ex) {
			throw ex;
		}
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
}