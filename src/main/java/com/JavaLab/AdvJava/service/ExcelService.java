package com.JavaLab.AdvJava.service;

import com.JavaLab.AdvJava.models.RztkGood;
import com.JavaLab.AdvJava.models.RztkGoodsRepository;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

//An Excel service that uses Apache POI library to create an Excel workbook
@Service
public class ExcelService {
    @Autowired
    private RztkGoodsRepository rztkGoodRepository;

    public ByteArrayInputStream exportToExcel() throws IOException {
        Iterable<RztkGood> rztkGoods = rztkGoodRepository.findAll();

        try (Workbook workbook = new HSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("rztkGoods");

            // Create header row
            Row headerRow = sheet.createRow(0);
            String[] headers = {"Name", "Price UAH", "Price USD"};
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }

            // Create data rows
            int rowNum = 1;
            for (RztkGood rztkGood : rztkGoods) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(rztkGood.getTitle());
                row.createCell(1).setCellValue(rztkGood.getPrice_uah());
                row.createCell(2).setCellValue(rztkGood.getPrice_usd());
            }

            // Auto-size columns
            for (int i = 0; i < headers.length; i++) {
                sheet.autoSizeColumn(i);
            }

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            workbook.write(out);
            return new ByteArrayInputStream(out.toByteArray());
        }
    }
}
