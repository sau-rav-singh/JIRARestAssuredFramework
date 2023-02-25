package TestRunner;

import java.io.IOException;

import utilities.ExcelSheetWriter;

public class DeleteRecords {

	public static void main(String[] args) throws IOException {
		ExcelSheetWriter writer = new ExcelSheetWriter("secTypes", "equity", 0);
		writer.deleteRecords("EXCEL");
		writer.closeFile();
	}

}
