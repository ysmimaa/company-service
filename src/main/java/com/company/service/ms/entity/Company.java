package com.company.service.ms.entity;

import com.company.service.ms.common.constant.CommonConstant;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.util.List;

/**
 * Company entity
 */

@EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Entity
@Table(name = CommonConstant.TABLE_NAME)
public class Company extends IdEntity {

    @Column
    private String name;

    @Column
    private String activity;

    @Embedded
    private Address address;

    @Transient
    private List<Driver> drivers;

    @Transient
    private List<Factories> factories;
}
