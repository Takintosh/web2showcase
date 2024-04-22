package com.takintosh.web2showcase.models;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "TB_CAR")
public class CarModel {

    @ManyToOne
    @JoinColumn(name = "categoryId", nullable = false)
    private CategoryModel category;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID carId;

    @Column(name = "model", nullable = false, unique = false, columnDefinition = "VARCHAR(50)")
    private String carModel;

    @Column(name = "brand", nullable = false, unique = false, columnDefinition = "VARCHAR(50)")
    private String carBrand;

    @Column(name = "plate", nullable = false, unique = true, columnDefinition = "VARCHAR(50)")
    private String carPlate;

    @Column(name = "color", nullable = true, unique = false, columnDefinition = "VARCHAR(50)")
    private String carColor;

    @Column(name = "image", nullable = false, unique = true, columnDefinition = "VARCHAR(50)")
    private String carImage;


    // Getters and Setters
    public CategoryModel getCategory() {
        return category;
    }
    public void setCategory(CategoryModel category) {
        this.category = category;
    }

    public UUID getCarId() {
        return carId;
    }
    public void setCarId(UUID carId) {
        this.carId = carId;
    }

    public String getCarModel() {
        return carModel;
    }
    public void setCarModel(String carModel) {
        this.carModel = carModel;
    }

    public String getCarBrand() {
        return carBrand;
    }
    public void setCarBrand(String carBrand) {
        this.carBrand = carBrand;
    }

    public String getCarPlate() {
        return carPlate;
    }
    public void setCarPlate(String carPlate) {
        this.carPlate = carPlate;
    }

    public String getCarColor() {
        return carColor;
    }
    public void setCarColor(String carColor) {
        this.carColor = carColor;
    }

    public String getCarImage() {
        return carImage;
    }
    public void setCarImage(String carImage) {
        this.carImage = carImage;
    }

}
