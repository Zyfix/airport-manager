package com.zyfix.airportmanager.data.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.persistence.*;



import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "airplane")
@Data
@NoArgsConstructor
public class Airplane {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(updatable = false)
    private UUID id;


    @Column(name = "manufacturer")
    @NotNull
    @NotBlank(message = "Airplane manufacturer cannot be blank!")
    private String manufacturer;


    @Column(name = "model")
    @NotNull
    @NotBlank(message = "Airplane model cannot be blank!")
    private String model;

    @Column(name = "capacity")
    @NotNull
    private Long capacity;

    public Airplane(String manufacturer, String model, int capacity) {
        this.manufacturer = manufacturer;
        this.model = model;
        this.capacity = (long) capacity;
    }

    public Airplane(String manufacturer, String model, Long capacity) {
        this.manufacturer = manufacturer;
        this.model = model;
        this.capacity = capacity;
    }


}