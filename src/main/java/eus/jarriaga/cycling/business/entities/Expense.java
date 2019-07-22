package eus.jarriaga.cycling.business.entities;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Objects;

@Entity
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

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
    private Float ammount;

    private String comment;

    private int type;

    private Float rate;

    private Float distancia;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public Float getAmmount() {
        return ammount;
    }

    public void setAmmount(Float ammount) {
        this.ammount = ammount;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Float getRate() {
        return rate;
    }

    public void setRate(Float rate) {
        this.rate = rate;
    }

    public Float getDistancia() {
        return distancia;
    }

    public void setDistancia(Float distancia) {
        this.distancia = distancia;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Expense expense = (Expense) o;
        return type == expense.type &&
                rate == expense.rate &&
                distancia == expense.distancia &&
                id.equals(expense.id) &&
                user.equals(expense.user) &&
                project.equals(expense.project) &&
                date.equals(expense.date) &&
                ammount.equals(expense.ammount) &&
                comment.equals(expense.comment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, project, date, ammount, comment, type, rate, distancia);
    }

    @Override
    public String toString() {
        return "Expense{" +
                "id=" + id +
                ", user=" + user +
                ", project=" + project +
                ", date=" + date +
                ", ammount=" + ammount +
                ", comment='" + comment + '\'' +
                ", type=" + type +
                ", rate=" + rate +
                ", distancia=" + distancia +
                '}';
    }
}
