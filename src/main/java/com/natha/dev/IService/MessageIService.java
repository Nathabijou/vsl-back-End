package com.natha.dev.IService;

import com.natha.dev.Dto.MessageDto;

import java.util.List;

public interface MessageIService {
    List<MessageDto> getMessagesBetween(String user1, String user2);
    MessageDto saveMessage(MessageDto messageDto);
}
