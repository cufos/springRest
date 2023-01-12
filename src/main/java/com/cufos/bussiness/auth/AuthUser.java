package com.cufos.bussiness.auth;

import com.cufos.exception.ResourceAlreadyExistsException;
import com.cufos.model.RoleEn;
import com.cufos.model.RoleModel;
import com.cufos.model.UserModel;
import com.cufos.payload.request.SignupRequest;
import com.cufos.repository.RolesRepository;
import com.cufos.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.HashSet;
import java.util.Set;

@Service
public class AuthUser<T> {
  @Autowired
  UserRepository userRepository;
  @Autowired
  RolesRepository roleRepository;
  @Autowired
  PasswordEncoder encoder;

  public UserModel registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
    if (userRepository.existsByUsername(signUpRequest.getUsername())) {
      throw new ResourceAlreadyExistsException("Error: Username is already taken!");
    }

    if (userRepository.existsByEmail(signUpRequest.getEmail())) {
      throw new ResourceAlreadyExistsException("Error: Email is already in use!");
    }

    // Create new user's account
    UserModel user = new UserModel(signUpRequest.getUsername(),
      signUpRequest.getEmail(),
      encoder.encode(signUpRequest.getPassword()));

    Set<String> strRoles = signUpRequest.getRole();
    Set<RoleModel> roles = new HashSet<>();

    if (strRoles == null) {
      RoleModel userRole = roleRepository.findByName(RoleEn.ROLE_USER)
        .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
      roles.add(userRole);
    } else {
      strRoles.forEach(role -> {
        switch (role) {
          case "admin":
            RoleModel adminRole = roleRepository.findByName(RoleEn.ROLE_ADMIN)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(adminRole);

            break;
          case "mod":
            RoleModel modRole = roleRepository.findByName(RoleEn.ROLE_MODERATOR)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(modRole);

            break;
          default:
            RoleModel userRole = roleRepository.findByName(RoleEn.ROLE_USER)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        }
      });
    }

    user.setRoles(roles);
    userRepository.save(user);
    return user;
  }
}
