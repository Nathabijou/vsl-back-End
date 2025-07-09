package com.natha.dev.Model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Conversation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Yon itilizatè kapab nan plizyè konvèsasyon
    @ManyToOne
    @JoinColumn(name = "participant1_username", referencedColumnName = "userName")
    private Users participant1;

    @ManyToOne
    @JoinColumn(name = "participant2_username", referencedColumnName = "userName")
    private Users participant2;

    private LocalDateTime createdAt = LocalDateTime.now();
}
