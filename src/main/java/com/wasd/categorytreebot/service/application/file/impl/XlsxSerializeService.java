package com.wasd.categorytreebot.service.application.file.impl;

import com.wasd.categorytreebot.service.application.file.ExcelSerializeService;
import org.apache.poi.openxml4j.exceptions.NotOfficeXmlFileException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class XlsxSerializeService implements ExcelSerializeService {
    @Override
    public File serialize(String filePath, Map<Integer, List<String>> content) throws IOException,
            NullPointerException {
        File file = new File(filePath);
        
        try (XSSFWorkbook workbook = new XSSFWorkbook();
             FileOutputStream fileOutputStream = new FileOutputStream(file)) {

            XSSFSheet sheet = workbook.createSheet("Categories");
            fillSheetFromContent(sheet, content);
            workbook.write(fileOutputStream);
        }

        return file;
    }

    @Override
    public Map<Integer, List<String>> deserialize(String filePath) throws NotOfficeXmlFileException, NullPointerException, IOException {
        Map<Integer, List<String>> content = new HashMap<>();

        try (FileInputStream fileInputStream = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fileInputStream)) {

            Sheet sheet = workbook.getSheetAt(0);

            fillContentFromSheet(sheet, content);
        }

        return content;
    }

    private void fillContentFromSheet(Sheet sheet, Map<Integer, List<String>> content) {
        int i = 0;

        for (Row row : sheet) {
            content.put(i, new ArrayList<>());
            for (Cell cell : row) {
                content.get(i).add(cell.getRichStringCellValue().getString());
            }
            i++;
        }
    }

    private void fillSheetFromContent(Sheet sheet, Map<Integer, List<String>> content) {
        for (Map.Entry<Integer, List<String>> entry : content.entrySet()) {
            Row sheetRow = sheet.createRow(entry.getKey());
            int i = 0;
            for (String cell : entry.getValue()) {
                Cell rowCell = sheetRow.createCell(i++);
                rowCell.setCellValue(cell);
            }
        }
    }
}
