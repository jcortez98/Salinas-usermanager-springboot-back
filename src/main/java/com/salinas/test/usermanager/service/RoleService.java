package com.salinas.test.usermanager.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.salinas.test.usermanager.model.Role;
import com.salinas.test.usermanager.repository.RoleRepository;
import com.salinas.test.usermanager.service.interfaces.IRoleService;

@Service
public class RoleService implements IRoleService{

    @Autowired
    RoleRepository roleRepository;


    @Override
    public Role findRoleById(Long id) {
        return roleRepository.findById(id).orElse(null);
    }

    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public void saveRole(Role role) {
        roleRepository.save(role);
    }

    @Override
    public void deleteRole(Role role) {
        roleRepository.delete(role);
    }

}
