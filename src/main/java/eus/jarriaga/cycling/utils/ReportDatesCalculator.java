package eus.jarriaga.cycling.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import eus.jarriaga.cycling.business.entities.WorkPart;
import eus.jarriaga.cycling.business.repositories.WorkPartRepository;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

public class ReportDatesCalculator {

    private final Log logger = LogFactory.getLog(getClass());

    public final static String REPORT_TYPE_MONTH = "reportMonth";
    public final static String REPORT_TYPE_YEAR = "reportYear";
    public final static String REPORT_TYPE_CUSTOM = "reportCustom";

    private String type, current, from, to, year;
    private WorkPartRepository partRepository;

    public ReportDatesCalculator(WorkPartRepository partRepository, String type, String current, String from, String to, String year) {
        this.partRepository = partRepository;
        this.type = type;
        this.current = current;
        this.from = from;
        this.to = to;
        this.year = year;
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

    public String getJSONParts() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(partRepository.findAllByDateBetween(getFrom(),getTo()));
        } catch(JsonProcessingException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            return null;
        }
    }

    public List<WorkPart> getParts() {
        return partRepository.findAllByDateBetween(getFrom(),getTo());
    }

}
