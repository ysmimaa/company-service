package com.driver.ride.entity;

import com.driver.ride.common.constant.DriverConstant;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Entity Driver
 */

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = DriverConstant.TABLE_NAME)
public class Driver implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String firstname;

    @Column
    private String lastname;

    @Column
    private String phone;

    @Column
    @Enumerated(EnumType.STRING)
    private ContractType contractType;
}
