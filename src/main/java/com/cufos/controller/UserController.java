package com.cufos.controller;


import com.cufos.model.RoleEn;
import com.cufos.model.RoleModel;
import com.cufos.model.UserModel;
import com.cufos.payload.request.SignupRequest;
import com.cufos.payload.response.MessageResponse;
import com.cufos.repository.RolesRepository;
import com.cufos.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api")
public class UserController {

  @Autowired
  UserRepository userRepository;

  @Autowired
  PasswordEncoder encoder;
  @Autowired
  private RolesRepository rolesRepository;

  @GetMapping("/users")
  public ResponseEntity<List<UserModel>> getUsers(){
    List<UserModel> userArrayList = new ArrayList<>(userRepository.findAll());

    if (userArrayList.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    return new ResponseEntity<>(userArrayList, HttpStatus.OK);
  }

  @GetMapping("/user/{id}")
  public ResponseEntity<UserModel> getUser(@PathVariable Long id){
    UserModel _user = userRepository.getReferenceById(id);
    return new ResponseEntity<UserModel>(_user,HttpStatus.OK);
  }

//  @PostMapping("/users")
//  public ResponseEntity<UserModel> createUser(@RequestBody UserModel user){
//    UserModel _user = userRepository.save(user);
//    return new ResponseEntity<>(_user, HttpStatus.CREATED);
//  }

  @PostMapping("/users/admin")
  public ResponseEntity<?> createAdmin(@Valid @RequestBody SignupRequest signUpRequest) {
      if (userRepository.existsByUsername(signUpRequest.getUsername())) {
        return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
      }

      if (userRepository.existsByEmail(signUpRequest.getEmail())) {
        return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
      }

      UserModel user = new UserModel(signUpRequest.getUsername(),
        signUpRequest.getEmail(),
        encoder.encode(signUpRequest.getPassword()));

      Set<String> strRoles = signUpRequest.getRole();
      Set<RoleModel> roles = new HashSet<>();


    if (strRoles == null) {
      RoleModel userRole = rolesRepository.findByName(RoleEn.ROLE_USER)
        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
      roles.add(userRole);
    } else {
      strRoles.forEach(role -> {

          switch (role) {
            case "admin":
              RoleModel adminRole = rolesRepository.findByName(RoleEn.ROLE_ADMIN)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
              roles.add(adminRole);

              break;
          }
      });
    }


      user.setRoles(roles);
      userRepository.save(user);
      return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}
