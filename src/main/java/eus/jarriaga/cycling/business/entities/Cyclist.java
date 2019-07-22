package eus.jarriaga.cycling.business.entities;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Objects;

@Entity
public class Cyclist {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String name;
    @NotNull
    private String surname;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    @ManyToOne
    @JoinColumn(name="country_id")
    private Country country;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cyclist cyclist = (Cyclist) o;
        return id.equals(cyclist.id) &&
                name.equals(cyclist.name) &&
                surname.equals(cyclist.surname) &&
                birthDate.equals(cyclist.birthDate) &&
                country.equals(cyclist.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, surname, birthDate, country);
    }
}
