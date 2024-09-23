package com.salinas.test.usermanager.service.interfaces;

import java.util.List;

import com.salinas.test.usermanager.model.Role;

public interface IRoleService {

    Role findRoleById(Long id);

    List<Role> getAllRoles();

    void saveRole(Role role);

    void deleteRole(Role role);
}
