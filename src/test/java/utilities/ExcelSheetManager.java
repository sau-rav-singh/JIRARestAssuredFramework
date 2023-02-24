package utilities;

public class ExcelSheetManager {
    private static ThreadLocal<ExcelSheetReader> excelSheetReader = new ThreadLocal<>();
    private static ThreadLocal<ExcelSheetWriter> excelSheetWriter = new ThreadLocal<>();

    public static void setExcelSheetReader(ExcelSheetReader reader) {
        excelSheetReader.set(reader);
    }

    public static ExcelSheetReader getExcelSheetReader() {
        return excelSheetReader.get();
    }

    public static void setExcelSheetWriter(ExcelSheetWriter writer) {
        excelSheetWriter.set(writer);
    }

    public static ExcelSheetWriter getExcelSheetWriter() {
        return excelSheetWriter.get();
    }

    
}
	