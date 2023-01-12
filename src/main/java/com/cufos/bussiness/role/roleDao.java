package com.cufos.bussiness.role;

import com.cufos.model.RoleModel;

import java.util.List;

public interface roleDao {
  List<RoleModel> getRoles();

  void deleteRole(Long id);

  void addRoleToUser(Long id,RoleModel roleModel);
}
