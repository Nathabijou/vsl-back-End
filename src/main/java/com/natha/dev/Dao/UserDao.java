package com.natha.dev.Dao;


import com.natha.dev.Model.Users;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserDao extends CrudRepository<Users, String> {
    Users findByUserName(String userName);

    Users findByUserEmail(String userEmail);




    List<Users> findByCreatedBy(String createdBy);


    Optional<Users> findByUserIdentification(String userIdentification);


}
