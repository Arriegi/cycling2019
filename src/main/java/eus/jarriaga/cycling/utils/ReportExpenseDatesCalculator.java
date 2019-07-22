package eus.jarriaga.cycling.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import eus.jarriaga.cycling.business.entities.Expense;
import eus.jarriaga.cycling.business.entities.Project;
import eus.jarriaga.cycling.business.entities.User;
import eus.jarriaga.cycling.business.repositories.ExpenseRepository;
import eus.jarriaga.cycling.business.repositories.ProjectRepository;
import eus.jarriaga.cycling.business.repositories.UserRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

public class ReportExpenseDatesCalculator {

    private final Log logger = LogFactory.getLog(getClass());

    public final static String REPORT_TYPE_MONTH = "reportMonth";
    public final static String REPORT_TYPE_YEAR = "reportYear";
    public final static String REPORT_TYPE_CUSTOM = "reportCustom";

    private String type, current, from, to, year, filterUserId, filterProjectId;

    private ExpenseRepository expenseRepository;

    private UserRepository userRepository;

    private ProjectRepository projectRepository;

    public ReportExpenseDatesCalculator(ExpenseRepository expenseRepository, UserRepository userRepository, ProjectRepository projectRepository, String type, String current, String from, String to, String year, String filterUserId, String filterProjectId) {
        this.expenseRepository = expenseRepository;
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
        this.type = type;
        this.current = current;
        this.from = from;
        this.to = to;
        this.year = year;
        this.filterUserId = filterUserId;
        this.filterProjectId = filterProjectId;
    }

    public LocalDate getFrom() {
        if (from != null) return LocalDate.parse(from);
        if (current != null) {
            switch(type) {
                case REPORT_TYPE_MONTH:
                    return year != null ? LocalDate.of(Integer.valueOf(year),Integer.valueOf(current),1) :
                            LocalDate.of(LocalDate.now().getYear(),Integer.valueOf(current),1);
                case REPORT_TYPE_YEAR:
                    return LocalDate.of(Integer.valueOf(current),1,1);
                default:
                    return LocalDate.parse(from);
            }
        } else {
            switch(type) {
                case REPORT_TYPE_MONTH:
                    return year != null ? LocalDate.of(Integer.valueOf(year),LocalDate.now().getMonth(),1) :
                            LocalDate.of(LocalDate.now().getYear(),LocalDate.now().getMonth(),1);
                case REPORT_TYPE_YEAR:
                    return LocalDate.of(LocalDate.now().getYear(),1,1);
                default:
                    return LocalDate.parse(from);
            }
        }
    }

    public LocalDate getTo() {
        if (to != null) return LocalDate.parse(to);
        if (current != null) {
            switch(type) {
                case REPORT_TYPE_MONTH:
                    LocalDate lastDayOfMonth = LocalDate.of(year != null ? Integer.valueOf(year) : LocalDate.now().getYear(),Integer.valueOf(current),1) ;
                    return lastDayOfMonth.with(TemporalAdjusters.lastDayOfMonth());
                case REPORT_TYPE_YEAR:
                    return LocalDate.of(Integer.valueOf(current),12,31);
                default:
                    return LocalDate.parse(to);
            }
        } else {
            LocalDate date = LocalDate.of(LocalDate.now().getYear(),LocalDate.now().getMonth(),1);
            switch(type) {
                case REPORT_TYPE_MONTH:
                    return year != null ? date.withYear(Integer.valueOf(year)).with(TemporalAdjusters.lastDayOfMonth()) : date.with(TemporalAdjusters.lastDayOfMonth());
                case REPORT_TYPE_YEAR:
                    return date.with(TemporalAdjusters.lastDayOfYear());
                default:
                    return LocalDate.parse(to);
            }
        }
    }

    public String getJSONExpenses() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        try {
            if (this.filterUserId != null && this.filterUserId != "" && this.filterProjectId != null && this.filterProjectId != "") {
                User user = this.userRepository.findUserById(Long.parseLong(this.filterUserId));
                Project project = this.projectRepository.findProjectById(Long.parseLong(this.filterProjectId));
                return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(expenseRepository.findAllByUserAndProjectAndDateBetweenOrderByDate(user, project, getFrom(),getTo()));
            }
            else if (this.filterProjectId != null && this.filterProjectId != "") {
                Project project = this.projectRepository.findProjectById(Long.parseLong(this.filterProjectId));
                return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(expenseRepository.findAllByProjectAndDateBetweenOrderByDate(project, getFrom(),getTo()));
            }
            else if (this.filterUserId != null && this.filterUserId != "") {
                User user = this.userRepository.findUserById(Long.parseLong(this.filterUserId));
                //return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(expenseRepository.findAllByUserAndDateBetween(user, getFrom(),getTo()));
                return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(expenseRepository.findAllByUserAndDateBetweenOrderByDate(user, getFrom(),getTo()));
            }
            else {
                //return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(expenseRepository.findAllByDateBetween(getFrom(),getTo()));
                //return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(expenseRepository.findAllByDateBetweenOrderByDate(getFrom(),getTo()));
                return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(expenseRepository.findAllByDateBetweenOrderByDateAscUserNameAsc(getFrom(),getTo()));
            }
        } catch(JsonProcessingException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return null;
        }
    }

    public List<Expense> getExpenses() {
        return expenseRepository.findAllByDateBetween(getFrom(),getTo());
    }

}
