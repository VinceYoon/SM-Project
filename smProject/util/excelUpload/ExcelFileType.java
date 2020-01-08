package com.smProject.util.excelUpload;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.smProject.util.exception.BizException;



public class ExcelFileType {

	/*
     * FileInputStream은 파일의 경로에 있는 파일을
     * 읽어서 Byte로 가져온다.
     */
	public static Workbook getWorkbook(String filePath) {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(filePath);
		} catch (FileNotFoundException e) {
			throw new BizException("엑셀 파일이 없습니다.");
		}

	    Workbook wb = null;

        /*
         * 파일의 확장자를 체크해서 .XLS 라면 HSSFWorkbook에
         * .XLSX라면 XSSFWorkbook에 각각 초기화 한다.
         */
		try {
			if (filePath.toUpperCase().endsWith(".XLS")) {
				wb = new HSSFWorkbook(fis);

			} else if (filePath.toUpperCase().endsWith(".XLSX")) {
				wb = new XSSFWorkbook(fis);
			}

		} catch (Exception e) {
			throw new BizException("파일 확장자 처리중 오류 발생");
		}

		return wb;
	}

}
