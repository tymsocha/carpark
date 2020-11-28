package com.myprojects.carpark.domain.service;

import com.myprojects.carpark.domain.dto.exceldtos.DatabaseDto;
import com.myprojects.carpark.domain.entity.Occupation;
import com.myprojects.carpark.domain.entity.Slot;
import com.myprojects.carpark.domain.entity.TimeUnit;
import com.myprojects.carpark.utils.GeneralConstants;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ExcelDownloadService {

    static ByteArrayInputStream createExcelFile(List<DatabaseDto> database, List<Slot> slots, List<TimeUnit> timeUnits, List<Occupation> occupations) {

        String[] entityHeaders = {GeneralConstants.slotEntityName, GeneralConstants.timeUnitEntityName, GeneralConstants.occupationEntityName};
        String[] fieldHeaders = {GeneralConstants.slotIdFieldName, GeneralConstants.slotNameFieldName, GeneralConstants.slotFloorNumberFieldName,
                                 GeneralConstants.timeUnitIdFieldName, GeneralConstants.timeUnitDateTimeFieldName,
                                 GeneralConstants.occupationIdFieldName, GeneralConstants.occupiedFieldName, GeneralConstants.isClosedFieldName};


        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            Sheet slotSheet = workbook.createSheet("SLOT");
            Sheet timeUnitSheet = workbook.createSheet("TIME_UNIT");
            Sheet occupationSheet = workbook.createSheet("OCCUPATION");
            Sheet databaseSheet = workbook.createSheet("PARKING_DATABASE");

            //HEADER ROWS
            createEntityHeaderRow(databaseSheet, entityHeaders);
            createFieldsHeaderRow(databaseSheet, fieldHeaders);
            createSlotHeaderRow(slotSheet, fieldHeaders);
            createTimeUnitHeaderRow(timeUnitSheet, fieldHeaders);
            createOccupationHeaderRow(occupationSheet, fieldHeaders);

            createDataBaseSheet(database, databaseSheet);
            createSlotSheet(slots, slotSheet);
            createTimeUnitSheet(timeUnits, timeUnitSheet);
            createOccupationSheet(occupations, occupationSheet);

            workbook.write(out);

            return new ByteArrayInputStream(out.toByteArray());

        } catch (IOException e) {
            throw new RuntimeException("FAIL TO IMPORT DATA TO EXCEL FILE: " + e.getMessage());
        }
    }

    private static void createOccupationHeaderRow(Sheet occupationSheet, String[] fieldHeaders) {
        Row headerRow = occupationSheet.createRow(0);

        Cell occupationIdCell = headerRow.createCell(0);
        occupationIdCell.setCellValue(fieldHeaders[5]);

        Cell occupiedCell = headerRow.createCell(1);
        occupiedCell.setCellValue(fieldHeaders[6]);

        Cell closedCell = headerRow.createCell(2);
        closedCell.setCellValue(fieldHeaders[7]);

        Cell slotIdCell = headerRow.createCell(3);
        slotIdCell.setCellValue(fieldHeaders[0]);

        Cell timeUnitIdCell = headerRow.createCell(4);
        timeUnitIdCell.setCellValue(fieldHeaders[3]);

    }

    private static void createTimeUnitHeaderRow(Sheet timeUnitSheet, String[] fieldHeaders) {
        Row headerRow = timeUnitSheet.createRow(0);

        Cell timeUnitIdCell = headerRow.createCell(0);
        timeUnitIdCell.setCellValue(fieldHeaders[3]);

        Cell dataTimeCell = headerRow.createCell(1);
        dataTimeCell.setCellValue(fieldHeaders[4]);


    }

    private static void createSlotHeaderRow(Sheet slotSheet, String[] fieldHeaders) {
        Row headerRow = slotSheet.createRow(0);

        Cell slotIdCell = headerRow.createCell(0);
        slotIdCell.setCellValue(fieldHeaders[0]);

        Cell slotNameCell = headerRow.createCell(1);
        slotNameCell.setCellValue(fieldHeaders[1]);

        Cell slotFloorNumberCell = headerRow.createCell(2);
        slotFloorNumberCell.setCellValue(fieldHeaders[2]);

    }

    private static void createFieldsHeaderRow(Sheet sheet, String[] fieldHeaders) {
        Row headerRow = sheet.createRow(1);

        for (int col = 0; col < fieldHeaders.length; col++) {
            Cell cell = headerRow.createCell(col);
            cell.setCellValue(fieldHeaders[col]);
        }

    }

    private static void createEntityHeaderRow(Sheet sheet, String[] entityHeaders) {
        Row headerRow = sheet.createRow(0);

        Cell slotCell = headerRow.createCell(0);
        slotCell.setCellValue(entityHeaders[0]);

        Cell timeUnitCell = headerRow.createCell(3);
        timeUnitCell.setCellValue(entityHeaders[1]);

        Cell occupationCell = headerRow.createCell(5);
        occupationCell.setCellValue(entityHeaders[2]);

    }

    private static void createDataBaseSheet(List<DatabaseDto> database, Sheet databaseSheet) {
        Integer rowIndex = 2;

        for (DatabaseDto dto : database) {
            Row row = databaseSheet.createRow(rowIndex++);

            row.createCell(0).setCellValue(dto.getSlotId());
            row.createCell(1).setCellValue(dto.getSlotName());
            row.createCell(2).setCellValue(dto.getSlotFloorNumber());

            row.createCell(3).setCellValue(dto.getTimeUnitId());
            row.createCell(4).setCellValue(dto.getDateTime());

            row.createCell(5).setCellValue(dto.getOccupationId());
            row.createCell(6).setCellValue(dto.isOccupied());
            row.createCell(7).setCellValue(dto.isClosed());
        }
    }

    private static void createSlotSheet(List<Slot> slots, Sheet slotSheet) {
        Integer rowIndex = 1;

        for (Slot slot : slots) {
            Row row = slotSheet.createRow(rowIndex++);

            row.createCell(0).setCellValue(slot.getId());
            row.createCell(1).setCellValue(slot.getName());
            row.createCell(2).setCellValue(slot.getFloorNumber());
        }
    }

    private static void createTimeUnitSheet(List<TimeUnit> timeUnits, Sheet timeUnitSheet) {
        Integer rowIndex = 1;

        for (TimeUnit timeUnit : timeUnits) {
            Row row = timeUnitSheet.createRow(rowIndex++);

            row.createCell(0).setCellValue(timeUnit.getId());
            row.createCell(1).setCellValue(timeUnit.getDateTime());
        }
    }

    private static void createOccupationSheet(List<Occupation> occupations, Sheet occupationSheet) {
        Integer rowIndex = 1;

        for (Occupation occupation : occupations) {
            Row row = occupationSheet.createRow(rowIndex++);

            row.createCell(0).setCellValue(occupation.getId());
            row.createCell(1).setCellValue(occupation.isOccupied());
            row.createCell(2).setCellValue(occupation.isClosed());
            row.createCell(3).setCellValue(occupation.getSlot().getId());
            row.createCell(4).setCellValue(occupation.getTimeUnit().getId());
        }
    }
}
