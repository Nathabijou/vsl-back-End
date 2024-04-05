package com.natha.dev.Dao;

import com.natha.dev.Model.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrivilegeDao extends JpaRepository<Privilege, String> {
    Privilege findByPrivilegeName(String privilegeName);
}
