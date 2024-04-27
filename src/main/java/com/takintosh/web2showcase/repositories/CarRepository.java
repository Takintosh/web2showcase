package com.takintosh.web2showcase.repositories;

import com.takintosh.web2showcase.models.CarModel;
import com.takintosh.web2showcase.models.CategoryModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CarRepository extends JpaRepository<CarModel, UUID> {

    List<CarModel> findAllByCategory(CategoryModel category);
    List<CarModel> findAllByCarModelLikeIgnoreCaseOrCarBrandLikeIgnoreCase(String carModel, String carBrand);
    List<CarModel> findAllByCarModelLikeIgnoreCaseOrCarBrandLikeIgnoreCaseOrCarYearEquals(String carModel, String carBrand, Integer carYear);

}