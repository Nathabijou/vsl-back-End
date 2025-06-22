package com.natha.dev.ServiceImpl;

import com.natha.dev.Dao.PrivilegeDao;
import com.natha.dev.Dao.RoleDao;
import com.natha.dev.Model.Privilege;
import com.natha.dev.Model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RoleService {

    @Autowired
    private RoleDao roleDao;

    public Role createNewRoleWithPrivileges(String roleName) {
        Role role = new Role();
        role.setRoleName(roleName);
        role.setRoleDescription("Description for " + roleName + " role");

        Set<Privilege> privileges = new HashSet<>();

        if ("Admin".equals(roleName)) {
            privileges.add(new Privilege("CREATE", "Create privilege"));
            privileges.add(new Privilege("UPDATE", "Update privilege"));
            privileges.add(new Privilege("READ", "Read privilege"));
            privileges.add(new Privilege("DELETE", "Delete privilege"));
        } else if ("Manager".equals(roleName)) {
            privileges.add(new Privilege("CREATE", "Create privilege"));
            privileges.add(new Privilege("UPDATE", "Update privilege"));
            privileges.add(new Privilege("READ", "Read privilege"));
        } else if ("User".equals(roleName)) {
            privileges.add(new Privilege("CREATE", "Update privilege"));
            privileges.add(new Privilege("READ", "Read privilege"));
        } else if ("Moderant".equals(roleName)) {
            privileges.add(new Privilege("READ", "Read privilege"));
        }

        role.setPrivileges(privileges);
        return roleDao.save(role);
    }
    @Autowired
    private PrivilegeDao privilegeDao;

    public Privilege createNewPrivilege(String privilegeName, String privilegeDescription) {
        Privilege privilege = new Privilege();
        privilege.setPrivilegeName(privilegeName);
        privilege.setPrivilegeDescription(privilegeDescription);
        return privilegeDao.save(privilege);
    }

    public List<Role> getAllRoles() {
        return (List<Role>) roleDao.findAll();
    }
}
