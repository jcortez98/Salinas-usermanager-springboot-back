package com.salinas.test.usermanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.salinas.test.usermanager.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

}
