package com.cufos.repository;

import com.cufos.model.RoleEn;
import com.cufos.model.RoleModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface RolesRepository extends JpaRepository<RoleModel, Long> {
    Optional<RoleModel> findByName(RoleEn name);
  }


