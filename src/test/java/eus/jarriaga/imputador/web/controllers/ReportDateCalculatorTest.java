package eus.jarriaga.imputador.web.controllers;

import eus.jarriaga.cycling.business.BusinessConfig;
import eus.jarriaga.cycling.business.repositories.WorkPartRepository;
import eus.jarriaga.cycling.utils.ReportDatesCalculator;
import eus.jarriaga.cycling.web.WebConfig;
import eus.jarriaga.cycling.web.security.WebSecurityConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAdjusters;

import static junit.framework.TestCase.assertTrue;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {WebSecurityConfig.class, WebConfig.class, BusinessConfig.class})
@WebAppConfiguration
public class ReportDateCalculatorTest {

    @Autowired
    WorkPartRepository partRepository;

    @Test
    public void testDatesMonth() {
        ReportDatesCalculator calculator = new ReportDatesCalculator(partRepository,ReportDatesCalculator.REPORT_TYPE_MONTH,null,null,null, null);
        assertTrue(calculator.getFrom().isEqual(LocalDate.now().with(TemporalAdjusters.firstDayOfMonth())));
        assertTrue(calculator.getTo().isEqual(LocalDate.now().with(TemporalAdjusters.lastDayOfMonth())));
    }

    @Test
    public void testDatesCurrentMonth() {
        LocalDate now = LocalDate.now();
        ReportDatesCalculator calculator = new ReportDatesCalculator(partRepository,ReportDatesCalculator.REPORT_TYPE_MONTH,"2",null,null, null);
        assertTrue(calculator.getFrom().isEqual(LocalDate.of(now.getYear(),2,1)));
        assertTrue(calculator.getTo().isEqual(LocalDate.of(now.getYear(),2,1).with(TemporalAdjusters.lastDayOfMonth())));

        calculator = new ReportDatesCalculator(partRepository,ReportDatesCalculator.REPORT_TYPE_MONTH,"2",null,null, "2017");
        assertTrue(calculator.getFrom().isEqual(LocalDate.of(2017,2,1)));
        assertTrue(calculator.getTo().isEqual(LocalDate.of(2017,2,1).with(TemporalAdjusters.lastDayOfMonth())));
    }

    @Test
    public void testDatesYear() {
        ReportDatesCalculator calculator = new ReportDatesCalculator(partRepository,ReportDatesCalculator.REPORT_TYPE_YEAR,null,null,null, null);
        assertTrue(calculator.getFrom().isEqual(LocalDate.now().with(TemporalAdjusters.firstDayOfYear())));
        assertTrue(calculator.getTo().isEqual(LocalDate.now().with(TemporalAdjusters.lastDayOfYear())));
    }

    @Test
    public void testDatesCurrentYear() {
        ReportDatesCalculator calculator = new ReportDatesCalculator(partRepository,ReportDatesCalculator.REPORT_TYPE_YEAR,"2016",null,null, null);
        assertTrue(calculator.getFrom().isEqual(LocalDate.of(2016,1,1)));
        assertTrue(calculator.getTo().isEqual(LocalDate.of(2016,12,31)));
    }

    @Test
    public void testDateFormat() {
        ReportDatesCalculator calculator = new ReportDatesCalculator(partRepository,ReportDatesCalculator.REPORT_TYPE_CUSTOM,null,
                "2018-04-01","2019-03-23", null);
        assertTrue(calculator.getFrom().isEqual(LocalDate.of(2018,4,1)));
        assertTrue(calculator.getTo().isEqual(LocalDate.of(2019,3,23)));
    }

    @Test(expected = DateTimeParseException.class)
    public void testDateFormatError() {
        ReportDatesCalculator calculator = new ReportDatesCalculator(partRepository,ReportDatesCalculator.REPORT_TYPE_CUSTOM,null,
                "01/04/2018","23/03/2019", null);
        assertTrue(calculator.getTo().isEqual(LocalDate.of(2019,3,23)));
    }


}
