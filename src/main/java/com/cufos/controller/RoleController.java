package com.cufos.controller;

import com.cufos.bussiness.role.RoleImplementation;
import com.cufos.model.RoleModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RoleController {
  private final RoleImplementation roleImplementation;

  public RoleController(RoleImplementation roleImplementation) {
    this.roleImplementation = roleImplementation;
  }

  @GetMapping("/roles")
  public ResponseEntity<List<RoleModel>> getRole(){
    List<RoleModel> roles = roleImplementation.getRoles();
    return new ResponseEntity<>(roles, HttpStatus.OK);
  }

  @PostMapping("/users/{userId}/roles")
  public ResponseEntity<Object> addRoleToUser(@PathVariable Long userId, @RequestBody RoleModel role) {
   roleImplementation.addRoleToUser(userId,role);

    return ResponseEntity.ok().build();
  }

//  @PutMapping("/role/{id}")
//  public ResponseEntity<RoleModel> updateRoles(@PathVariable("id") long id, @RequestBody RoleModel role) {
//    RoleModel _role = rolesRepository.findById(id)
//      .orElseThrow(() -> new ResourceNotFoundException("Not found Role with id = " + id));
//    _role.setName(role.getName());
//    return new ResponseEntity<>(rolesRepository.save(_role), HttpStatus.OK);
//  }

  @DeleteMapping("roles/{id}")
  public ResponseEntity<HttpStatus> deleteRole(@PathVariable("id") long id){
    roleImplementation.deleteRole(id);

    return new ResponseEntity<>(HttpStatus.OK);
  }
}
