package com.journey.service.ms.entity;


import static com.journey.service.ms.common.constant.JourneyConstant.TABLE_NAME;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    @Embedded
    private Address address;

    @Column(name = "NBR_OF_PLACES")
    private Integer nbrOfPlaces;

    @Column(name = "START_AT")
    private LocalDateTime startAt;

    @Column(name = "END_AT")
    private LocalDateTime endAt;

    @Column(name = "company_id")
    private Long companyId;

    @Column(name = "driver_id")
    private Long driverId;

    @Transient
    private Company company;

    @Transient
    private Driver driver;
}
