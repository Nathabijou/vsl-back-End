package com.natha.dev.Dao;


import com.natha.dev.Model.Privilege;
import com.natha.dev.Model.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleDao extends CrudRepository<Role, String> {
    Role findByRoleName(String roleName);
    Privilege save(Privilege privilege);
}
