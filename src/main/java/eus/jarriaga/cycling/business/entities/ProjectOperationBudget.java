package eus.jarriaga.cycling.business.entities;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Duration;
import java.util.Objects;

@Entity
public class ProjectOperationBudget {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="operation_id", nullable = false)
    private Operation operation;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @NotNull
    private Duration duration;

    private Long rate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Operation getOperation() {
        return operation;
    }

    public void setOperation(Operation operation) {
        this.operation = operation;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public Long getRate() {
        return rate;
    }

    public void setRate(Long rate) {
        this.rate = rate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectOperationBudget that = (ProjectOperationBudget) o;
        return id.equals(that.id) &&
                operation.equals(that.operation) &&
                project.equals(that.project) &&
                duration.equals(that.duration) &&
                rate.equals(that.rate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, operation, project, duration, rate);
    }

    @Override
    public String toString() {
        return "ProjectOperationBudget{" +
                "id=" + id +
                ", operation=" + operation +
                ", project=" + project +
                ", duration=" + duration +
                ", rate=" + rate +
                '}';
    }
}
