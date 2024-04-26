package com.takintosh.web2showcase.controllers;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.takintosh.web2showcase.models.CarModel;
import com.takintosh.web2showcase.models.CategoryModel;
import com.takintosh.web2showcase.repositories.CarRepository;
import com.takintosh.web2showcase.repositories.CategoryRepository;

@Controller
public class UserFrontController {
	
	@Autowired
	CarRepository carRepository;
	@Autowired
	CategoryRepository categoryRepository;
	
	// Get all cars
    @GetMapping("/")
    public ModelAndView getAllCars() {
        ModelAndView mv = new ModelAndView("user/ReadAll");
        List<CarModel> cars = carRepository.findAll();
        List<CategoryModel> categories = categoryRepository.findAll();
        mv.addObject("cars", cars);
        mv.addObject("categories", categories);
        return mv;
    }
    
    // Get Car by Category
    @GetMapping("/category/{id}")
    public ModelAndView carCategory(@PathVariable(value = "id") UUID id) {
        ModelAndView mv = new ModelAndView("user/ReadAll");

        CategoryModel category = categoryRepository.findById(id).orElse(null);
        List<CarModel> cars = carRepository.findAllByCategory(category);
        mv.addObject("cars", cars);

        List<CategoryModel> categories = categoryRepository.findAll();
        mv.addObject("categories", categories);
        return mv;
    }
    
    // Search Car
    @PostMapping("/search")
    public ModelAndView carSearch(@RequestParam("search") String search) {
        ModelAndView mv = new ModelAndView("user/ReadAll");

        String searchQuery = "%" + search + "%";
        List<CarModel> cars = carRepository.findAllByCarModelLikeIgnoreCaseOrCarBrandLikeIgnoreCase(searchQuery, searchQuery);
        mv.addObject("cars", cars);

        List<CategoryModel> categories = categoryRepository.findAll();
        mv.addObject("categories", categories);
        return mv;
    }

}
