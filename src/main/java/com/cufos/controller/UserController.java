package com.cufos.controller;


import com.cufos.bussiness.impl.UsersBOImpl;
import com.cufos.model.UserModel;
import com.cufos.payload.request.SignupRequest;
import com.cufos.payload.response.MessageResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api")
public class UserController {
  private final UsersBOImpl usersBOImpl;

  public UserController(UsersBOImpl usersBOImpl) {
    this.usersBOImpl = usersBOImpl;
  }

  @GetMapping("/users")
  public ResponseEntity<List<UserModel>> getUsers(){
    List<UserModel> _users = usersBOImpl.getAllUsers();
    return new ResponseEntity<>(_users, HttpStatus.OK);
  }

  @GetMapping("/user/{id}")
  public ResponseEntity<?> getUser(@PathVariable Long id){
    Optional<UserModel> user = usersBOImpl.getUserById(id);

    return new ResponseEntity<>(user,HttpStatus.OK);
  }

  @DeleteMapping("user/{id}")
  public ResponseEntity<?> deleteUser(@PathVariable Long id){
    usersBOImpl.deleteUser(id);

    return new ResponseEntity(HttpStatus.OK);
  }

//  @PostMapping("/users")
//  public ResponseEntity<UserModel> createUser(@RequestBody UserModel user){
//    UserModel _user = userRepository.save(user);
//    return new ResponseEntity<>(_user, HttpStatus.CREATED);
//  }

  @PostMapping("/users/admin")
  public ResponseEntity<?> createAdmin(@Valid @RequestBody SignupRequest signUpRequest) {

      usersBOImpl.createAdmin(signUpRequest);
      return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}
