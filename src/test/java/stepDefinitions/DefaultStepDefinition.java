package stepDefinitions;

import io.cucumber.java.en.Given;
import utilities.ExcelSheetManager;
import utilities.ExcelSheetReader;

public class DefaultStepDefinition {

	// Instance variables to store workbook name and sheet name
	private String workbookName;
	private String sheetName;

	/**
	 * Initializes the workbookName and sheetName instance variables with the
	 * provided parameters. Reads the Excel sheet specified by workbookName and
	 * sheetName at row 0 using ExcelSheetReader instance and sets it in
	 * ExcelSheetManager.
	 * 
	 * @param workbookName Name of the Excel workbook
	 * @param sheetName    Name of the sheet within the workbook
	 * @throws Exception If there is an issue with initializing the ExcelSheetReader
	 *                   or ExcelSheetManager
	 */
	@Given("A Workbook named {string} and sheetname as{string} is read")
	public void a_workbook_with_name_and_sub_sheet_name_is_read(String workbookName, String sheetName)
			throws Exception {
		this.workbookName = workbookName;
		this.sheetName = sheetName;

		// Initialize reader and writer using workbookName and sheetName at row 0
		ExcelSheetReader reader = new ExcelSheetReader(workbookName, sheetName, 0);
		ExcelSheetManager.setExcelSheetReader(reader);
	}

	/**
	 * Reads the row specified by rowNumber using ExcelSheetReader instance and sets
	 * it in ExcelSheetManager.
	 * 
	 * @param rowNumber Row number to read
	 * @throws Exception If there is an issue with initializing the ExcelSheetReader
	 *                   or ExcelSheetManager
	 */
	@Given("Row number as{int} is read")
	public void row_number_is_read(int rowNumber) throws Exception {
		// Use workbookName, sheetName, and rowNumber to initialize reader and writer
		ExcelSheetReader reader = new ExcelSheetReader(workbookName, sheetName, rowNumber);
		ExcelSheetManager.setExcelSheetReader(reader);
	}
}
