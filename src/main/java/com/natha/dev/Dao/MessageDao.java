package com.natha.dev.Dao;

import com.natha.dev.Model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MessageDao extends JpaRepository<Message, Long> {


    @Query("SELECT m FROM Message m WHERE " +
            "(m.sender.userName = :user1 AND m.receiver.userName = :user2) OR " +
            "(m.sender.userName = :user2 AND m.receiver.userName = :user1) " +
            "ORDER BY m.sentAt ASC")
    List<Message> findMessagesBetweenUsers(@Param("user1") String user1, @Param("user2") String user2);

}
