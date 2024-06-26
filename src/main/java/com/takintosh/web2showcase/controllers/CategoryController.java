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
@RequestMapping("/admin/category")
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
            return "redirect:/admin/category/";
        }
        CategoryModel categoryModel = new CategoryModel();
        BeanUtils.copyProperties(categoryRecordDto, categoryModel);
        categoryModel.setCategorySlug(generateSlugFromName(categoryModel.getCategoryName()));

        categoryRepository.save(categoryModel);
        return "redirect:/admin/category/";
    }

    // Delete Category
    @GetMapping("/delete/{id}")
    public String deleteCategory(@PathVariable(value="id") UUID id) {

        Optional<CategoryModel> category = categoryRepository.findById(id);
        if(category.isEmpty()) {
            return "redirect:/admin/category/";
        }
        categoryRepository.delete(category.get());
        return "redirect:/admin/category/";
    }

    // Update Category
    @PostMapping("/update/{id}")
    public String updateCategory(@Valid @ModelAttribute CategoryRecordDto categoryRecordDto, BindingResult result, @PathVariable(value="id") UUID id) {

        Optional<CategoryModel> categoryO = categoryRepository.findById(id);
        if(categoryO.isEmpty() || result.hasErrors()) {
            return "redirect:/admin/category/";
        }
        CategoryModel categoryModel = categoryO.get();
        BeanUtils.copyProperties(categoryRecordDto, categoryModel);
        categoryModel.setCategorySlug(generateSlugFromName(categoryModel.getCategoryName()));

        categoryRepository.save(categoryModel);
        return "redirect:/admin/category/";
    }

    public String generateSlugFromName(String name) {
        String slug = name.replaceAll("\\s+", "-");
        slug = slug.toLowerCase();
        return slug;
    }


}
