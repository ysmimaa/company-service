package com.journey.service.ms.service;

import com.journey.service.ms.entity.Journey;
import com.journey.service.ms.repository.JourneyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JourneyServiceImpl implements JourneyService {

    private JourneyRepository journeyRepository;

    @Autowired
    public JourneyServiceImpl(JourneyRepository journeyRepository) {
        this.journeyRepository = journeyRepository;
    }

    @Override
    public Journey create(Journey journey) {
        if (journey != null) {
            return journeyRepository.save(journey);
        }
        return null;
    }

    @Override
    public Journey findById(Long id) {
        return null;
    }

    @Override
    public List<Journey> findAll() {
        return journeyRepository.findAll();
    }

    @Override
    public Journey deleteById(Long id) {
        if (id != null) {
            Optional<Journey> journeyById = journeyRepository.findById(id);
            journeyById.ifPresent((journey) -> journeyRepository.deleteById(journey.getId()));
            return journeyById.get();
        }
        return null;
    }

    @Override
    public Journey update(Journey journey) {
        return null;
    }
}
