package com.tuwaiq.jpa_e_commerce.Controller;

import Api.ApiResponse;
import com.tuwaiq.jpa_e_commerce.Model.MerchantStock;
import com.tuwaiq.jpa_e_commerce.Model.Product;
import com.tuwaiq.jpa_e_commerce.Service.MerchantStockService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/merchant-stock")
@RequiredArgsConstructor
public class MerchantStockController {

    private final MerchantStockService merchantStockService;

    @PostMapping("/add")
    public ResponseEntity<?> addMerchantStock(@RequestBody @Valid MerchantStock merchantStock, Errors errors){
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }
        else {
            String value= merchantStockService.addMerchantStock(merchantStock);
            switch (value){
                case "ok":
                    return ResponseEntity.status(200).body(new ApiResponse("The merchant stock have been added successfully"));
                case "product id error":
                    return ResponseEntity.status(400).body(new ApiResponse(("There are no product with this id found")));
                case "merchant id error":
                    return ResponseEntity.status(400).body(new ApiResponse("There are no merchant with this id found"));
                default:
                    return ResponseEntity.status(400).body(new ApiResponse("General error"));

            }
        }
    }

    @GetMapping("/get")
    public ResponseEntity<?> getMerchantStocks(){
        ArrayList<MerchantStock> merchantStocks = merchantStockService.getMerchantStocks();
        if (merchantStocks.isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("There are no merchant stocks to show"));
        }
        else {
            return ResponseEntity.status(200).body(merchantStocks);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateMerchantStock(@PathVariable String id, @RequestBody @Valid MerchantStock merchantStock, Errors errors){
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }
        if (merchantStockService.updateMerchantStock(id,merchantStock)){
            return ResponseEntity.status(200).body(new ApiResponse("The merchant stock have been updated successfully"));
        }
        else {
            return ResponseEntity.status(400).body(new ApiResponse("There is no merchant stock with that id found"));
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteMerchantStock(@PathVariable String id){
        if (merchantStockService.deleteMerchantStock(id)){
            return ResponseEntity.status(200).body(new ApiResponse("The merchant stock have been deleted successfully"));
        }
        else {
            return ResponseEntity.status(400).body(new ApiResponse("There are no merchant stock with that id found"));
        }
    }

    @PutMapping("/increase-stock/{merchantId}/{productId}/{newStock}")
    public ResponseEntity<?> increaseProductStock(@PathVariable String merchantId, @PathVariable String productId, @PathVariable int newStock){
        String  value= merchantStockService.increaseProductStock(merchantId,productId,newStock);
        switch (value){
            case "ok":
                return ResponseEntity.status(200).body(new ApiResponse("The new stock have been added successfully"));
            case "product id error":
                return ResponseEntity.status(400).body(new ApiResponse("There are no product with this id found"));
            case "merchant id error":
                return ResponseEntity.status(400).body(new ApiResponse("There are no merchant with this id found"));
            default:
                return ResponseEntity.status(400).body(new ApiResponse("General error"));
        }
    }

    @PutMapping("/buy-product/{userId}/{merchantId}/{productId}")
    public ResponseEntity<?> buyProduct(@PathVariable String userId, @PathVariable String merchantId, @PathVariable String productId){
        String value= merchantStockService.buyProduct(userId,merchantId,productId);
        switch (value){
            case "ok":
                return ResponseEntity.status(200).body(new ApiResponse("The product have been purchased successfully"));
            case "user id error":
                return ResponseEntity.status(400).body(new ApiResponse("There are no user with this id found"));
            case "merchant id error":
                return ResponseEntity.status(400).body(new ApiResponse("There are no merchant with this id found"));
            case "product id error":
                return ResponseEntity.status(400).body(new ApiResponse("There are no product with this id found"));
            case "stock error":
                return ResponseEntity.status(400).body(new ApiResponse("The product is out of stocks"));
            case "balance error":
                return ResponseEntity.status(400).body(new ApiResponse("There are no sufficient funds in your account to make this purchase"));
            default:
                return ResponseEntity.status(400).body(new ApiResponse("General error"));
        }
    }

    @PutMapping("/bulk-buy-product/{userId}/{merchantId}/{productId}/{count}")
    public ResponseEntity<?> bulkBuyProduct(@PathVariable String userId, @PathVariable String merchantId, @PathVariable String productId, @PathVariable int count){
        String value= merchantStockService.bulkBuyProducts(userId,merchantId,productId,count);
        switch (value){
            case "ok":
                return ResponseEntity.status(200).body(new ApiResponse("The products have been purchased successfully"));
            case "user id error":
                return ResponseEntity.status(400).body(new ApiResponse("There are no user with this id found"));
            case "merchant id error":
                return ResponseEntity.status(400).body(new ApiResponse("There are no merchant with this id found"));
            case "product id error":
                return ResponseEntity.status(400).body(new ApiResponse("There are no product with this id found"));
            case "stock error":
                return ResponseEntity.status(400).body(new ApiResponse("The product is out of stocks"));
            case "balance error":
                return ResponseEntity.status(400).body(new ApiResponse("There are no sufficient funds in your account to make this purchase"));
            default:
                return ResponseEntity.status(400).body(new ApiResponse("General error"));
        }
    }

    @GetMapping("/get-total-stock/{productId}")
    public ResponseEntity<?> getProductStockFromAllMerchants(@PathVariable String productId){
        ArrayList<String> productStockFromAllMerchants=merchantStockService.getProductStockFromAllMerchants(productId);
        if (productStockFromAllMerchants.isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("There are no product with this id found"));
        }
        else {
            return ResponseEntity.status(200).body(productStockFromAllMerchants);
        }
    }

    @GetMapping("/more-product-from-merchant/{merchantId}")
    public ResponseEntity<?> getMoreProductFromMerchant(@PathVariable String merchantId){
        ArrayList<Product> moreProducts=merchantStockService.getMoreProductFromMerchant(merchantId);
        if (moreProducts.isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("There are no products found with this merchant id"));
        }
        else {
            return ResponseEntity.status(200).body(moreProducts);
        }
    }
}
