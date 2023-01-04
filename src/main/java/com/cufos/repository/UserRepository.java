package com.cufos.repository;

import com.cufos.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserModel, Long> {
  UserModel getReferenceById(long id);
}

