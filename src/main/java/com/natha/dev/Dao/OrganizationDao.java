package com.natha.dev.Dao;

import com.natha.dev.Model.Organization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrganizationDao extends JpaRepository<Organization, String> {
    List<Organization> findByMySystemId(Long id);
    Optional<Organization> findByIdorgAndMySystemId(String idorg, Long mySystemId);



}
