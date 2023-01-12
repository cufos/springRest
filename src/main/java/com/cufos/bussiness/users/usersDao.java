package com.cufos.bussiness.users;

import com.cufos.model.UserModel;
import com.cufos.payload.request.SignupRequest;

import java.util.List;
import java.util.Optional;

public interface usersDao {
  List<UserModel> getAllUsers();

  Optional<UserModel> getUserById(Long id);

  void deleteUser(Long id);

  void createAdmin(SignupRequest signUpRequest);
}
