package eus.jarriaga.cycling.business.entities;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Duration;
import java.time.LocalDate;
import java.util.Objects;

@Entity
public class WorkPart {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="operation_id", nullable = false)
    private Operation operation;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;

    @NotNull
    private Duration duration;

    private String comment;

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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkPart workPart = (WorkPart) o;
        return Objects.equals(id, workPart.id) &&
                Objects.equals(operation, workPart.operation) &&
                Objects.equals(user, workPart.user) &&
                Objects.equals(project, workPart.project) &&
                Objects.equals(date, workPart.date) &&
                Objects.equals(duration, workPart.duration) &&
                Objects.equals(comment, workPart.comment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, operation, user, project, date, duration, comment);
    }

    @Override
    public String toString() {
        return "WorkPart{" +
                "id=" + id +
                ", operation=" + operation +
                ", user=" + user +
                ", project=" + project +
                ", date=" + date +
                ", duration=" + duration +
                ", comment='" + comment + '\'' +
                '}';
    }
}
