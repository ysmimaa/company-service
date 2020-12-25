package com.journey.service.ms.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@SuperBuilder
@Getter
public abstract class IdEntityNonePersisted {
    private Long id;
}
