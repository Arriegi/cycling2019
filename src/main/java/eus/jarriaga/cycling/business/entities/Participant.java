package eus.jarriaga.cycling.business.entities;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
public class Participant {

    @NotNull
    private Cyclist cyclist;
    @NotNull
    private RaceInstance Instance;

    public Cyclist getCyclist() {
        return cyclist;
    }

    public void setCyclist(Cyclist cyclist) {
        this.cyclist = cyclist;
    }

    public RaceInstance getInstance() {
        return Instance;
    }

    public void setInstance(RaceInstance instance) {
        Instance = instance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Participant that = (Participant) o;
        return cyclist.equals(that.cyclist) &&
                Instance.equals(that.Instance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cyclist, Instance);
    }
}

