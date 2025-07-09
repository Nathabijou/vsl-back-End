package com.natha.dev.ServiceImpl;

import com.natha.dev.Dao.ConversationDao;
import com.natha.dev.Dao.UserDao;
import com.natha.dev.Dto.ConversationDto;
import com.natha.dev.IService.ConversationIService;
import com.natha.dev.Model.Conversation;
import com.natha.dev.Model.Users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ConversationServiceImpl implements ConversationIService {

    @Autowired
    private ConversationDao conversationDao;

    @Autowired
    private UserDao userDao;

    @Override
    public ConversationDto getOrCreateConversation(String username1, String username2) {
        Users user1 = userDao.findById(username1)
                .orElseThrow(() -> new RuntimeException("User " + username1 + " not found"));
        Users user2 = userDao.findById(username2)
                .orElseThrow(() -> new RuntimeException("User " + username2 + " not found"));

        Conversation conversation = conversationDao.findByParticipant1AndParticipant2(user1, user2)
                .or(() -> conversationDao.findByParticipant2AndParticipant1(user1, user2))
                .orElseGet(() -> {
                    Conversation conv = new Conversation();
                    conv.setParticipant1(user1);
                    conv.setParticipant2(user2);
                    return conversationDao.save(conv);
                });

        return mapToDto(conversation);
    }

    @Override
    public Optional<ConversationDto> findConversationBetween(String username1, String username2) {
        Optional<Users> user1Opt = userDao.findById(username1);
        Optional<Users> user2Opt = userDao.findById(username2);

        if (user1Opt.isEmpty() || user2Opt.isEmpty()) {
            return Optional.empty();
        }

        Optional<Conversation> convOpt = conversationDao.findByParticipant1AndParticipant2(user1Opt.get(), user2Opt.get())
                .or(() -> conversationDao.findByParticipant2AndParticipant1(user1Opt.get(), user2Opt.get()));

        return convOpt.map(this::mapToDto);
    }

    private ConversationDto mapToDto(Conversation conv) {
        return new ConversationDto(
                conv.getId(),
                conv.getParticipant1().getUserName(),
                conv.getParticipant2().getUserName(),
                conv.getCreatedAt()
        );
    }
}
