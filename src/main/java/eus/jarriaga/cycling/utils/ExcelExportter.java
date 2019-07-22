package eus.jarriaga.cycling.utils;

import eus.jarriaga.cycling.business.entities.User;
import eus.jarriaga.cycling.business.entities.WorkPart;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ExcelExportter {

    @Autowired
    private Environment env;

    private void exporWorkPartsClient(List<WorkPart> parts, User user, Workbook wb, LocalDate from, LocalDate to) {

        Sheet sheet = wb.createSheet("Clientes");

        DataFormat fmt = wb.createDataFormat();

        CellStyle cellstyleSHMM = wb.createCellStyle();
        cellstyleSHMM.setDataFormat(fmt.getFormat("[h]:mm"));

        Font fontCabecera = wb.createFont();
        fontCabecera.setColor(IndexedColors.WHITE.getIndex());

        CellStyle cellstyleCabecera = wb.createCellStyle();
        cellstyleCabecera.setFillBackgroundColor(IndexedColors.GREEN.getIndex());
        cellstyleCabecera.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellstyleCabecera.setFont(fontCabecera);

        List<String> listaClientes = new ArrayList<String>();
        for (int iPart = 0; iPart < parts.size() ; iPart++) {
            if (listaClientes.indexOf(parts.get(iPart).getProject().getClient().getName()) == -1) {
                listaClientes.add(parts.get(iPart).getProject().getClient().getName());
            }
        }

        Float[] listaSumaTiempo = new Float[listaClientes.size()];
        for(int iClient = 0 ; iClient < listaClientes.size() ; iClient++) {
            listaSumaTiempo[iClient] = 0f;

            for (int iPart = 0; iPart < parts.size() ; iPart++) {
                if (listaClientes.get(iClient) == parts.get(iPart).getProject().getClient().getName()) {
                    Long dur = parts.get(iPart).getDuration().getSeconds();
                    listaSumaTiempo[iClient] += dur;
                }
            }

            // Convertirlo en horas de un día para luego aplicar formato excel: hh:mm
            listaSumaTiempo[iClient] = listaSumaTiempo[iClient] / 86400f;
        }

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
        cell.setCellValue("Cliente");
        cell.setCellStyle(cellstyleCabecera);
        cell = headerRow.createCell(1);
        cell.setCellValue("Horas");
        cell.setCellStyle(cellstyleCabecera);

        int inicioHoras = -1;
        for(int iClient = 0 ; iClient < listaClientes.size() ; iClient++) {
            rowNum++;

            if (inicioHoras == -1) {
                inicioHoras = rowNum;
            }

            Row row = sheet.createRow(rowNum);
            // Columna cliente
            row.createCell(0).setCellValue(listaClientes.get(iClient));

            // Columna horas
            cell = row.createCell(1);

            cell.setCellStyle(cellstyleSHMM);
            cell.setCellValue(listaSumaTiempo[iClient]);
        }

        rowNum += 2;

        Row row = sheet.createRow(rowNum);
        // Columna cliente
        row.createCell(0).setCellValue("Total");
        //Columna suma horas
        String strFormula  = "SUM(B"+ inicioHoras +":B" + (listaClientes.size()+inicioHoras) + ")";
        cell = row.createCell(1);
        cell.setCellStyle(cellstyleSHMM);
        cell.setCellType(CellType.FORMULA);
        cell.setCellFormula(strFormula);


        sheet.autoSizeColumn(0);
    }

    private void exporWorkPartsProject(List<WorkPart> parts, User user, Workbook wb, LocalDate from, LocalDate to) {

        Sheet sheet = wb.createSheet("Proyectos");

        DataFormat fmt = wb.createDataFormat();
        CellStyle cellstyleSHMM = wb.createCellStyle();
        cellstyleSHMM.setDataFormat(fmt.getFormat("[h]:mm"));

        Font fontCabecera = wb.createFont();
        fontCabecera.setColor(IndexedColors.WHITE.getIndex());

        CellStyle cellstyleCabecera = wb.createCellStyle();
        cellstyleCabecera.setFillBackgroundColor(IndexedColors.GREEN.getIndex());
        cellstyleCabecera.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellstyleCabecera.setFont(fontCabecera);


        List<String> listaProyectos = new ArrayList<String>();
        for (int iPart = 0; iPart < parts.size() ; iPart++) {
            if (listaProyectos.indexOf(parts.get(iPart).getProject().getName()) == -1) {
                listaProyectos.add(parts.get(iPart).getProject().getName());
            }
        }

        Float[] listaSumaTiempo = new Float[listaProyectos.size()];
        for(int iProject = 0 ; iProject < listaProyectos.size() ; iProject++) {
            listaSumaTiempo[iProject] = 0f;

            for (int iPart = 0; iPart < parts.size() ; iPart++) {
                if (listaProyectos.get(iProject) == parts.get(iPart).getProject().getName()) {
                    Long dur = parts.get(iPart).getDuration().getSeconds();
                    listaSumaTiempo[iProject] += dur;
                }
            }

            // Convertirlo en horas de un día para luego aplicar formato excel: hh:mm
            listaSumaTiempo[iProject] = listaSumaTiempo[iProject] / 86400f;
        }

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
        cell.setCellValue("Proyecto");
        cell.setCellStyle(cellstyleCabecera);
        cell = headerRow.createCell(1);
        cell.setCellValue("Horas");
        cell.setCellStyle(cellstyleCabecera);

        int inicioHoras = -1;
        for(int iClient = 0 ; iClient < listaProyectos.size() ; iClient++) {
            rowNum++;

            if (inicioHoras == -1) {
                inicioHoras = rowNum;
            }

            Row row = sheet.createRow(rowNum);
            // Columna cliente
            row.createCell(0).setCellValue(listaProyectos.get(iClient));

            // Columna horas
            cell = row.createCell(1);

            cell.setCellStyle(cellstyleSHMM);
            cell.setCellValue(listaSumaTiempo[iClient]);
        }

        rowNum += 2;

        Row row = sheet.createRow(rowNum);
        // Columna cliente
        row.createCell(0).setCellValue("Total");
        //Columna suma horas
        String strFormula  = "SUM(B" + inicioHoras + ":B" + (listaProyectos.size()+inicioHoras) + ")";
        cell = row.createCell(1);
        cell.setCellStyle(cellstyleSHMM);
        cell.setCellType(CellType.FORMULA);
        cell.setCellFormula(strFormula);


        sheet.autoSizeColumn(0);

    }

    private void exporWorkPartsUser(List<WorkPart> parts, User user, Workbook wb, LocalDate from, LocalDate to) {

        Sheet sheet = wb.createSheet("Operarios");

        DataFormat fmt = wb.createDataFormat();
        CellStyle cellstyleSHMM = wb.createCellStyle();
        cellstyleSHMM.setDataFormat(fmt.getFormat("[h]:mm"));

        Font fontCabecera = wb.createFont();
        fontCabecera.setColor(IndexedColors.WHITE.getIndex());

        CellStyle cellstyleCabecera = wb.createCellStyle();
        cellstyleCabecera.setFillBackgroundColor(IndexedColors.GREEN.getIndex());
        cellstyleCabecera.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellstyleCabecera.setFont(fontCabecera);


        List<String> listaUsuarios = new ArrayList<String>();
        for (int iPart = 0; iPart < parts.size() ; iPart++) {
            if (listaUsuarios.indexOf(parts.get(iPart).getUser().getName()) == -1) {
                listaUsuarios.add(parts.get(iPart).getUser().getName());
            }
        }

        Float[] listaSumaTiempo = new Float[listaUsuarios.size()];
        for(int iProject = 0 ; iProject < listaUsuarios.size() ; iProject++) {
            listaSumaTiempo[iProject] = 0f;

            for (int iPart = 0; iPart < parts.size() ; iPart++) {
                if (listaUsuarios.get(iProject) == parts.get(iPart).getUser().getName()) {
                    Long dur = parts.get(iPart).getDuration().getSeconds();
                    listaSumaTiempo[iProject] += dur;
                }
            }

            // Convertirlo en horas de un día para luego aplicar formato excel: hh:mm
            listaSumaTiempo[iProject] = listaSumaTiempo[iProject] / 86400f;
        }

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
        cell.setCellValue("Operario");
        cell.setCellStyle(cellstyleCabecera);
        cell = headerRow.createCell(1);
        cell.setCellValue("Horas");
        cell.setCellStyle(cellstyleCabecera);

        int inicioHoras = -1;
        for(int iClient = 0 ; iClient < listaUsuarios.size() ; iClient++) {
            rowNum++;

            if (inicioHoras == -1) {
                inicioHoras = rowNum;
            }

            Row row = sheet.createRow(rowNum);
            // Columna cliente
            row.createCell(0).setCellValue(listaUsuarios.get(iClient));

            // Columna horas
            cell = row.createCell(1);

            cell.setCellStyle(cellstyleSHMM);
            cell.setCellValue(listaSumaTiempo[iClient]);
        }

        rowNum += 2;

        Row row = sheet.createRow(rowNum);
        // Columna cliente
        row.createCell(0).setCellValue("Total");
        //Columna suma horas
        String strFormula  = "SUM(B" + inicioHoras + ":B" + (listaUsuarios.size()+inicioHoras) + ")";
        cell = row.createCell(1);
        cell.setCellStyle(cellstyleSHMM);
        cell.setCellType(CellType.FORMULA);
        cell.setCellFormula(strFormula);


        sheet.autoSizeColumn(0);

    }

    private void exporWorkPartsOperations(List<WorkPart> parts, User user, Workbook wb, LocalDate from, LocalDate to) {

        Sheet sheet = wb.createSheet("Operaciones");

        DataFormat fmt = wb.createDataFormat();
        CellStyle cellstyleSHMM = wb.createCellStyle();
        cellstyleSHMM.setDataFormat(fmt.getFormat("[h]:mm"));

        Font fontCabecera = wb.createFont();
        fontCabecera.setColor(IndexedColors.WHITE.getIndex());

        CellStyle cellstyleCabecera = wb.createCellStyle();
        cellstyleCabecera.setFillBackgroundColor(IndexedColors.GREEN.getIndex());
        cellstyleCabecera.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellstyleCabecera.setFont(fontCabecera);


        List<String> listaOperaciones = new ArrayList<String>();
        for (int iPart = 0; iPart < parts.size() ; iPart++) {
            if (listaOperaciones.indexOf(parts.get(iPart).getOperation().getName()) == -1) {
                listaOperaciones.add(parts.get(iPart).getOperation().getName());
            }
        }

        Float[] listaSumaTiempo = new Float[listaOperaciones.size()];
        for(int iProject = 0 ; iProject < listaOperaciones.size() ; iProject++) {
            listaSumaTiempo[iProject] = 0f;

            for (int iPart = 0; iPart < parts.size() ; iPart++) {
                if (listaOperaciones.get(iProject) == parts.get(iPart).getOperation().getName()) {
                    Long dur = parts.get(iPart).getDuration().getSeconds();
                    listaSumaTiempo[iProject] += dur;
                }
            }

            // Convertirlo en horas de un día para luego aplicar formato excel: hh:mm
            listaSumaTiempo[iProject] = listaSumaTiempo[iProject] / 86400f;
        }

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
        cell.setCellValue("Operación");
        cell.setCellStyle(cellstyleCabecera);
        cell = headerRow.createCell(1);
        cell.setCellValue("Horas");
        cell.setCellStyle(cellstyleCabecera);

        int inicioHoras = -1;
        for(int iOperator = 0 ; iOperator < listaOperaciones.size() ; iOperator++) {
            rowNum++;

            if (inicioHoras == -1) {
                inicioHoras = rowNum;
            }

            Row row = sheet.createRow(rowNum);
            // Columna cliente
            row.createCell(0).setCellValue(listaOperaciones.get(iOperator));

            // Columna horas
            cell = row.createCell(1);

            cell.setCellStyle(cellstyleSHMM);
            cell.setCellValue(listaSumaTiempo[iOperator]);
        }

        rowNum += 2;

        Row row = sheet.createRow(rowNum);
        // Columna cliente
        row.createCell(0).setCellValue("Total");
        //Columna suma horas
        String strFormula  = "SUM(B" + inicioHoras + ":B" + (listaOperaciones.size()+inicioHoras) + ")";
        cell = row.createCell(1);
        cell.setCellStyle(cellstyleSHMM);
        cell.setCellType(CellType.FORMULA);
        cell.setCellFormula(strFormula);


        sheet.autoSizeColumn(0);

    }

    private void exporWorkPartsDetailed(List<WorkPart> parts, User user, Workbook wb, LocalDate from, LocalDate to) {
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

        List<ExcelExportterAux> listaDetallado = new ArrayList<ExcelExportterAux>();
        for (int iPart = 0; iPart < parts.size() ; iPart++) {

            // Project
            ExcelExportterAux project = new ExcelExportterAux();
            project.setName(parts.get(iPart).getProject().getName());
            //if (listaDetallado.indexOf(parts.get(iPart).getProject().getName()) == -1) {
            int lll = listaDetallado.indexOf(project);
            if (listaDetallado.indexOf(project) == -1) {

                listaDetallado.add(project);
            }
            int indiceProyecto = listaDetallado.indexOf(project);
            listaDetallado.get(indiceProyecto).setTotalSeconds(listaDetallado.get(indiceProyecto).getTotalSeconds() + parts.get(iPart).getDuration().getSeconds());
            //listaDetallado.get(indiceProyecto).

            // Operation
            ExcelExportterAux operation = new ExcelExportterAux();
            operation.setName(parts.get(iPart).getOperation().getName());

            if (listaDetallado.get(indiceProyecto).getList().indexOf(operation) == -1) {
                listaDetallado.get(indiceProyecto).getList().add(operation);
            }
            int indiceOperation = listaDetallado.get(indiceProyecto).getList().indexOf(operation);
            ((ExcelExportterAux) listaDetallado.get(indiceProyecto).getList().get(indiceOperation)).setTotalSeconds(((ExcelExportterAux) listaDetallado.get(indiceProyecto).getList().get(indiceOperation)).getTotalSeconds() + parts.get(iPart).getDuration().getSeconds());

            // Operator
            ExcelExportterAux operator = new ExcelExportterAux();
            operator.setName(parts.get(iPart).getUser().getName());

            if  (((ExcelExportterAux) listaDetallado.get(indiceProyecto).getList().get(indiceOperation)).getList().indexOf(operator) == -1) {
                ((ExcelExportterAux) listaDetallado.get(indiceProyecto).getList().get(indiceOperation)).getList().add(operator);
            }
            int indiceOperator = ((ExcelExportterAux) listaDetallado.get(indiceProyecto).getList().get(indiceOperation)).getList().indexOf(operator);

            ((ExcelExportterAux) (((ExcelExportterAux) listaDetallado.get(indiceProyecto).getList().get(indiceOperation)).getList().get(indiceOperator))).setTotalSeconds(((ExcelExportterAux) (((ExcelExportterAux) listaDetallado.get(indiceProyecto).getList().get(indiceOperation)).getList().get(indiceOperator))).getTotalSeconds() + parts.get(iPart).getDuration().getSeconds());

        }

        // Order info
        Collections.sort(listaDetallado);
        for(int iProject = 0 ; iProject < listaDetallado.size() ; iProject++) {
            List<ExcelExportterAux> listaOperations = listaDetallado.get(iProject).getList();
            Collections.sort(listaOperations);
            for (int iOperation = 0 ; iOperation < listaOperations.size() ; iOperation++) {
                List<ExcelExportterAux> listaOperators = ((ExcelExportterAux) listaOperations.get(iOperation)).getList();
                Collections.sort(listaOperators);
            }
        }

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
        cell.setCellValue("Proyectos");
        cell.setCellStyle(cellstyleCabecera);
        cell = headerRow.createCell(1);
        cell.setCellValue("Operaciones");
        cell.setCellStyle(cellstyleCabecera);
        cell = headerRow.createCell(2);
        cell.setCellValue("Operarios");
        cell.setCellStyle(cellstyleCabecera);
        cell = headerRow.createCell(3);
        cell.setCellValue("Horas");
        cell.setCellStyle(cellstyleCabecera);

        int inicioHoras = -1;
        for(int iProject = 0 ; iProject < listaDetallado.size() ; iProject++) {
            List<ExcelExportterAux> listaOperations = listaDetallado.get(iProject).getList();
            for (int iOperation = 0 ; iOperation < listaOperations.size() ; iOperation++) {
                List<ExcelExportterAux> listaOperators = ((ExcelExportterAux) listaOperations.get(iOperation)).getList();
                for (int iOperator = 0 ; iOperator < listaOperators.size() ; iOperator++) {
                    rowNum++;

                    if (inicioHoras == -1) {
                        inicioHoras = rowNum;
                    }

                    Row row = sheet.createRow(rowNum);

                    String value;

                    value = listaDetallado.get(iProject).getName();

                    // Column project
                    row.createCell(0).setCellValue(listaDetallado.get(iProject).getName());

                    // Column operation
                    value = ((ExcelExportterAux) listaDetallado.get(iProject).getList().get(iOperation)).getName();
                    row.createCell(1).setCellValue(((ExcelExportterAux) listaDetallado.get(iProject).getList().get(iOperation)).getName());

                    // Column operator
                    value = ((ExcelExportterAux) ((ExcelExportterAux) listaDetallado.get(iProject).getList().get(iOperation)).getList().get(iOperator)).getName();
                    row.createCell(2).setCellValue(((ExcelExportterAux) ((ExcelExportterAux) listaDetallado.get(iProject).getList().get(iOperation)).getList().get(iOperator)).getName());

                    // Colum hours
                    cell = row.createCell(3);


                    cell.setCellStyle(cellstyleSHMM);
                    cell.setCellValue(((ExcelExportterAux) ((ExcelExportterAux) listaDetallado.get(iProject).getList().get(iOperation)).getList().get(iOperator)).getTotalSeconds() / 86400f);
                }
            }
        }

        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(1);
        sheet.autoSizeColumn(2);
    }

    public String exportWorkParts(List<WorkPart> parts, User user, LocalDate from, LocalDate to) {
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

            exporWorkPartsClient(parts, user, wb, from, to);

            exporWorkPartsProject(parts, user, wb, from, to);

            exporWorkPartsUser(parts, user, wb, from, to);

            exporWorkPartsOperations(parts, user, wb, from, to);

            exporWorkPartsDetailed(parts, user, wb, from, to);

            wb.write(fileOut);
            wb.close();
        }
        catch (IOException ioe) {

        }
        return fileName;
    }

}
