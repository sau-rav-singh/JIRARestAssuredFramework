package utilities;

/**
 * 
 * This class provides a way to manage Excel sheet readers and writers through
 * thread-local storage.
 * 
 * Thread-local storage ensures that each thread has its own instance of Excel
 * sheet reader and writer, so they don't
 * 
 * interfere with each other's operations.
 */
public class ExcelSheetManager {
	
	private static ThreadLocal<ExcelSheetReader> excelSheetReader = new ThreadLocal<>();
	private static ThreadLocal<ExcelSheetWriter> excelSheetWriter = new ThreadLocal<>();

	/**
	 * Sets the Excel sheet reader for the current thread.
	 * @param reader the Excel sheet reader to set
	 */
	public static void setExcelSheetReader(ExcelSheetReader reader) {
		excelSheetReader.set(reader);
	}

	/**
	 * Gets the Excel sheet reader for the current thread.
	 * @return the Excel sheet reader for the current thread
	 */
	public static ExcelSheetReader getExcelSheetReader() {
		return excelSheetReader.get();
	}

	/**
	 * Sets the Excel sheet writer for the current thread.
	 * @param writer the Excel sheet writer to set
	 */
	public static void setExcelSheetWriter(ExcelSheetWriter writer) {
		excelSheetWriter.set(writer);
	}

	/**
	 * Gets the Excel sheet writer for the current thread.
	 * @return the Excel sheet writer for the current thread
	 */
	public static ExcelSheetWriter getExcelSheetWriter() {
		return excelSheetWriter.get();
	}

}
