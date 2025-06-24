package com.natha.dev.Model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.Random;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class ApplicationInstance {

    @Id
    @Column(length = 10)
    private String idApp;

    @Column(nullable = false, unique = true)
    private String name;

    private String description;

    private String logo;

    private String status;

    private Boolean isSandbox = false;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime lastModifiedDate;

    private String createdBy;

    // Si w vle ajoute paramet esansyèl, tankou lang, theme, elatriye
    private String language; // eg. "en", "fr"

    private String themeColor; // eg. "#007bff"

    // Relasyon avèk Organization
    @ManyToOne
    @JoinColumn(name = "organization_id")
    private Organization organization;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.lastModifiedDate = LocalDateTime.now();

        if (this.idApp == null) {
            this.idApp = generateCustomIdApp(10);
        }
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
        this.lastModifiedDate = LocalDateTime.now();
    }

    private String generateCustomIdApp(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefg0123456789";
        StringBuilder sb = new StringBuilder();
        Random rand = new Random();

        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(rand.nextInt(chars.length())));
        }
        return sb.toString();
    }

    public String getId() {
        return this.idApp;
    }

}
