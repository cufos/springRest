package com.cufos.bussiness.role;

import com.cufos.exception.ResourceNotFoundException;
import com.cufos.model.RoleModel;
import com.cufos.model.UserModel;
import com.cufos.repository.RolesRepository;
import com.cufos.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleImplementation implements roleDao {
  private final RolesRepository rolesRepository;
  private final UserRepository userRepository;

  public RoleImplementation(RolesRepository rolesRepository, UserRepository userRepository) {
    this.rolesRepository = rolesRepository;
    this.userRepository = userRepository;
  }

  @Override
  public List<RoleModel> getRoles() {
    List<RoleModel> roleArrayList = new ArrayList<>(rolesRepository.findAll());

    return roleArrayList;
  }

  @Override
  public void deleteRole(Long id) {
    if (!rolesRepository.existsById(id)){
      throw new ResourceNotFoundException("Role with [%id] not found".formatted(id));
    }
    rolesRepository.deleteById(id);
  }

  @Override
  public void addRoleToUser(Long id, RoleModel role) {

    UserModel user = userRepository.findById(id).orElse(null);
    if (user == null || role == null) {
      throw new ResourceNotFoundException("User with [%id] not found".formatted(id));
    }

    user.getRoles().add(role);
    userRepository.save(user);
  }
}
