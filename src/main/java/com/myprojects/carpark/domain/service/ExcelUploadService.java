package com.myprojects.carpark.domain.service;

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
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ExcelUploadService {

    public static boolean hasExcelFormat(MultipartFile file) {
        String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

        return TYPE.equals(file.getContentType());
    }

    static List<Slot> excelToSlots(InputStream is) {
        try {
            Workbook workbook = new XSSFWorkbook(is);
            Sheet slotSheet = workbook.getSheet("SLOT");

            Iterator<Row> slotRows = slotSheet.iterator();

            List<Slot> slots = new ArrayList<>();

            while (slotRows.hasNext()) {
                Row currentRow = slotRows.next();

                //skip header
                if (currentRow.getRowNum() != 0) {
                    Iterator<Cell> cellsInRow = currentRow.iterator();

                    Slot slot = new Slot();

                    int cellIndex = 0;

                    while (cellsInRow.hasNext()) {
                        Cell currentCell = cellsInRow.next();

                        switch (cellIndex) {
                            case 0:
                                slot.setId((long) currentCell.getNumericCellValue());
                                break;

                            case 1:
                                slot.setName(currentCell.getStringCellValue());
                                break;

                            case 2:
                                slot.setFloorNumber((int) currentCell.getNumericCellValue());
                                break;

                            default:
                                break;
                        }
                        cellIndex++;
                    }
                    slots.add(slot);
                }
            }
            workbook.close();
            return slots;
        } catch (IOException e) {
            throw new RuntimeException("FAIL TO PARSE EXCEL FILE: " + e.getMessage());
        }
    }

    static List<TimeUnit> excelToTimeUnits(InputStream is) {
        try {
            Workbook workbook = new XSSFWorkbook(is);
            Sheet timeUnitSheet = workbook.getSheet("TIME_UNIT");
            Iterator<Row> timeUnitRows = timeUnitSheet.iterator();
            List<TimeUnit> timeUnits = new ArrayList<>();

            while (timeUnitRows.hasNext()) {
                Row currentRow = timeUnitRows.next();

                //skip header
                if (currentRow.getRowNum() != 0) {
                    Iterator<Cell> cellsInRow = currentRow.iterator();

                    TimeUnit timeUnit = new TimeUnit();

                    int cellIndex = 0;

                    while (cellsInRow.hasNext()) {
                        Cell currentCell = cellsInRow.next();

                        switch (cellIndex) {
                            case 0:
                                timeUnit.setId((long) currentCell.getNumericCellValue());
                                break;

                            case 1:
                                timeUnit.setDateTime(currentCell.getLocalDateTimeCellValue());
                                break;

                            default:
                                break;
                        }
                        cellIndex++;
                    }
                    timeUnits.add(timeUnit);
                }
            }
            workbook.close();
            return timeUnits;
        } catch (IOException e) {
            throw new RuntimeException("FAIL TO PARSE EXCEL FILE: " + e.getMessage());
        }
    }

    static List<Occupation> excelToOccupations(InputStream is, List<Slot> slots, List<TimeUnit> timeUnits) {
        try {
            Workbook workbook = new XSSFWorkbook(is);
            Sheet occupationSheet = workbook.getSheet("OCCUPATION");
            Iterator<Row> occupationRows = occupationSheet.iterator();
            List<Occupation> occupations = new ArrayList<>();

            while (occupationRows.hasNext()) {
                Row currentRow = occupationRows.next();

                //skip header
                if (currentRow.getRowNum() != 0) {
                    Iterator<Cell> cellsInRow = currentRow.iterator();

                    Occupation occupation = new Occupation();

                    int cellIndex = 0;

                    while (cellsInRow.hasNext()) {
                        Cell currentCell = cellsInRow.next();

                        switch (cellIndex) {
                            case 0:
                                occupation.setId((long) currentCell.getNumericCellValue());
                                break;

                            case 1:
                                occupation.setOccupied(currentCell.getBooleanCellValue());
                                break;

                            case 2:
                                occupation.setClosed(currentCell.getBooleanCellValue());
                                break;

                            case 3:
                                Long slotId = (long) currentCell.getNumericCellValue();
                                Slot slot = slots.stream().filter(s -> s.getId().equals(slotId)).findAny().orElse(new Slot());
                                occupation.setSlot(slot);
                                break;

                            case 4:
                                Long timeUnitId = (long) currentCell.getNumericCellValue();
                                TimeUnit timeUnit = timeUnits.stream().filter(t -> t.getId().equals(timeUnitId)).findAny().orElse(new TimeUnit());
                                occupation.setTimeUnit(timeUnit);
                                break;

                            default:
                                break;
                        }
                        cellIndex++;
                    }
                    occupations.add(occupation);
                }
            }
            workbook.close();
            return occupations;
        } catch (IOException e) {
            throw new RuntimeException("FAIL TO PARSE EXCEL FILE: " + e.getMessage());
        }
    }
}
