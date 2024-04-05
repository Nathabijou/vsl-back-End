package com.natha.dev.Controller;

import com.natha.dev.Model.Role;
import com.natha.dev.ServiceImpl.RoleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping("/createNewRole")
    public Role createNewRole(@RequestBody String roleName) {
        return roleService.createNewRoleWithPrivileges(roleName);
    }
}
