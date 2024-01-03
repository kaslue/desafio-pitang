package com.pitang.test.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Entity
@Table(	name = "cars",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = { "licensePlate" , "country" })
        })
public class Car implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 20)
    private String licensePlate;

    @NotNull
    private Integer manufactured_year;

    @NotBlank
    @Size(max = 50)
    private String model;

    @NotBlank
    @Size(max = 20)
    private String color;

    @NotBlank
    @Size(max = 30)
    private String country;

    public Car(){

    }

    public Car(String licensePlate, Integer year, String model, String color, String country) {
        this.licensePlate = licensePlate;
        this.manufactured_year = year;
        this.model = model;
        this.color = color;
        this.country = country;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public Integer getYear() {
        return manufactured_year;
    }

    public void setYear(Integer year) {
        this.manufactured_year = year;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
