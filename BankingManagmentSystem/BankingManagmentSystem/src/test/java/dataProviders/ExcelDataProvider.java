package dataProviders;
import org.apache.poi.ss.usermodel.*;
import org.testng.annotations.DataProvider;

import java.io.FileInputStream;
import java.io.IOException;

public class ExcelDataProvider {
    @DataProvider(name = "accountsData")
    public static Object[][] getData() throws IOException {
        String excelFilePath = "src/test/resources/AccountsTestCases.xlsx";
        FileInputStream fis = new FileInputStream(excelFilePath);
        Workbook workbook = WorkbookFactory.create(fis);
        Sheet sheet = workbook.getSheetAt(0);
        
        int rowCount = sheet.getPhysicalNumberOfRows();
        Object[][] data = new Object[rowCount - 1][6]; // Exclude header

        for (int i = 1; i < rowCount; i++) {
            Row row = sheet.getRow(i);
            data[i - 1][0] = getCellValue(row.getCell(2)); // Input Email
            data[i - 1][1] = getCellValue(row.getCell(3)); // Input Full Name
            data[i - 1][2] = row.getCell(4) != null ? row.getCell(4).getNumericCellValue() : 0.0; // Input Balance
            data[i - 1][3] = getCellValue(row.getCell(5)); // Input Security Pin
            data[i - 1][4] = getCellValue(row.getCell(6)); // Expected Result
            data[i - 1][5] = getCellValue(row.getCell(1)); // Function Name
        }
        workbook.close();
        return data;
    }

    private static String getCellValue(Cell cell) {
        if (cell == null) {
            return ""; // Return empty string if cell is null
        }
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                return String.valueOf(cell.getNumericCellValue());
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            default:
                return "";
        }
    }
}

