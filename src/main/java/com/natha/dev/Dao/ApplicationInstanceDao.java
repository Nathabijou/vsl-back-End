package com.natha.dev.Dao;

import com.natha.dev.Model.ApplicationInstance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationInstanceDao extends JpaRepository<ApplicationInstance, String> {
}
