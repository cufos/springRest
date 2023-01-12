package com.cufos.bussiness;

import com.cufos.model.RoleModel;

import java.util.List;

public interface roleBO {
  List<RoleModel> getRoles();

  void deleteRole(Long id);

  void addRoleToUser(Long id,RoleModel roleModel);
}
