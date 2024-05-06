package com.gui.swing.Repository;

import com.gui.swing.Entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    public Boolean existsRoleByRoleNameIgnoreCase(String roleName);

    public Role findRoleByRoleNameIgnoreCase(String roleName);
}