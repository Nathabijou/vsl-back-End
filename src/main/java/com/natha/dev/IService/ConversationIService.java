package com.natha.dev.IService;

import com.natha.dev.Dto.ConversationDto;

import java.util.Optional;

public interface ConversationIService {
    ConversationDto getOrCreateConversation(String username1, String username2);
    Optional<ConversationDto> findConversationBetween(String username1, String username2);
}