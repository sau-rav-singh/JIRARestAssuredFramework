package stepDefinitions;

import java.io.IOException;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import utilities.ExcelSheetManager;
import utilities.ExcelSheetReader;
import utilities.ExcelSheetWriter;

public class WriteToExcelStepDefinition {

	private ExcelSheetReader excelSheetReader;
	private ExcelSheetWriter excelSheetWriter;
	
	@Given("A Workbook named {string} and sheetname as{string} and Row number as{int} is read")
    public void a_workbook_with_name_and_sub_sheet_name_and_row_number_is_read(String workbookName, String subSheetName, int rowNumber) throws Exception {
		excelSheetReader = new ExcelSheetReader(workbookName, subSheetName, rowNumber);
		excelSheetWriter = new ExcelSheetWriter(workbookName, subSheetName, rowNumber);
		ExcelSheetManager.setExcelSheetReader(excelSheetReader);
		ExcelSheetManager.setExcelSheetWriter(excelSheetWriter);
    }
	@When("Read symbol as {string} and orderType as {string} and print the values")
	public void read_symbol_as_and_order_type_as_and_print_the_values(String symbol, String orderType)
			throws IOException {

		if (excelSheetReader == null) {
			throw new IllegalStateException("ExcelSheetReader is not initialized for the current thread.");
		}
		symbol = excelSheetReader.readCell(symbol);
		orderType = excelSheetReader.readCell(orderType);
		System.out.println("Symbol is " + symbol);
		System.out.println("Order Type  is " + orderType);
		excelSheetWriter.writeCell("EXCEL", symbol);
		excelSheetWriter.closeFile();
	}		

}
