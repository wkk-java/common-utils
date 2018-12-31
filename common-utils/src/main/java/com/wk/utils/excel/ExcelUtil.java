package com.wk.utils.excel;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.CharArrayWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.GeneralSecurityException;

import org.apache.commons.io.IOUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;
import org.apache.poi.poifs.crypt.EncryptionInfo;
import org.apache.poi.poifs.crypt.EncryptionMode;
import org.apache.poi.poifs.crypt.Encryptor;
import org.apache.poi.poifs.crypt.HashAlgorithm;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtil {

	/**
	 * 文档名字
	 */
	private static String workbookName = ".xlsx";
	/**
	 * excel密码
	 */
	private static String excelPassword = "123";

	public static void encryptExcelXlsx() throws Exception {
		workbookName = File.createTempFile("征信任务", workbookName).getAbsolutePath();
		System.out.println(workbookName);
		// 构建XSSFWorkbook
		XSSFWorkbook hssfWorkbook = new XSSFWorkbook();
		XSSFSheet sheet1 = hssfWorkbook.createSheet("sheet1");
		XSSFRow row1 = sheet1.createRow(0);
		XSSFCell cell1 = row1.createCell(0);
		cell1.setCellValue("cell1");
		cell1.setCellType(CellType.STRING);
		XSSFCell cell2 = row1.createCell(1);
		cell2.setCellValue(2);
		cell2.setCellType(CellType.NUMERIC);

		// 保存此XSSFWorkbook对象为xlsx文件
		hssfWorkbook.write(new FileOutputStream(workbookName));

		POIFSFileSystem fs = new POIFSFileSystem();
		EncryptionInfo info = new EncryptionInfo(EncryptionMode.standard);
		// final EncryptionInfo info = new EncryptionInfo(EncryptionMode.agile,
		// CipherAlgorithm.aes256, HashAlgorithm.sha256, -1, -1, null);
		Encryptor enc = info.getEncryptor();

		// 设置密码
		enc.confirmPassword(excelPassword);

		// 加密文件
		OPCPackage opc = OPCPackage.open(new File(workbookName), PackageAccess.READ_WRITE);
		OutputStream os = enc.getDataStream(fs);
		opc.save(os);
		opc.close();

		// 把加密后的文件写回到流
		FileOutputStream fos = new FileOutputStream(workbookName);
		fs.writeFilesystem(fos);
		fos.close();
	}

	public static void decryptExcelXlsx() throws IOException {
		Workbook wb = null;
		FileInputStream in = null;
		try {
			in = new FileInputStream(workbookName);// 读取xlsx文件
			wb = WorkbookFactory.create(in, excelPassword);// 设置密码打开
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			in.close();
		}
		System.out.println("=================================");
		System.out.println("Number of Sheets:" + wb.getNumberOfSheets());
		System.out.println("Sheet3's name:" + wb.getSheetName(0));
		System.out.println();
	}

	private static byte[] createExcelWithPassword(String excelPassword)
			throws IOException, InvalidFormatException, GeneralSecurityException, FileNotFoundException {
		XSSFWorkbook wb = new XSSFWorkbook();
		wb.setWorkbookPassword(excelPassword, HashAlgorithm.sha512);
		wb.setRevisionsPassword(excelPassword, HashAlgorithm.sha512);
		XSSFSheet sheet1 = wb.createSheet("sheet1");
		// title
		Row row0 = sheet1.createRow(0);
		row0.createCell(0).setCellValue("姓名");
		row0.createCell(1).setCellValue("证件类型");
		row0.createCell(2).setCellValue("证件号码");
		row0.createCell(3).setCellValue("姓名类型");
		row0.createCell(4).setCellValue("查询原因");

		Row row1 = sheet1.createRow(1);
		row1.createCell(0).setCellValue("name");
		row1.createCell(1).setCellValue("类型");
		row1.createCell(2).setCellValue("号码");
		row1.createCell(3).setCellValue("类型");
		row1.createCell(4).setCellValue("原因");

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		wb.write(baos);
		wb.close();
		baos.close();

		POIFSFileSystem fs = new POIFSFileSystem();
		EncryptionInfo info = new EncryptionInfo(EncryptionMode.standard);
		// final EncryptionInfo info = new EncryptionInfo(EncryptionMode.agile,
		// CipherAlgorithm.aes256, HashAlgorithm.sha256, -1, -1, null);
		Encryptor enc = info.getEncryptor();
		// 设置密码
		enc.confirmPassword(excelPassword);

		InputStream is = new ByteArrayInputStream(baos.toByteArray());

		// 加密文件
		OPCPackage opc = OPCPackage.open(is);
		OutputStream os = enc.getDataStream(fs);
		opc.save(os);
		opc.close();

		// 把加密后的文件写回到流
//		FileOutputStream fileOutStream = new FileOutputStream("d:\\test1.xlsx");
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		fs.writeFilesystem(outStream);
		outStream.close();
		return outStream.toByteArray();
	}

	public static void main(String[] args) throws Exception {
//		encryptExcelXlsx();
//
//		decryptExcelXlsx();
//		System.out.println(File.createTempFile("征信任务", TEST_WORKBOOK_NAME).getAbsolutePath());

		String file = "D:\\黄道远.html";

		try {
			BufferedReader reader = new BufferedReader(new FileReader(new File(file)));
			String text = IOUtils.toString(reader);
			System.out.println(text);

			String htmlTxt = "12312121231233";
			System.out.println(htmlTxt);
			// 内存流, 作为临时流
			CharArrayWriter tempStream = new CharArrayWriter();
			tempStream.append(htmlTxt);
			FileWriter out = new FileWriter(new File(file + ".bak11"));
			tempStream.writeTo(out);
//	        out.close();  
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}