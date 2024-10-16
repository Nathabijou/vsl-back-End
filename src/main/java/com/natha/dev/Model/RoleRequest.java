package com.natha.dev.Model;

import java.util.List;

public class RoleRequest {
    private String roleName;
    private List<String> privileges;
    public String getRoleName() {
        return roleName;
    }
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
    public List<String> getPrivileges() {
        return privileges;
    }
    public void setPrivileges(List<String> privileges) {
        this.privileges = privileges;
    }
}