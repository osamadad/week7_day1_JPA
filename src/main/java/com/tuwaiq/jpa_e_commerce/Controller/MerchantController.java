package com.tuwaiq.jpa_e_commerce.Controller;

import Api.ApiResponse;
import com.tuwaiq.jpa_e_commerce.Model.Merchant;
import com.tuwaiq.jpa_e_commerce.Service.MerchantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/merchant")
@RequiredArgsConstructor
public class MerchantController {

    private final MerchantService merchantService;

    @PostMapping("/add")
    public ResponseEntity<?> addMerchant(@RequestBody @Valid Merchant merchant, Errors errors){
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }
        else {
            merchantService.addMerchant(merchant);
            return ResponseEntity.status(200).body(new ApiResponse("The merchant have been added successfully"));
        }
    }

    @GetMapping("/get")
    public ResponseEntity<?> getMerchants(){
        List<Merchant> merchants= merchantService.getMerchants();
        if (merchants.isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("There are no merchants to show"));
        }
        else {
            return ResponseEntity.status(200).body(merchants);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateMerchant(@PathVariable Integer id, @RequestBody @Valid Merchant merchant, Errors errors){
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }
        if (merchantService.updateMerchant(id,merchant)){
            return ResponseEntity.status(200).body(new ApiResponse("The merchant have been updated successfully"));
        }
        else {
            return ResponseEntity.status(400).body(new ApiResponse("There are not merchant with this id found"));
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteMerchant(@PathVariable Integer id){
        if (merchantService.deleteMerchant(id)){
            return ResponseEntity.status(200).body(new ApiResponse("The merchant have been deleted successfully"));
        }
        else {
            return ResponseEntity.status(400).body(new ApiResponse("There are not merchant with this id found"));
        }
    }

}
