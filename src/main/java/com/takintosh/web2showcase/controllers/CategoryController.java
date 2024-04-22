package com.takintosh.web2showcase.controllers;

import com.takintosh.web2showcase.dtos.CategoryRecordDto;
import com.takintosh.web2showcase.models.CategoryModel;
import com.takintosh.web2showcase.repositories.CategoryRepository;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    CategoryRepository categoryRepository;

    // Get all categories
    @GetMapping("/")
    public ModelAndView getAllCategories() {
        ModelAndView mv = new ModelAndView("admin/category/ReadAll");
        List<CategoryModel> categories = categoryRepository.findAll();
        mv.addObject("categories", categories);
        return mv;
    }

    // Add Category
    @PostMapping("/add")
    public String categoryCreate(@Valid @ModelAttribute CategoryRecordDto categoryRecordDto, BindingResult result) {
        if (result.hasErrors()) {
            return "redirect:/category/";
        }
        CategoryModel categoryModel = new CategoryModel();
        BeanUtils.copyProperties(categoryRecordDto, categoryModel);
        categoryRepository.save(categoryModel);
        return "redirect:/category/";
    }

    // Delete Category
    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable(value="id") UUID id) {

        Optional<CategoryModel> courseO = categoryRepository.findById(id);
        if(courseO.isEmpty()) {
            return "redirect:/category/";
        }
        categoryRepository.delete(courseO.get());
        return "redirect:/category/";
    }


}
