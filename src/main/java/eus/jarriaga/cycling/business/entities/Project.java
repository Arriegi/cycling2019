package eus.jarriaga.cycling.business.entities;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDate;
import java.util.Objects;

@Entity
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotEmpty(message = "{error.name.notEmpty}")
    private String name;

    private Boolean acceptedBudget;

    private Boolean archived;

    @ManyToOne
    @JoinColumn(name="client_id")
    private Client client;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate dischargeDate;

    private String note;

    private float presupProveedorImporte;
    private float presupProveedorIncremento;

    private float realProveedorImporte;
    private float realProveedorIncremento;

    private String code;
    private String subcode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getAcceptedBudget() {
        return acceptedBudget;
    }

    public void setAcceptedBudget(Boolean acceptedBudget) {
        this.acceptedBudget = acceptedBudget;
    }

    public Boolean getArchived() {
        return archived;
    }

    public void setArchived(Boolean archived) {
        this.archived = archived;
    }

    public LocalDate getDischargeDate() {
        return dischargeDate;
    }

    public void setDischargeDate(LocalDate dischargeDate) {
        this.dischargeDate = dischargeDate;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public float getPresupProveedorImporte() {
        return presupProveedorImporte;
    }

    public void setPresupProveedorImporte(float presupProveedorImporte) {
        this.presupProveedorImporte = presupProveedorImporte;
    }

    public float getPresupProveedorIncremento() {
        return presupProveedorIncremento;
    }

    public void setPresupProveedorIncremento(float presupProveedorIncremento) {
        this.presupProveedorIncremento = presupProveedorIncremento;
    }

    public float getRealProveedorImporte() {
        return realProveedorImporte;
    }

    public void setRealProveedorImporte(float realProveedorImporte) {
        this.realProveedorImporte = realProveedorImporte;
    }

    public float getRealProveedorIncremento() {
        return realProveedorIncremento;
    }

    public void setRealProveedorIncremento(float realProveedorIncremento) {
        this.realProveedorIncremento = realProveedorIncremento;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getSubcode() {
        return subcode;
    }

    public void setSubcode(String subcode) {
        this.subcode = subcode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;
        return Float.compare(project.presupProveedorImporte, presupProveedorImporte) == 0 &&
                Float.compare(project.presupProveedorIncremento, presupProveedorIncremento) == 0 &&
                Float.compare(project.realProveedorImporte, realProveedorImporte) == 0 &&
                Float.compare(project.realProveedorIncremento, realProveedorIncremento) == 0 &&
                id.equals(project.id) &&
                name.equals(project.name) &&
                acceptedBudget.equals(project.acceptedBudget) &&
                archived.equals(project.archived) &&
                client.equals(project.client) &&
                dischargeDate.equals(project.dischargeDate) &&
                Objects.equals(note, project.note) &&
                code.equals(project.code) &&
                subcode.equals(project.subcode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, acceptedBudget, archived, client, dischargeDate, note, presupProveedorImporte, presupProveedorIncremento, realProveedorImporte, realProveedorIncremento, code, subcode);
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", acceptedBudget=" + acceptedBudget +
                ", archived=" + archived +
                ", client=" + client +
                ", dischargeDate=" + dischargeDate +
                ", note='" + note + '\'' +
                ", presupProveedorImporte=" + presupProveedorImporte +
                ", presupProveedorIncremento=" + presupProveedorIncremento +
                ", realProveedorImporte=" + realProveedorImporte +
                ", realProveedorIncremento=" + realProveedorIncremento +
                ", code='" + code + '\'' +
                ", subcode='" + subcode + '\'' +
                '}';
    }
}