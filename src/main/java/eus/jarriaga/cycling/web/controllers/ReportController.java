package eus.jarriaga.cycling.web.controllers;

import eus.jarriaga.cycling.business.entities.Project;
import eus.jarriaga.cycling.business.entities.User;
import eus.jarriaga.cycling.business.repositories.ExpenseRepository;
import eus.jarriaga.cycling.business.repositories.ProjectRepository;
import eus.jarriaga.cycling.business.repositories.UserRepository;
import eus.jarriaga.cycling.business.repositories.WorkPartRepository;
import eus.jarriaga.cycling.utils.ExcelExpenseExportter;
import eus.jarriaga.cycling.utils.ExcelExportter;
import eus.jarriaga.cycling.utils.ReportDatesCalculator;
import eus.jarriaga.cycling.utils.ReportExpenseDatesCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@RestController
@RequestMapping("api/v1")
public class ReportController {
    @Autowired
    private WorkPartRepository workPartRepository;

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @GetMapping("/reports/download/excel")
    //public @ResponseBody byte[] getReportExcel(Principal principal, HttpServletResponse response) throws IOException {
    public void getReportExcel(Principal principal, HttpServletResponse response
            , @RequestParam(value = "type", required = false, defaultValue = ReportDatesCalculator.REPORT_TYPE_MONTH) String reportTimeType
            , @RequestParam(value = "current", required = false) String current
            , @RequestParam(value = "year", required = false) String year
            , @RequestParam(value = "from", required = false) String from
            , @RequestParam(value = "to", required = false) String to
    ) throws IOException {

        ReportDatesCalculator calculator = new ReportDatesCalculator(workPartRepository, reportTimeType, current, from, to, year);

        ExcelExportter excelExportter = new ExcelExportter();
        User user = userRepository.findUserByEmail(principal.getName());
        LocalDate ldFrom = calculator.getFrom();
        LocalDate ldTo = calculator.getTo();
        String file = excelExportter.exportWorkParts(workPartRepository.findAllByDateBetween(calculator.getFrom(), calculator.getTo()), user, ldFrom, ldTo);

        File fileDownload = new File(file);

        InputStream is = new FileInputStream(fileDownload);

        String filenameDownload = "informe";
        switch(reportTimeType) {
            case ReportDatesCalculator.REPORT_TYPE_MONTH:
                filenameDownload = "informe";
                Locale spanishLocale = new Locale("es", "ES");
                if (current == null) {
                    filenameDownload += "_" + LocalDate.now().format(DateTimeFormatter.ofPattern("MMMM", spanishLocale));
                }
                else {
                    filenameDownload += "_" + (year != null ? LocalDate.of(Integer.valueOf(year),Integer.valueOf(current),1) :
                            LocalDate.of(LocalDate.now().getYear(),Integer.valueOf(current),1)).format(DateTimeFormatter.ofPattern("MMMM", spanishLocale));
                }
                if (year == null) {
                    filenameDownload += "_" + LocalDate.now().getYear();
                }
                else {
                    filenameDownload += "_" + year;
                }

                break;
            case ReportDatesCalculator.REPORT_TYPE_YEAR:
                filenameDownload = "informe";
                if (year == null) {
                    filenameDownload += "_" + LocalDate.now().getYear();
                }
                else {
                    filenameDownload += "_" + year;
                }
                break;
            default:
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-y");
                filenameDownload = "informe";
                //filenameDownload += "_" + from;
                //filenameDownload += "_" + to;
                filenameDownload += "_" + ldFrom.format(formatter).toString();
                filenameDownload += "_" + ldTo.format(formatter).toString();
                break;
        }

        response.setContentType("application/vnd.ms-excel");
        response.addHeader("Content-Disposition", "attachment; filename=" + filenameDownload +".xls");
        org.apache.commons.io.IOUtils.copy(is, response.getOutputStream());

        is.close();
        fileDownload.delete();

    }

    @GetMapping("/reports/download/excelExpense")
    //public @ResponseBody byte[] getReportExcel(Principal principal, HttpServletResponse response) throws IOException {
    public void getReportExcelExpense(Principal principal, HttpServletResponse response
            , @RequestParam(value = "type", required = false, defaultValue = ReportDatesCalculator.REPORT_TYPE_MONTH) String reportTimeType
            , @RequestParam(value = "current", required = false) String current
            , @RequestParam(value = "year", required = false) String year
            , @RequestParam(value = "from", required = false) String from
            , @RequestParam(value = "to", required = false) String to
            , @RequestParam(value = "filterUserId", required = false) String filterUserId
            , @RequestParam(value = "filterProjectId", required = false) String filterProjectId
            ) throws IOException {

        ReportExpenseDatesCalculator calculator = new ReportExpenseDatesCalculator(expenseRepository, userRepository, projectRepository, reportTimeType, current, from, to, year, filterUserId, filterProjectId);

        ExcelExpenseExportter excelExpenseExportter = new ExcelExpenseExportter();
        User user = userRepository.findUserByEmail(principal.getName());

        LocalDate ldFrom = calculator.getFrom();
        LocalDate ldTo = calculator.getTo();
        String file = null;
        if (filterUserId != null && filterUserId != "" && filterProjectId != null && filterProjectId != "") {
            User filterUser = userRepository.findUserById(Long.parseLong(filterUserId));
            Project filterProject = projectRepository.findProjectById(Long.parseLong(filterProjectId));
            file = excelExpenseExportter.exportExpenses(expenseRepository.findAllByUserAndProjectAndDateBetweenOrderByDate(filterUser, filterProject, calculator.getFrom(), calculator.getTo()), user, ldFrom, ldTo);
        }
        else if (filterProjectId != null && filterProjectId != "") {
            Project filterProject = projectRepository.findProjectById(Long.parseLong(filterProjectId));
            file = excelExpenseExportter.exportExpenses(expenseRepository.findAllByProjectAndDateBetweenOrderByDate(filterProject, calculator.getFrom(), calculator.getTo()), user, ldFrom, ldTo);
        }
        else if (filterUserId != null && filterUserId != "") {
            User filterUser = userRepository.findUserById(Long.parseLong(filterUserId));
            file = excelExpenseExportter.exportExpenses(expenseRepository.findAllByUserAndDateBetweenOrderByDate(filterUser, calculator.getFrom(), calculator.getTo()), user, ldFrom, ldTo);
        }
        else {
            file = excelExpenseExportter.exportExpenses(expenseRepository.findAllByDateBetweenOrderByDateAscUserNameAsc(calculator.getFrom(), calculator.getTo()), user, ldFrom, ldTo);
            //file = excelExpenseExportter.exportExpenses(expenseRepository.findAllByDateBetweenOrderByDateAscUserAsc(calculator.getFrom(), calculator.getTo()), user, ldFrom, ldTo);
        }

        File fileDownload = new File(file);

        InputStream is = new FileInputStream(fileDownload);

        String filenameDownload = "informe";
        switch(reportTimeType) {
            case ReportDatesCalculator.REPORT_TYPE_MONTH:
                filenameDownload = "informeGastos";
                Locale spanishLocale = new Locale("es", "ES");
                if (current == null) {
                    filenameDownload += "_" + LocalDate.now().format(DateTimeFormatter.ofPattern("MMMM", spanishLocale));
                }
                else {
                    filenameDownload += "_" + (year != null ? LocalDate.of(Integer.valueOf(year),Integer.valueOf(current),1) :
                            LocalDate.of(LocalDate.now().getYear(),Integer.valueOf(current),1)).format(DateTimeFormatter.ofPattern("MMMM", spanishLocale));
                }
                if (year == null) {
                    filenameDownload += "_" + LocalDate.now().getYear();
                }
                else {
                    filenameDownload += "_" + year;
                }

                break;
            case ReportDatesCalculator.REPORT_TYPE_YEAR:
                filenameDownload = "informeGastos";
                if (year == null) {
                    filenameDownload += "_" + LocalDate.now().getYear();
                }
                else {
                    filenameDownload += "_" + year;
                }
                break;
            default:
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-y");
                filenameDownload = "informeGastos";
                //filenameDownload += "_" + from;
                //filenameDownload += "_" + to;
                filenameDownload += "_" + ldFrom.format(formatter).toString();
                filenameDownload += "_" + ldTo.format(formatter).toString();
                break;
        }

        response.setContentType("application/vnd.ms-excel");
        response.addHeader("Content-Disposition", "attachment; filename=" + filenameDownload +".xls");
        org.apache.commons.io.IOUtils.copy(is, response.getOutputStream());

        is.close();
        fileDownload.delete();

    }

}
