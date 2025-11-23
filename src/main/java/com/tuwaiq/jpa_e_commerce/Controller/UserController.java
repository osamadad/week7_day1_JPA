package com.tuwaiq.jpa_e_commerce.Controller;

import Api.ApiResponse;
import com.tuwaiq.jpa_e_commerce.Model.User;
import com.tuwaiq.jpa_e_commerce.Service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/add")
    public ResponseEntity<?> addUser(@RequestBody @Valid User user, Errors errors){
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }
        else {
            userService.addUser(user);
            return ResponseEntity.status(200).body(new ApiResponse("The user have been added successfully"));
        }
    }

    @GetMapping("/get")
    public ResponseEntity<?> getUsers(){
        ArrayList<User> users=userService.getUsers();
        if (users.isEmpty()){
            return ResponseEntity.status(400).body(new ApiResponse("There are no users to show"));
        }
        else {
            return ResponseEntity.status(200).body(users);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable String id, @RequestBody @Valid User user, Errors errors){
        if (errors.hasErrors()){
            return ResponseEntity.status(400).body(new ApiResponse(errors.getFieldError().getDefaultMessage()));
        }
        if (userService.updateUser(id,user)){
            return ResponseEntity.status(200).body(new ApiResponse("The user have been updated successfully"));
        }
        else {
            return ResponseEntity.status(400).body(new ApiResponse("There is no user with that id found"));
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable String id){
        if (userService.deleteUser(id)){
            return ResponseEntity.status(200).body(new ApiResponse("The user have been deleted successfully"));
        }
        else {
            return ResponseEntity.status(400).body(new ApiResponse("There is no user with that id found"));
        }
    }

    @PutMapping("/add-balance/{id}/{addedBalance}")
    public ResponseEntity<?> addBalanceFunds(@PathVariable String id, @PathVariable double addedBalance){
        if (userService.addBalanceFunds(id,addedBalance)){
            return ResponseEntity.status(200).body(new ApiResponse("The balance have been added successfully"));
        }else {
            return ResponseEntity.status(400).body(new ApiResponse("There is no user with this id found"));
        }
    }

}
