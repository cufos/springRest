package com.cufos.controller;


import com.cufos.bussiness.users.UsersImplementation;
import com.cufos.model.UserModel;
import com.cufos.payload.request.SignupRequest;
import com.cufos.payload.response.MessageResponse;
import com.cufos.repository.RolesRepository;
import com.cufos.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api")
public class UserController {
  private final UsersImplementation usersImplementation;

  public UserController(UsersImplementation usersImplementation) {
    this.usersImplementation = usersImplementation;
  }

  @GetMapping("/users")
  public ResponseEntity<List<UserModel>> getUsers(){
    return new ResponseEntity<>(usersImplementation.getAllUsers(), HttpStatus.OK);
  }

  @GetMapping("/user/{id}")
  public ResponseEntity<?> getUser(@PathVariable Long id){
    Optional<UserModel> user = usersImplementation.getUserById(id);

    return new ResponseEntity<>(user,HttpStatus.OK);
  }

  @DeleteMapping("user/{id}")
  public ResponseEntity<?> deleteUser(@PathVariable Long id){
    usersImplementation.deleteUser(id);

    return new ResponseEntity(HttpStatus.OK);
  }

//  @PostMapping("/users")
//  public ResponseEntity<UserModel> createUser(@RequestBody UserModel user){
//    UserModel _user = userRepository.save(user);
//    return new ResponseEntity<>(_user, HttpStatus.CREATED);
//  }

  @PostMapping("/users/admin")
  public ResponseEntity<?> createAdmin(@Valid @RequestBody SignupRequest signUpRequest) {

      usersImplementation.createAdmin(signUpRequest);
      return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}
