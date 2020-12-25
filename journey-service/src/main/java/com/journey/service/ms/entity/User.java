package com.journey.service.ms.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

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
