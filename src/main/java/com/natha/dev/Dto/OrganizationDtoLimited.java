package com.natha.dev.Dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrganizationDtoLimited {
    private String idorg;
    private String name;
    private String type;
    private String edition;
    private String status;
    private Boolean isSandbox;
    private String contactEmail;
    private String phoneNumber;
    private String address;
    private String description;
    private String language;
    private String currency;
    private Boolean active;
}
