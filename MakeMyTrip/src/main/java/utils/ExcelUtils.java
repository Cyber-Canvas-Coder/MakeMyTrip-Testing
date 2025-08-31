package utils;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.DataProvider;

public class ExcelUtils {
	@DataProvider(name = "excelData")
	public Object[][] excelData() throws IOException{
		Object[][] arrObj = getExcelData("filePath", "sheetname"); // excel file path
		return arrObj;
	}
	public String[][] getExcelData(String filename, String sheetname) throws IOException{
		String[][] data = null;
		try {
			FileInputStream fis = new FileInputStream(filename);
			try (XSSFWorkbook workbook = new XSSFWorkbook(fis)) { // try with resources
				XSSFSheet sheet = workbook.getSheet(sheetname);
				XSSFRow row = sheet.getRow(0);
				int numRows = sheet.getPhysicalNumberOfRows();
				int numCols = row.getLastCellNum();
				Cell cell;
				data = new String[numRows - 1][numCols];
				for (int i = 1; i < numRows; i++) {
					for (int j = 0; j < numCols; j++) {
						row = sheet.getRow(i);
						cell = row.getCell(j);
						switch (cell.getCellType()) {
				            case STRING:
				            	data[i - 1][j] = cell.getStringCellValue();
				                break;
				            case NUMERIC:
				                if (DateUtil.isCellDateFormatted(cell)) {
//			                	data[i - 1][j] = (String)cell.getDateCellValue();
				                } else {
				                	data[i - 1][j] = Double.toString(cell.getNumericCellValue());
				                }
				                break;
				            case BOOLEAN:
//			            	data[i - 1][j] = (String)cell.getBooleanCellValue();
				                break;
				            case FORMULA:
				                // Evaluate formula if needed
				            	data[i - 1][j] = (String)cell.getCellFormula();
				                break;
				            case BLANK:
				                System.out.println("");
				                break;
				            default:
				                System.out.println("Unsupported cell type");
				        }
//					data[i - 1][j] = cell.getStringCellValue();
					}
				}
			}
		}
		catch(Exception e) {
			System.out.println("The exception is: " + e.getMessage());
		}
		return data;
	}
}
