package com.company.service.ms.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

/**
 * Entity Driver
 */

@EqualsAndHashCode(callSuper = true)
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
    private LocalDateTime hiredDate;
    private User user;
    private Long journeyId;
    private Long companyId;
    private Journey journey;
    private Company company;

}
