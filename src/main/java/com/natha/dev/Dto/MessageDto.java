package com.natha.dev.Dto;


import lombok.Data;

@Data
public class MessageDto {
    private Long conversationId;
    private String senderUsername;
    private String receiverUsername;
    private String content;
    private Long teamId;
    private Long channelId;

}