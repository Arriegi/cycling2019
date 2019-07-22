package eus.jarriaga.cycling.business.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Objects;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotNull
    private String name;
    @Column(unique = true)
    @NotNull
    private String email;
    @NotNull
    private String password;
    private Boolean enabled;
    private Boolean tokenExpired;
    private String freeAccess;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id", nullable = false)
    @JsonIgnore
    private Role role;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate registrationDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate lastLoginDate;

    private Long rate;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }
    @JsonProperty
    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean isTokenExpired() {
        return tokenExpired;
    }

    public void setTokenExpired(Boolean tokenExpired) {
        this.tokenExpired = tokenExpired;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }

    public LocalDate getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(LocalDate lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public Long getRate() {
        return rate;
    }

    public void setRate(Long rate) {
        this.rate = rate;
    }

    public String getFreeAccess() {
        return freeAccess;
    }

    public void setFreeAccess(String freeAccess) {
        this.freeAccess = freeAccess;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(name, user.name) &&
                Objects.equals(email, user.email) &&
                Objects.equals(password, user.password) &&
                Objects.equals(role, user.role) &&
                Objects.equals(registrationDate, user.registrationDate) &&
                Objects.equals(lastLoginDate, user.lastLoginDate) &&
                Objects.equals(rate, user.rate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, password, role, registrationDate, lastLoginDate, rate);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", registrationDate=" + registrationDate +
                ", lastLoginDate=" + lastLoginDate +
                ", rate=" + rate +
                '}';
    }
}
