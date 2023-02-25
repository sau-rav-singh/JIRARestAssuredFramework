package stepDefinitions;

import java.io.IOException;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import utilities.ExcelSheetManager;
import utilities.ExcelSheetReader;
import utilities.ExcelSheetWriter;

/**
 * 
 * This class contains the step definitions for reading and writing to an Excel
 * sheet.
 */
public class WriteToExcelStepDefinition {

	private ExcelSheetReader excelSheetReader;
	private ExcelSheetWriter excelSheetWriter;

	/**
	 * 
	 * Reads a workbook with the specified name and sub-sheet name and row number.
	 * 
	 * @param workbookName the name of the workbook to read
	 * @param subSheetName the name of the sub-sheet to read
	 * @param rowNumber    the row number to read
	 * @throws Exception if an error occurs while reading the workbook
	 */
	@Given("A Workbook named {string} and sheetname as{string} and Row number as {int} is read")
	public void a_workbook_with_name_and_sub_sheet_name_and_row_number_is_read(String workbookName, String subSheetName,
			int rowNumber) throws Exception {
		System.out.println("rowNumber from feature file is " + rowNumber);
		excelSheetReader = new ExcelSheetReader(workbookName, subSheetName, rowNumber);
		ExcelSheetManager.setExcelSheetReader(excelSheetReader);
	}

	/**
	 * 
	 * Reads a workbook with the specified name and sub-sheet name and row number,
	 * and sets up an Excel sheet writer.
	 * 
	 * @param workbookName the name of the workbook to read
	 * @param subSheetName the name of the sub-sheet to read
	 * @param rowNumber    the row number to read
	 * @throws Exception if an error occurs while reading the workbook
	 */
	@Given("A Workbook named {string} and sheetname as{string} and Row number as {int} is read and to write Data")
	public void a_workbook_with_name_and_sub_sheet_name_and_row_number_is_readWrite(String workbookName,
			String subSheetName, int rowNumber) throws Exception {
		System.out.println("rowNumber from feature file is " + rowNumber);
		excelSheetReader = new ExcelSheetReader(workbookName, subSheetName, rowNumber);
		excelSheetWriter = new ExcelSheetWriter(workbookName, subSheetName, rowNumber);
		ExcelSheetManager.setExcelSheetReader(excelSheetReader);
		ExcelSheetManager.setExcelSheetWriter(excelSheetWriter);
	}

	/**
	 * 
	 * Reads a cell with the specified symbol and orderType, and prints the values.
	 * 
	 * @param symbol    the name of the symbol to read from the cell
	 * 
	 * @param orderType the order type to read from the cell
	 * 
	 * @throws IOException if an error occurs while reading the cell
	 */
	@When("Read symbol as {string} and orderType as {string} and print the values")
	public void read_symbol_as_and_order_type_as_and_print_the_values(String symbol, String orderType)
			throws IOException {

		if (excelSheetReader == null) {
			throw new IllegalStateException("ExcelSheetReader is not initialized for the current thread.");
		}

		symbol = excelSheetReader.readCell(symbol);
		orderType = excelSheetReader.readCell(orderType);
		System.out.println("Symbol is " + symbol);
		System.out.println("Order Type is " + orderType);
		excelSheetWriter.writeCell("EXCEL", symbol);
		excelSheetWriter.closeFile();
	}
}
