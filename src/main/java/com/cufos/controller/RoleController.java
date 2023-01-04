package com.cufos.controller;

import com.cufos.model.RoleModel;
import com.cufos.model.UserModel;
import com.cufos.repository.RolesRepository;
import com.cufos.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class RoleController {

  @Autowired
  RolesRepository rolesRepository;
  @Autowired
  private UserRepository userRepository;

  @GetMapping("/roles")
  public ResponseEntity<List<RoleModel>> getRole(){
    List<RoleModel> userArrayList = new ArrayList<>(rolesRepository.findAll());

    if (userArrayList.isEmpty()) {
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    return new ResponseEntity<>(userArrayList, HttpStatus.OK);
  }

  @PostMapping("/users/{userId}/roles")
  public ResponseEntity<Object> addRoleToUser(@PathVariable Long userId, @RequestBody RoleModel role) {
    UserModel user = userRepository.findById(userId).orElse(null);
    if (user == null || role == null) {
      return ResponseEntity.notFound().build();
    }

    user.getRoles().add(role);
    userRepository.save(user);

    return ResponseEntity.ok().build();
  }
}
