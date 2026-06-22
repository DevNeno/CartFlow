package com.cart.demo.controller;

import com.cart.demo.dto.user.UserResponse;
import com.cart.demo.dto.user.UserSaveRequest;
import com.cart.demo.dto.user.UserUpdateRequest;
import com.cart.demo.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    //Get all
    @GetMapping
    public ResponseEntity<List<UserResponse>> findAll(){
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    // Get by id
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findById(@PathVariable Long id){
        return new ResponseEntity<>(userService.findById(id), HttpStatus.OK);
    }

    // Save
    @PostMapping
    public ResponseEntity<UserResponse> save(@Valid @RequestBody UserSaveRequest request){
        return new ResponseEntity<>(userService.save(request), HttpStatus.CREATED);
    }

    // Update
    @PutMapping("/{id}")
    public  ResponseEntity<UserResponse> update(@PathVariable Long id, @Valid @RequestBody UserUpdateRequest request){
        return new ResponseEntity<>(userService.update(id, request), HttpStatus.OK);
    }

    // Delete
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
