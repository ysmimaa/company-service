package com.company.service.ms.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Setter
@Getter
@NoArgsConstructor
public class User extends IdEntityNonePersisted {
    private String username;
    private String password;
    private String validatePassword;
    private String email;
    private Driver driver;
}
