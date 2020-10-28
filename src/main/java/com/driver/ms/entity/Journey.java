package com.driver.ms.entity;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;

import static com.driver.ms.common.constant.JourneyConstant.TABLE_NAME;

/**
 * Journey entity
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@Entity
@Table(name = TABLE_NAME)
public class Journey extends IdEntity {

    @Column
    private String city;

    @Column
    private String address;

    @Column
    private Integer nbrOfPlaces;

    @Column
    private LocalDateTime startAt;

    @Column
    private LocalDateTime endAt;

    @ManyToOne
    @JoinColumn(name = "id_company")
    private Company company;

    @OneToOne
    @JoinColumn(name = "id_driver")
    private Driver driver;
}
