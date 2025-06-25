package com.natha.dev.Dto.ServiceImpl;

import com.natha.dev.Dao.PrivilegeDao;
import com.natha.dev.Dao.RoleDao;
import com.natha.dev.Model.Privilege;
import com.natha.dev.Model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class RoleService {

    @Autowired
    private RoleDao roleDao;

    @Autowired
    private PrivilegeDao privilegeDao;

    public Role createNewRoleWithPrivileges(String roleName) {
        Role role = new Role();
        role.setRoleName(roleName);
        role.setRoleDescription("Description for " + roleName + " role");

        Set<Privilege> privileges = new HashSet<>();

        if ("SUPERADNIM".equalsIgnoreCase(roleName)) {
            privileges.add(getOrCreatePrivilege("CREATE", "Create privilege"));
            privileges.add(getOrCreatePrivilege("UPDATE", "Update privilege"));
            privileges.add(getOrCreatePrivilege("READ", "Read privilege"));
            privileges.add(getOrCreatePrivilege("DELETE", "Delete privilege"));
            privileges.add(getOrCreatePrivilege("MANAGE_ROLES", "Manage roles and privileges"));
            privileges.add(getOrCreatePrivilege("ACCESS_ALL_DATA", "Access all data"));
            privileges.add(getOrCreatePrivilege("MANAGE_USERS", "Manage users"));
            privileges.add(getOrCreatePrivilege("MANAGE_SYSTEM_SETTINGS", "Manage system settings"));
            privileges.add(getOrCreatePrivilege("VIEW_AUDIT_LOGS", "View audit logs"));
            privileges.add(getOrCreatePrivilege("MANAGE_SECURITY", "Manage security settings"));
            privileges.add(getOrCreatePrivilege("MANAGE_GROUPS", "Manage user groups"));
            privileges.add(getOrCreatePrivilege("MANAGE_PERMISSIONS", "Manage permissions"));
            privileges.add(getOrCreatePrivilege("IMPERSONATE_USERS", "Impersonate other users"));
            privileges.add(getOrCreatePrivilege("EXPORT_DATA", "Export system data"));
            privileges.add(getOrCreatePrivilege("IMPORT_DATA", "Import data into system"));
            privileges.add(getOrCreatePrivilege("BACKUP_RESTORE", "Backup and restore system"));
            privileges.add(getOrCreatePrivilege("VIEW_REPORTS", "View all reports"));
            privileges.add(getOrCreatePrivilege("MANAGE_NOTIFICATIONS", "Manage system notifications"));
            privileges.add(getOrCreatePrivilege("ACCESS_API", "Access system APIs"));


    } else if ("ADMIN".equalsIgnoreCase(roleName)) {
            privileges.add(getOrCreatePrivilege("CREATE", "Create privilege"));
            privileges.add(getOrCreatePrivilege("UPDATE", "Update privilege"));
            privileges.add(getOrCreatePrivilege("READ", "Read privilege"));
            privileges.add(getOrCreatePrivilege("DELETE", "Delete privilege"));
        } else if ("MANAGER".equalsIgnoreCase(roleName)) {
            privileges.add(getOrCreatePrivilege("CREATE", "Create privilege"));
            privileges.add(getOrCreatePrivilege("UPDATE", "Update privilege"));
            privileges.add(getOrCreatePrivilege("READ", "Read privilege"));
        } else if ("USER".equalsIgnoreCase(roleName)) {
            privileges.add(getOrCreatePrivilege("CREATE", "Create privilege"));
            privileges.add(getOrCreatePrivilege("READ", "Read privilege"));
            privileges.add(getOrCreatePrivilege("CREATE", "Create privilege"));
        } else if ("MODERANT".equalsIgnoreCase(roleName)) {
            privileges.add(getOrCreatePrivilege("READ", "Read privilege"));
        }

        role.setPrivileges(privileges);
        return roleDao.save(role);
    }

    private Privilege getOrCreatePrivilege(String privilegeName, String privilegeDescription) {
        Optional<Privilege> existingPrivilege = privilegeDao.findById(privilegeName);
        if (existingPrivilege.isPresent()) {
            return existingPrivilege.get();
        }
        Privilege privilege = new Privilege();
        privilege.setPrivilegeName(privilegeName);
        privilege.setPrivilegeDescription(privilegeDescription);
        return privilegeDao.save(privilege);
    }

    public Privilege createNewPrivilege(String privilegeName, String privilegeDescription) {
        // Ou kapab ajoute yon validasyon si ou vle
        Privilege privilege = new Privilege();
        privilege.setPrivilegeName(privilegeName);
        privilege.setPrivilegeDescription(privilegeDescription);
        return privilegeDao.save(privilege);
    }

    public List<Role> getAllRoles() {
        return (List<Role>) roleDao.findAll();
    }
}
