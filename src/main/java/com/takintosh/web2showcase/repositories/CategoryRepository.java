package com.takintosh.web2showcase.repositories;

import com.takintosh.web2showcase.models.CategoryModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CategoryRepository extends JpaRepository<CategoryModel, UUID> {

    CategoryModel findByCategorySlug(String categorySlug);

}
