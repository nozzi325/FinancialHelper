package com.andrew.FinancialHelper.controller;

import com.andrew.FinancialHelper.dto.request.UserRequest;
import com.andrew.FinancialHelper.dto.response.UserResponse;
import com.andrew.FinancialHelper.db.entity.User;
import com.andrew.FinancialHelper.service.UserService;

import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(("/api/users"))
@AllArgsConstructor
public class UserController {
    private final UserService userService;
    private final ModelMapper modelMapper;


    @GetMapping()
    public List<UserResponse> getAllUsers(){
        return userService.findAll()
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    @GetMapping("/{id}")
    public UserResponse getUserById(@PathVariable Long id){
        return convertToResponse(userService.findUserById(id));
    }

    @PostMapping()
    public ResponseEntity<HttpStatus> createUser(@RequestBody @Valid UserRequest request){
        userService.createUser(convertToEntity(request));
        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @PutMapping()
    public ResponseEntity<HttpStatus> updateUser(@RequestBody @Valid UserRequest request){
        userService.updateUser(convertToEntity(request));
        return ResponseEntity.ok(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable Long id){
        userService.deleteUser(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    private User convertToEntity(UserRequest request){
        return modelMapper.map(request,User.class);
    }

    private UserResponse convertToResponse(User user) {
        return modelMapper.map(user, UserResponse.class);
    }

}
