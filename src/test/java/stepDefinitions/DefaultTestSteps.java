package stepDefinitions;

import io.cucumber.java.en.Given;
import utilities.ExcelSheetManager;
import utilities.ExcelSheetReader;
import utilities.ExcelSheetWriter;

public class DefaultTestSteps {

	 @Given("A Workbook named {string} and sheetname as {string} and Row number as {int} is read")
	    public void a_workbook_with_name_and_sub_sheet_name_and_row_number_is_read(String workbookName, String subSheetName, int rowNumber) throws Exception {
		 ExcelSheetReader reader = new ExcelSheetReader(workbookName, subSheetName, rowNumber);
			ExcelSheetWriter writer = new ExcelSheetWriter(workbookName, subSheetName, rowNumber);
			ExcelSheetManager.setExcelSheetReader(reader);
			ExcelSheetManager.setExcelSheetWriter(writer);
	    }
}
