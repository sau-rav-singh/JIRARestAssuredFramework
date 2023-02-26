package utilities;

import java.io.FileInputStream;
import java.io.IOException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * A class that can read data from an Excel sheet given the workbook name, sheet
 * name and row number.
 */
public class ExcelSheetReader {
	private Sheet sheet;
	private Row row;

	// A constant for the file extension
	private static final String FILE_EXTENSION = ".xlsx";

	/**
	 * Constructs an ExcelSheetReader object and reads the specified row from the
	 * sheet.
	 * 
	 * @param workbookName The name of the workbook without extension
	 * @param sheetName    The name of the sheet
	 * @param rowNumber    The number of the row to read (zero-based)
	 * @throws RuntimeException         If there is an error in reading the workbook
	 * @throws IllegalArgumentException If the row number or column name does not
	 *                                  exist in the sheet
	 */
	public ExcelSheetReader(String workbookName, String sheetName, int rowNumber) {
		try (FileInputStream fileInputStream = new FileInputStream(
				TestConstants.DATA_FILE_PATH + workbookName + FILE_EXTENSION)) {
			Workbook workbook = WorkbookFactory.create(fileInputStream);
			sheet = workbook.getSheet(sheetName);
			row = sheet.getRow(rowNumber);
			if (row == null) {
				throw new IllegalArgumentException("Row number " + rowNumber + " does not exist in the sheet.");
			}
		} catch (IOException e) {
			throw new RuntimeException("Failed to read Excel workbook.", e);
		}
	}

	/**
	 * Reads a cell value from the specified column index.
	 * 
	 * @param columnIndex The index of the column to read (zero-based)
	 * @return The cell value as a string, or an empty string if the cell is null
	 */
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
		for (Cell cell : firstRow) {
			if (cell == null) {
				continue;
			}
			String value = cell.toString();
			if (value.equals(columnName)) {
				return cell.getColumnIndex();
			}
		}
		throw new IllegalArgumentException("Column " + columnName + " does not exist in the sheet.");
	}

	public String readCell(String columnName) {
		int columnIndex = getCellIndex(columnName);
		return readCell(columnIndex);
	}

}