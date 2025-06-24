package com.natha.dev.Dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserApplicationPrivilegeDto {
    private String userName;
    private String applicationId;
    private String privilegeName;
}
