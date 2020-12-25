package com.journey.service.ms.service;

import com.journey.service.ms.entity.Journey;

import java.util.List;

public interface JourneyService {

    Journey create(Journey journey);

    Journey findById(Long id);

    List<Journey> findAll();

    Journey deleteById(Long id);

    Journey update(Journey journey);
}
