package com.tuwaiq.jpa_e_commerce.Controller;

import Api.ApiResponse;
import com.tuwaiq.jpa_e_commerce.Model.Category;
import com.tuwaiq.jpa_e_commerce.Service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping("/add")
    public ResponseEntity<?> addCategory(@RequestBody @Valid Category category, Errors errors){
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }
        else {
            categoryService.addCategory(category);
            return ResponseEntity.status(200).body(new ApiResponse("The category have been added successfully"));
        }
    }

    @GetMapping("/get")
    public ResponseEntity<?> getCategories(){
        List<Category> categories = categoryService.getCategories();
        if (categories.isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("There are no categories to show"));
        }
        else {
            return ResponseEntity.status(200).body(categories);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable Integer id, @RequestBody @Valid Category category, Errors errors){
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }
        if (categoryService.updateCategory(id,category)){
            return ResponseEntity.status(200).body(new ApiResponse("The category have been updated successfully"));
        }
        else {
            return ResponseEntity.status(400).body(new ApiResponse("There is no category with that id found"));
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable Integer id){
        if (categoryService.deleteCategory(id)){
            return ResponseEntity.status(200).body(new ApiResponse("The category have been deleted successfully"));
        }
        else {
            return ResponseEntity.status(400).body(new ApiResponse("There are no category with that id found"));
        }
    }
}
