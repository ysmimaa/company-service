package com.company.service.ms.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
public class Factories extends IdEntityNonePersisted {
    private String name;
    private Address address;
    private String description;

}
