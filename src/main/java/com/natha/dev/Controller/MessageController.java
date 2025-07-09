package com.natha.dev.Controller;

import com.natha.dev.Dto.MessageDto;
import com.natha.dev.IService.MessageIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @Autowired
    private MessageIService messageIService;

    // Rekipere tout mesaj ant 2 itilizatè
    @GetMapping
    public List<MessageDto> getMessagesBetweenUsers(@RequestParam String user1, @RequestParam String user2) {
        return messageIService.getMessagesBetween(user1, user2);
    }

    // Sove nouvo mesaj
    @PostMapping
    public MessageDto sendMessage(@RequestBody MessageDto messageDto) {
        MessageDto savedMessage = messageIService.saveMessage(messageDto);

        // Ou ka emèt mesaj la via WebSocket isit la si vle

        return savedMessage;
    }
}
