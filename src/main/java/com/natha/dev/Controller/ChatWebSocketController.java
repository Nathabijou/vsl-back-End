package com.natha.dev.Controller;


import com.natha.dev.Dto.MessageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class ChatWebSocketController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;



    @MessageMapping("/chat.send")
    public void sendMessage(@Payload MessageDto message, Principal principal) {
        System.out.println("Message de: " + principal.getName());
        // Ou ka verifye ke `principal.getName()` se senderUsername oswa lòt validasyon
        messagingTemplate.convertAndSendToUser(
                message.getReceiverUsername(),
                "/queue/messages",
                message
        );
        System.out.println("🔸 From: " + message.getSenderUsername());
        System.out.println("🔹 To: " + message.getReceiverUsername());
        System.out.println("🔊 Content: " + message.getContent());


    }

    @MessageMapping("/teams/{teamId}/channels/{channelId}/send")
    public void sendTeamMessage(@Payload MessageDto message,
                                @DestinationVariable Long teamId,
                                @DestinationVariable Long channelId,
                                Principal principal) {
        System.out.println("📢 [Team: " + teamId + " | Channel: " + channelId + "] " + message.getContent());

        messagingTemplate.convertAndSend(
                "/topic/teams/" + teamId + "/channels/" + channelId,
                message
        );
    }


}