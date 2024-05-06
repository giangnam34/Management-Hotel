package com.gui.swing.Service;

import com.gui.swing.Entity.Role;
import com.gui.swing.Repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public Role getRole(String roleName){
        if (roleRepository.existsRoleByRoleNameIgnoreCase(roleName)){
            return roleRepository.findRoleByRoleNameIgnoreCase(roleName);
        }
        throw new IllegalArgumentException("Không tìm thấy role");
    }
}
