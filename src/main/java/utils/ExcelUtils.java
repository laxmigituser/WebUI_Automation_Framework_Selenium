package utils;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExcelUtils {

    public static List<Map<String, String>> getData(String filePath, String sheetName) {
        List<Map<String, String>> dataList = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {
            Sheet sheet = workbook.getSheet(sheetName);
            Row headerRow = sheet.getRow(0);
            int cols = headerRow.getPhysicalNumberOfCells();
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                Map<String, String> dataMap = new HashMap<>();
                for (int j = 0; j < cols; j++) {
                    String key = headerRow.getCell(j).getStringCellValue();
                    String value = row.getCell(j).toString();
                    dataMap.put(key, value);
                }
                dataList.add(dataMap);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return dataList;
    }

}
