package utilities;

import java.io.FileInputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelSheetReader {
	private Sheet sheet;
	private Row row;

	public ExcelSheetReader(String workbookName, String sheetName, int rowNumber) {
		try (FileInputStream fis = new FileInputStream(TestConstants.DATA_FILE_PATH + workbookName + ".xlsx")) {
			Workbook workbook = WorkbookFactory.create(fis);
			sheet = workbook.getSheet(sheetName);
			row = sheet.getRow(rowNumber);
			if (row == null) {
				throw new IllegalArgumentException("Row number " + rowNumber + " does not exist in the sheet.");
			}
		} catch (IOException e) {
			throw new RuntimeException("Failed to read Excel workbook.", e);
		}
	}

	public String readCell(int columnIndex) {
		Cell cell = row.getCell(columnIndex);
		if (cell == null) {
			return "";
		}
		return cell.toString();
	}

	private int getCellIndex(String columnName) {
		Row firstRow = sheet.getRow(0);
		if (firstRow == null) {
			throw new IllegalStateException("The first row of the sheet is empty.");
		}
		for (int i = 0; i < firstRow.getLastCellNum(); i++) {
			Cell cell = firstRow.getCell(i);
			if (cell == null) {
				continue;
			}
			String value = cell.toString();
			if (value.equals(columnName)) {
				return i;
			}
		}
		throw new IllegalArgumentException("Column " + columnName + " does not exist in the sheet.");
	}

	public String readCell(String columnName) {
		int columnIndex = getCellIndex(columnName);
		return readCell(columnIndex);
	}

}
