package com.natha.dev.Dao;

import com.natha.dev.Model.Conversation;
import com.natha.dev.Model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConversationDao extends JpaRepository<Conversation, Long> {

    Optional<Conversation> findByParticipant1AndParticipant2(Users p1, Users p2);
    Optional<Conversation> findByParticipant2AndParticipant1(Users p1, Users p2);
}
