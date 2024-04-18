package com.takintosh.web2showcase.repositories;

import com.takintosh.web2showcase.models.CarModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CarRepository extends JpaRepository<CarModel, UUID> {

}
