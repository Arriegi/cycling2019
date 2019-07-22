package eus.jarriaga.cycling.utils;

import eus.jarriaga.cycling.business.entities.Expense;
import eus.jarriaga.cycling.business.entities.User;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ExcelExpenseExportter {

    @Autowired
    private Environment env;

    private void exporExpensesDetailed(List<Expense> expenses, User user, Workbook wb, LocalDate from, LocalDate to) {
        Sheet sheet = wb.createSheet("Detallado");

        DataFormat fmt = wb.createDataFormat();
        CellStyle cellstyleSHMM = wb.createCellStyle();
        cellstyleSHMM.setDataFormat(fmt.getFormat("[h]:mm"));

        Font fontCabecera = wb.createFont();
        fontCabecera.setColor(IndexedColors.WHITE.getIndex());

        CellStyle cellstyleCabecera = wb.createCellStyle();
        cellstyleCabecera.setFillBackgroundColor(IndexedColors.GREEN.getIndex());
        cellstyleCabecera.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellstyleCabecera.setFont(fontCabecera);

        CellStyle cellstyleNumber = wb.createCellStyle();
        //cellstyleNumber.setDataFormat(fmt.getFormat("#.##0,00"));
        cellstyleNumber.setDataFormat(fmt.getFormat("#.00"));

        Cell cell;
        int rowNum;

        rowNum = -1;

        // Create context info
        rowNum++;
        Row infoRow = sheet.createRow(rowNum);
        cell = infoRow.createCell(0);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-y");
        //cell.setCellValue("Periodo: " + from.toString() + " / " + to.toString());
        cell.setCellValue("Periodo: " + from.format(formatter) + " / " + to.format(formatter));

        // Empty line between info row and head row
        rowNum++;

        // Create head row
        rowNum++;
        Row headerRow = sheet.createRow(rowNum);

        cell = headerRow.createCell(0);
        cell.setCellValue("Fecha");
        cell.setCellStyle(cellstyleCabecera);
        cell = headerRow.createCell(1);
        cell.setCellValue("Usuario");
        cell.setCellStyle(cellstyleCabecera);
        cell = headerRow.createCell(2);
        cell.setCellValue("Tipo");
        cell.setCellStyle(cellstyleCabecera);
        cell = headerRow.createCell(3);
        cell.setCellValue("Proyecto");
        cell.setCellStyle(cellstyleCabecera);
        cell = headerRow.createCell(4);
        cell.setCellValue("Comentario");
        cell.setCellStyle(cellstyleCabecera);
        cell = headerRow.createCell(5);
        cell.setCellValue("Distancia");
        cell.setCellStyle(cellstyleCabecera);
        cell = headerRow.createCell(6);
        cell.setCellValue("Importe");
        cell.setCellStyle(cellstyleCabecera);

        Float totalAmmount = 0f;

        int inicioHoras = -1;
        for (int iExpense = 0; iExpense < expenses.size() ; iExpense++) {
            rowNum++;

            if (inicioHoras == -1) {
                inicioHoras = rowNum;
            }

            Row row = sheet.createRow(rowNum);

            totalAmmount += expenses.get(iExpense).getAmmount();

            // Date
            row.createCell(0).setCellValue(expenses.get(iExpense).getDate().toString());

            // Column User
            row.createCell(1).setCellValue(expenses.get(iExpense).getUser().getName());

            // Column type
            if (expenses.get(iExpense).getType() == 2) {
                row.createCell(2).setCellValue("Kilometraje");
            }
            else {
                row.createCell(2).setCellValue("Dietas");
            }

            // Column Project
            row.createCell(3).setCellValue(expenses.get(iExpense).getProject().getName());

            // Comment
            row.createCell(4).setCellValue(expenses.get(iExpense).getComment());

            // Distancia
            if (expenses.get(iExpense).getDistancia() != null) {
                row.createCell(5).setCellValue(expenses.get(iExpense).getDistancia());
            }

            // Ammount
            cell = row.createCell(6);
            cell.setCellValue(expenses.get(iExpense).getAmmount());
            cell.setCellStyle(cellstyleNumber);


        }
        rowNum++;

        Row row = sheet.createRow(rowNum);
        // Date
        row.createCell(0).setCellValue("Total");

        // Column User
        row.createCell(1).setCellValue("");

        // Column type
        row.createCell(2).setCellValue("");

        // Column Project
        row.createCell(3).setCellValue("");

        // Comment
        row.createCell(4).setCellValue("");

        row.createCell(5).setCellValue("");

        // Ammount
        cell = row.createCell(6);
        cell.setCellValue(totalAmmount);
        cell.setCellStyle(cellstyleNumber);

        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);
        sheet.autoSizeColumn(2);
        sheet.autoSizeColumn(3);
        sheet.autoSizeColumn(4);
        sheet.autoSizeColumn(5);
    }

    public String exportExpenses(List<Expense> expenses, User user, LocalDate from, LocalDate to) {
        String tmpPath = System.getProperty("java.io.tmpdir");
        String fileName = tmpPath + File.separator + "imputador" + File.separator + user.getId() + ".xls";
        File file = new File(fileName);
        file.getParentFile().mkdirs();

        Workbook wb = new HSSFWorkbook();
        try  (
                //Path rootLocation;
                //rootLocation = Paths.get(env.getProperty("resources.files.location"));
                //Files.createDirectories(rootLocation);
                OutputStream fileOut = new FileOutputStream(fileName)) {

            /* CreationHelper helps us create instances of various things like DataFormat,
           Hyperlink, RichTextString etc, in a format (HSSF, XSSF) independent way */
            CreationHelper createHelper = wb.getCreationHelper();

            exporExpensesDetailed(expenses, user, wb, from, to);

            wb.write(fileOut);
            wb.close();
        }
        catch (IOException ioe) {

        }
        return fileName;
    }

}
