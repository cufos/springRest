package com.cufos.bussiness.users;

import com.cufos.exception.ResourceAlreadyExistsException;
import com.cufos.exception.ResourceNotFoundException;
import com.cufos.model.RoleEn;
import com.cufos.model.RoleModel;
import com.cufos.model.UserModel;
import com.cufos.payload.request.SignupRequest;
import com.cufos.repository.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.cufos.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UsersImplementation implements usersDao{
  private final UserRepository userRepository;
  private final RolesRepository rolesRepository;

  @Autowired
  PasswordEncoder encoder;

  public UsersImplementation(UserRepository userRepository, RolesRepository rolesRepository) {
    this.userRepository = userRepository;
    this.rolesRepository = rolesRepository;
  }

  @Override
  public List<UserModel> getAllUsers() {
    List<UserModel> userArrayList = new ArrayList<>(userRepository.findAll());
    return  userArrayList;
  }

  @Override
  public Optional<UserModel> getUserById(Long id) {
    Optional<UserModel> _user = userRepository.findById(id);
    if (!userRepository.existsById(id)){
      throw new ResourceNotFoundException("User with [%id] not found".formatted(id));
    }

    return _user;
  }

  @Override
  public void deleteUser(Long id) {
    if(!userRepository.existsById(id)){
      throw new ResourceNotFoundException("User with [%id] not found".formatted(id));
    }

    userRepository.deleteById(id);
  }

  @Override
  public void createAdmin(SignupRequest signUpRequest) {

    if (userRepository.existsByUsername(signUpRequest.getUsername())) {
      throw new ResourceAlreadyExistsException("Error: Username is already taken!");
    }

    if (userRepository.existsByEmail(signUpRequest.getEmail())) {
      throw new ResourceAlreadyExistsException("Error: Email is already in use!");
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

        if (role != "admin"){
          RoleModel adminRole = rolesRepository.findByName(RoleEn.ROLE_ADMIN)
            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(adminRole);
        }

        RoleModel adminRole = rolesRepository.findByName(RoleEn.ROLE_ADMIN)
          .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        roles.add(adminRole);


      });
    }


    user.setRoles(roles);
    userRepository.save(user);
  }
}
