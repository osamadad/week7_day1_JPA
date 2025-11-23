package com.tuwaiq.jpa_e_commerce.Controller;

import Api.ApiResponse;
import com.tuwaiq.jpa_e_commerce.Model.Product;
import com.tuwaiq.jpa_e_commerce.Service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/add")
    public ResponseEntity<?> addProduct(@RequestBody @Valid Product product, Errors errors){
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }
        else {
            if (productService.addProduct(product)){
                return ResponseEntity.status(200).body(new ApiResponse("The product have been added successfully"));
            }else {
                return ResponseEntity.status(400).body(new ApiResponse("There are no category with this id found"));
            }
        }
    }

    @GetMapping("/get")
    public ResponseEntity<?> getProducts(){
        List<Product> products = productService.getProducts();
        if (products.isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("There are no products to show"));
        }
        else {
            return ResponseEntity.status(200).body(products);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Integer id, @RequestBody @Valid Product product, Errors errors){
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }
        if (productService.updateProduct(id,product)){
            return ResponseEntity.status(200).body(new ApiResponse("The product have been updated successfully"));
        }
        else {
            return ResponseEntity.status(400).body(new ApiResponse("There is no product with that id found"));
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Integer id){
        if (productService.deleteProduct(id)){
            return ResponseEntity.status(200).body(new ApiResponse("The product have been deleted successfully"));
        }
        else {
            return ResponseEntity.status(400).body(new ApiResponse("There are no product with that id found"));
        }
    }

    @GetMapping("/get/by-category-sorted-by-price/{categoryId}")
    public ResponseEntity<?> getProductsByCategorySortedByPrice(@PathVariable Integer categoryId){
        List<Product> sortedProduct= productService.getProductsByCategorySortedByPrice(categoryId);
        if (sortedProduct.isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("There are no products to show"));
        }else {
            return ResponseEntity.status(200).body(sortedProduct);
        }
    }

    @GetMapping("/product-count-in-category/{categoryId}")                                /* extra */
    public ResponseEntity<?> countProductInCategory(@PathVariable Integer categoryId){
        int count=productService.countProductInCategoryName(categoryId);
        if (count==0){
            return ResponseEntity.status(400).body(new ApiResponse("There are no products to show"));
        }else {
            return ResponseEntity.status(200).body(new ApiResponse("The numbers of products in this category is "+count));
        }
    }
}
