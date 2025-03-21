package com.JavaLab.AdvJava.Controllers;

import com.JavaLab.AdvJava.service.ExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.io.IOException;

@RestController
public class RestApiController {

    //Acquiring excel reference to excelService
    @Autowired
    private ExcelService excelService;


    //Returns a response with appended file
    @GetMapping("/export/excel")
    public ResponseEntity<InputStreamResource> exportToExcel() throws IOException {
        ByteArrayInputStream in = excelService.exportToExcel();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=rztkGoods.xlsx");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(new InputStreamResource(in));
    }
}
