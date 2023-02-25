package stepDefinitions;

import java.io.IOException;

import io.cucumber.java.en.When;
import utilities.ExcelSheetManager;
import utilities.ExcelSheetReader;
import utilities.ExcelSheetWriter;

public class ReadWriteToExcelStepDefinition {

	private final ExcelSheetReader excelSheetReader;
	private final ExcelSheetWriter excelSheetWriter;
	
	public ReadWriteToExcelStepDefinition() {
		excelSheetReader = ExcelSheetManager.getExcelSheetReader();
		excelSheetWriter = ExcelSheetManager.getExcelSheetWriter();
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
