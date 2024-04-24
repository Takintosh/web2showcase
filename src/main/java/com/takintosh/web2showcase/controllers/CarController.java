package com.takintosh.web2showcase.controllers;

import com.takintosh.web2showcase.dtos.CarRecordDto;
import com.takintosh.web2showcase.models.CarModel;
import com.takintosh.web2showcase.models.CategoryModel;
import com.takintosh.web2showcase.repositories.CarRepository;
import com.takintosh.web2showcase.repositories.CategoryRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/car")
public class CarController {

    private static String filepath = "src/main/resources/static/storage/", extension = "";

    @Autowired
    CarRepository carRepository;
    @Autowired
    CategoryRepository categoryRepository;

    // Get all cars
    @GetMapping("/")
    public ModelAndView getAllCars() {
        ModelAndView mv = new ModelAndView("admin/car/ReadAll");
        List<CarModel> cars = carRepository.findAll();
        List<CategoryModel> categories = categoryRepository.findAll();
        mv.addObject("cars", cars);
        mv.addObject("categories", categories);
        return mv;
    }

    // Add Car form
    @GetMapping("/add")
    public ModelAndView carCreateGet() {
        ModelAndView mv = new ModelAndView("admin/car/Create");
        List<CategoryModel> categories = categoryRepository.findAll();
        mv.addObject("categories", categories);
        return mv;
    }

    // Add Car
    @PostMapping("/add")
    public String carCreatePost(@Valid @ModelAttribute CarRecordDto carRecordDto, BindingResult result,
                                @RequestParam("file") MultipartFile carImage) {

        if (result.hasErrors()) {
            return "redirect:/car/add";
        }

        CarModel carModel = new CarModel();
        BeanUtils.copyProperties(carRecordDto, carModel);

        CategoryModel category = categoryRepository.findById(carRecordDto.categoryId()).get();
        carModel.setCategory(category);

        carModel = carRepository.saveAndFlush(carModel);
        try {
            if (!carImage.isEmpty()) {
                Path originalPath = Paths.get(carImage.getOriginalFilename());
                extension = originalPath.getFileName().toString();
                extension = extension.substring(extension.lastIndexOf(".") + 1);

                Path path = Paths.get(filepath + String.valueOf(carModel.getCarId()) + "." + extension);
                Files.write(path, carImage.getBytes());

                carModel.setCarImage(String.valueOf(carModel.getCarId()) + "." + extension);
            } else {
                carModel.setCarImage("default.jpg");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        carRepository.save(carModel);

        return "redirect:/car/";
    }

    // Update Car form
    @GetMapping("/update/{id}")
    public ModelAndView carUpdateGet(@PathVariable(value = "id") UUID id) {
        ModelAndView mv = new ModelAndView("admin/car/Update");
        Optional<CarModel> car = carRepository.findById(id);
        List<CategoryModel> categories = categoryRepository.findAll();

        mv.addObject("carModel", car.get().getCarModel());
        mv.addObject("carBrand", car.get().getCarBrand());
        mv.addObject("carColor", car.get().getCarColor());
        mv.addObject("carPlate", car.get().getCarPlate());
        mv.addObject("carImage", car.get().getCarImage());
        mv.addObject("carCategory", car.get().getCategory());
        mv.addObject("categories", categories);
        return mv;
    }

    // Update Car
    @PostMapping("/update/{id}")
    public String carUpdatePost(@PathVariable(value = "id") UUID id,
                                @Valid @ModelAttribute CarRecordDto carRecordDto, BindingResult result,
                                @RequestParam("file") MultipartFile carImage) {

        Optional<CarModel> car = carRepository.findById(id);
        if (car.isEmpty()) {
            return "redirect:/car/";
        }
        if (result.hasErrors()) {
            return "admin/car/Update";
        }

        CarModel carModel = car.get();
        BeanUtils.copyProperties(carRecordDto, carModel);

        CategoryModel category = categoryRepository.findById(carRecordDto.categoryId()).get();
        carModel.setCategory(category);

        try {
            if (!carImage.isEmpty()) {
                Path originalPath = Paths.get(carImage.getOriginalFilename());
                extension = originalPath.getFileName().toString();
                extension = extension.substring(extension.lastIndexOf(".") + 1);

                Path path = Paths.get(filepath + String.valueOf(carModel.getCarId()) + "." + extension);
                Files.write(path, carImage.getBytes());

                carModel.setCarImage(String.valueOf(carModel.getCarId()) + "." + extension);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        carRepository.save(carModel);
        return "redirect:/car/";
    }

    // Delete Car
    @GetMapping("/delete/{id}")
    public String carDelete(@PathVariable(value = "id") UUID id) {

        Optional<CarModel> car = carRepository.findById(id);
        if (car.isEmpty()) {
            return "redirect:/car/";
        }
        carRepository.delete(car.get());
        return "redirect:/car/";
    }

    // Get Car by Category
    @GetMapping("/category/{id}")
    public ModelAndView carCategory(@PathVariable(value = "id") UUID id) {
        ModelAndView mv = new ModelAndView("admin/car/ReadAll");

        CategoryModel category = categoryRepository.findById(id).orElse(null);
        List<CarModel> cars = carRepository.findAllByCategory(category);
        mv.addObject("cars", cars);

        List<CategoryModel> categories = categoryRepository.findAll();
        mv.addObject("categories", categories);
        return mv;
    }


}