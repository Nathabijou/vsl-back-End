package com.natha.dev.Dao;

import com.natha.dev.Model.ApplicationInstance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ApplicationInstanceDao extends JpaRepository<ApplicationInstance, String> {
    List<ApplicationInstance> findByUsersUserName(String userName);
}
