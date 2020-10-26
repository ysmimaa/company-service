package com.driver.ms.entity;

import com.driver.ms.common.constant.DriverConstant;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

/**
 * Entity Driver
 */

@SuperBuilder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = DriverConstant.TABLE_NAME)
public class Driver extends IdEntity {

    @Column
    private String firstname;

    @Column
    private String lastname;

    @Column
    private String phone;

    @Column(name = "contractType")
    @Enumerated(EnumType.STRING)
    private ContractType contractType;

    @OneToOne
    @JoinColumn(name = "journey_id")
    private Journey journey;
}
