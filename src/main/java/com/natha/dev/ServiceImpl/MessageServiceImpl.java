package com.natha.dev.ServiceImpl;

import com.natha.dev.Dao.ConversationDao;
import com.natha.dev.Dao.MessageDao;
import com.natha.dev.Dao.UserDao;
import com.natha.dev.Dto.MessageDto;
import com.natha.dev.IService.MessageIService;
import com.natha.dev.Model.Conversation;
import com.natha.dev.Model.Message;
import com.natha.dev.Model.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageServiceImpl implements MessageIService {

    @Autowired
    private MessageDao messageDao;

    @Autowired
    private ConversationDao conversationDao;  // ajoute repo konvèsasyon an

    @Autowired
    private UserDao userDao;  // ajoute repo itilizatè yo

    @Override
    public List<MessageDto> getMessagesBetween(String user1, String user2) {
        List<Message> messages = messageDao.findMessagesBetweenUsers(user1, user2);
        return messages.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public MessageDto saveMessage(MessageDto messageDto) {
        Message message = convertToEntity(messageDto);
        Message saved = messageDao.save(message);
        return convertToDto(saved);
    }

    private MessageDto convertToDto(Message message) {
        MessageDto dto = new MessageDto();
        dto.setConversationId(message.getConversation().getId());
        dto.setSenderUsername(message.getSender().getUserName());
        dto.setReceiverUsername(message.getReceiver().getUserName());
        dto.setContent(message.getContent());
        return dto;
    }

    private Message convertToEntity(MessageDto dto) {
        Message message = new Message();

        // Chaje Conversation pa id
        Conversation conversation = conversationDao.findById(dto.getConversationId())
                .orElseThrow(() -> new RuntimeException("Conversation pa jwenn ak id: " + dto.getConversationId()));
        message.setConversation(conversation);

        // Chaje Sender
        Users sender = userDao.findByUserName(dto.getSenderUsername());
        if(sender == null) {
            throw new RuntimeException("Sender pa jwenn ak username: " + dto.getSenderUsername());
        }
        message.setSender(sender);

        // Chaje Receiver
        Users receiver = userDao.findByUserName(dto.getReceiverUsername());
        if(receiver == null) {
            throw new RuntimeException("Receiver pa jwenn ak username: " + dto.getReceiverUsername());
        }
        message.setReceiver(receiver);

        // Mete kontni mesaj
        message.setContent(dto.getContent());

        return message;
    }


}
