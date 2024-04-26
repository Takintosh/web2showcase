package com.takintosh.web2showcase.models;

import jakarta.persistence.*;

import java.util.UUID;


@Entity
@Table(name = "TB_CATEGORY")
public class CategoryModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID categoryId;

    @Column(name = "name", nullable = false, unique = true, columnDefinition = "VARCHAR(50)")
    private String categoryName;

    @Column(name = "slug", nullable = false, unique = true, columnDefinition = "VARCHAR(50)")
    private String categorySlug;


    // Getters and Setters
    public UUID getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(UUID categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategorySlug() {
        return categorySlug;
    }
    public void setCategorySlug(String categorySlug) {
        this.categorySlug = categorySlug;
    }

}
