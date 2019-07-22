package eus.jarriaga.cycling.business.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
public class GeneralConfiguration {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private String idConfiguration;

    @NotNull
    private String description;

    private String value;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdConfiguration() {
        return idConfiguration;
    }

    public void setIdConfiguration(String idConfiguration) {
        this.idConfiguration = idConfiguration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GeneralConfiguration that = (GeneralConfiguration) o;
        return id.equals(that.id) &&
                idConfiguration.equals(that.idConfiguration) &&
                description.equals(that.description) &&
                Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idConfiguration, description, value);
    }

    @Override
    public String toString() {
        return "GeneralConfiguration{" +
                "id=" + id +
                ", idConfiguration='" + idConfiguration + '\'' +
                ", description='" + description + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
