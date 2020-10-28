package com.driver.ms.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

import static com.driver.ms.common.constant.CompanyConstant.TABLE_NAME;
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
@Table(name = TABLE_NAME)
public class Company extends IdEntity {

    @Column
    private String name;

    @Column
    private String activity;

    @Builder.Default
    @OneToMany(mappedBy = "company", fetch = LAZY, cascade = ALL)
    private List<Journey> journeys = new ArrayList<>();
}
