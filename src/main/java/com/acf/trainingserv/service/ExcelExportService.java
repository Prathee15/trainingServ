package com.acf.trainingserv.service;

import com.acf.trainingserv.model.Participant;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ExcelExportService {

    public byte[] generateExcelFile(List<Participant> participants) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Participants");

        // Create header row
        Row headerRow = sheet.createRow(0);
        String[] headers = {"ID", "Name", "Email", "Phone", "Course", "Enrollment Date", "Address"};
        for (int i = 0; i < headers.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(headers[i]);
            cell.setCellStyle(getHeaderCellStyle(workbook));
        }

        // Populate rows
        int rowNum = 1;
        for (Participant participant : participants) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(participant.getId());
            row.createCell(1).setCellValue(participant.getName());
            row.createCell(2).setCellValue(participant.getEmail());
            row.createCell(3).setCellValue(participant.getPhoneNumber());
            row.createCell(4).setCellValue(participant.getCourseEnrolled());
            row.createCell(5).setCellValue(participant.getEnrollmentDate().toString());
            row.createCell(6).setCellValue(participant.getAddress().getAddressLine1() + ", " +
                    participant.getAddress().getCity() + ", " +
                    participant.getAddress().getZip() + ", " +
                    participant.getAddress().getCountry());
        }

        // Auto-size columns
        for (int i = 0; i < headers.length; i++) {
            sheet.autoSizeColumn(i);
        }

        // Convert to byte array
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        workbook.write(outputStream);
        workbook.close();
        return outputStream.toByteArray();
    }

    private CellStyle getHeaderCellStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);
        style.setAlignment(HorizontalAlignment.CENTER);
        return style;
    }
}

