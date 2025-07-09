package com.natha.dev.Model;


import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Conversation conversation;

    @ManyToOne
    @JoinColumn(name = "sender_username", referencedColumnName = "userName")
    private Users sender;

    @ManyToOne
    @JoinColumn(name = "receiver_username", referencedColumnName = "userName")
    private Users receiver;

    private String content;

    private LocalDateTime sentAt = LocalDateTime.now();
}

