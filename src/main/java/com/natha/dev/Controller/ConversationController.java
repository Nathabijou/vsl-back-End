package com.natha.dev.Controller;

import com.natha.dev.Dto.ConversationDto;
import com.natha.dev.IService.ConversationIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/conversations")
public class ConversationController {

    @Autowired
    private ConversationIService conversationService;

    @PostMapping("/start")
    public ResponseEntity<ConversationDto> startConversation(
            @RequestParam String user1,
            @RequestParam String user2) {
        ConversationDto dto = conversationService.getOrCreateConversation(user1, user2);
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/check")
    public ResponseEntity<ConversationDto> checkConversation(
            @RequestParam String user1,
            @RequestParam String user2) {
        Optional<ConversationDto> dtoOpt = conversationService.findConversationBetween(user1, user2);
        return dtoOpt.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
}
