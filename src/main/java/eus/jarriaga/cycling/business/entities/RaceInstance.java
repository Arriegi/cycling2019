package eus.jarriaga.cycling.business.entities;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Objects;

@Entity
public class RaceInstance {

    @NotNull
    private Race race;
    @NotNull
    private Integer year;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    private Integer stageCount=1;

    public Race getRace() {
        return race;
    }

    public void setRace(Race race) {
        this.race = race;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Integer getStageCount() {
        return stageCount;
    }

    public void setStageCount(Integer stageCount) {
        this.stageCount = stageCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RaceInstance that = (RaceInstance) o;
        return race.equals(that.race) &&
                year.equals(that.year) &&
                startDate.equals(that.startDate) &&
                endDate.equals(that.endDate) &&
                stageCount.equals(that.stageCount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(race, year, startDate, endDate, stageCount);
    }
}
