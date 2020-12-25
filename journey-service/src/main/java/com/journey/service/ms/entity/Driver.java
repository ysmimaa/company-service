package com.journey.service.ms.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

/**
 * Entity Driver
 */

@SuperBuilder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Driver extends IdEntityNonePersisted {
    private String firstname;
    private String lastname;
    private Address address;
    private ContractType contractType;
    private Journey journey;
    private LocalDateTime hiredDate;
    private Company company;
    private User user;
}
