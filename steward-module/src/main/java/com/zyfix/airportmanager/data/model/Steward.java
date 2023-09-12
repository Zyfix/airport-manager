package com.zyfix.airportmanager.data.model;

import jakarta.validation.constraints.Past;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.persistence.*;



import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "stewards")
@Data
@NoArgsConstructor
public class Steward {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false)
    private UUID id;

    @NotNull
    @NotBlank(message = "Stewards first name cannot be blank!")
    @Column(name = "first_name")
    private String firstName;

    @NotNull
    @NotBlank(message = "Stewards family name cannot be blank!")
    @Column(name = "last_name")
    private String lastName;

    @NotNull
    @Past
    @Column(name = "birth_date")
    private LocalDate dateOfBirth;

    @NotNull
    @Column(name = "hire_date")
    private LocalDate hireDate;

    public Steward(String firstName, String lastName, LocalDate dateOfBirth, LocalDate hireDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.hireDate = hireDate;
    }
}