package com.smProject.util.excelUpload;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExcelRead {

	private static final Logger logger = LoggerFactory.getLogger(ExcelRead.class);

	public static List<Map<String, String>> read(ExcelReadOption excelReadOption) throws Exception {
		// 엑셀 파일 자체
		// 엑셀파일을 읽어 들인다.
		// FileType.getWorkbook() <-- 파일의 확장자에 따라서 적절하게 가져온다.
		Workbook wb = ExcelFileType.getWorkbook(excelReadOption.getFilePath());
		/**
		 * 엑셀 파일에서 첫번째 시트를 가지고 온다.
		 */
		Sheet sheet = wb.getSheetAt(0);

		/**
		 * sheet에서 유효한(데이터가 있는) 행의 개수를 가져온다.
		 */
		int numOfRows = sheet.getPhysicalNumberOfRows();
		int numOfCells = 0;

		logger.info("numOfRows :: {}", numOfRows);

		Row row = null;
		Cell cell = null;

		Map<String, String> map = null;

		List<Map<String, String>> result = new ArrayList<Map<String, String>>();

		/**
		 * 각 Row만큼 반복을 한다.
		 */
		logger.info("excelReadOption.getStartRow() - 1 :: {}", (excelReadOption.getStartRow() - 1));
		logger.info("(numOfRows - 1) :: {}", (numOfRows - 1));
		for (int rowIndex = excelReadOption.getStartRow() - 1; rowIndex < (numOfRows); rowIndex++) {
			logger.debug("rowIndex : {}", rowIndex);

			row = sheet.getRow(rowIndex);
			if (row == null) {
				logger.debug("ROW IS NULL");
				continue;
			}

			// 가져온 Row의 Cell의 개수 조회
			numOfCells = row.getPhysicalNumberOfCells();

			logger.info("excelReadOption.getOutputColumns().size():{}, numOfCells:{}",
					excelReadOption.getOutputColumns().size(), numOfCells);

			// if(excelReadOption.getOutputColumns().size() != numOfCells) {
			// throw new BizException("셀의 형태가 다릅니다.");
			// }

			map = new HashMap<String, String>();
			for (int cellIndex = 0; cellIndex < excelReadOption.getOutputColumns().size(); cellIndex++) {
				cell = row.getCell(cellIndex);
				logger.debug("{} :: {}", excelReadOption.getOutputColumns().get(cellIndex),
						ExcelCellRef.getValue(cell));
				map.put(excelReadOption.getOutputColumns().get(cellIndex), ExcelCellRef.getValue(cell));
			}

			result.add(map);
		}

		return result;
	}
}
