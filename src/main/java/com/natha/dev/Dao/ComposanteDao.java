package com.natha.dev.Dao;

import com.natha.dev.Model.Composante;
import com.natha.dev.Model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;

public interface ComposanteDao extends JpaRepository<Composante, Long> {
    List<Composante> findByApplicationInstanceId(String applicationId);

    @Query("SELECT c.users FROM Composante c WHERE c.id = :composanteId")
    List<Users> findUsersByComposanteId(@Param("composanteId") Long composanteId);


    @Query("SELECT c FROM Composante c JOIN c.users u WHERE c.applicationInstance.idApp = :applicationId AND u.userName = :username")
    List<Composante> findByApplicationIdAndUser(@Param("applicationId") String applicationId, @Param("username") String username);

    List<Composante> findByUsersUserName(String userName);
}
