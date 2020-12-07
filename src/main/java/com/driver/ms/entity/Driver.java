package com.driver.ms.entity;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

import java.time.LocalDateTime;

import static com.driver.ms.common.constant.DriverConstant.TABLE_NAME;

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
@Table(name = TABLE_NAME)
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
    @JoinColumn(name = "id_journey")
    private Journey journey;

    @Column
    private LocalDateTime hiredDate;
}
