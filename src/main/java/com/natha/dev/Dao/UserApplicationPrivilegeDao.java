package com.natha.dev.Dao;

import com.natha.dev.Model.UserAppPrivKey;
import com.natha.dev.Model.UserApplicationPrivilege;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserApplicationPrivilegeDao extends JpaRepository<UserApplicationPrivilege, UserAppPrivKey> {

}
