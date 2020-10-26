package com.driver.ms.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;

/**
 * Company entity
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = "t_company")
public class Company extends IdEntity {

    @Column
    private String name;

    @Column
    private String activity;

    @OneToMany(mappedBy = "company", fetch = LAZY, cascade = ALL)
    private List<Journey> journeys = new ArrayList<>();
}
