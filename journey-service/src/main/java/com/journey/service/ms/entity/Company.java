package com.journey.service.ms.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

/**
 * Company entity
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Company extends IdEntityNonePersisted {
    private String name;
    private String activity;
    private Address address;
    private List<Driver> drivers;
    private List<Factories> factories;
}
