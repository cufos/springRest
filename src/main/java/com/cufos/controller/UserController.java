package com.cufos.controller;


import com.cufos.model.UserModel;
import com.cufos.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {

  @Autowired
  UserRepository userRepository;

  @GetMapping("/users")
  public ResponseEntity<List<UserModel>> getUsers(){
    List<UserModel> userArrayList = new ArrayList<>(userRepository.findAll());

    if (userArrayList.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    return new ResponseEntity<>(userArrayList, HttpStatus.OK);
  }

  @PostMapping("/users")
  public ResponseEntity<UserModel> createUser(@RequestBody UserModel user){
    UserModel _user = userRepository.save(user);
    return new ResponseEntity<>(_user, HttpStatus.CREATED);
  }

}
