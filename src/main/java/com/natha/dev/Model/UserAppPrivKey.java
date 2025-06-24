package com.natha.dev.Model;

import jakarta.persistence.Embeddable;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
public class UserAppPrivKey implements Serializable {
    private String userName;
    private String idApp;
    private String privilegeName;

    public UserAppPrivKey(String userName, long applicationId, String privilegeName) {
        this.userName = userName;
        this.idApp = String.valueOf(applicationId); // paske applicationId se String nan klas la
        this.privilegeName = privilegeName;
    }

}
