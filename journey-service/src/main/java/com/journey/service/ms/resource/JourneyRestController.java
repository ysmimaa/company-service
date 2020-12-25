package com.journey.service.ms.resource;

import com.journey.service.ms.common.constant.CommonConstant;
import com.journey.service.ms.common.constant.JourneyConstant;
import com.journey.service.ms.entity.Journey;
import com.journey.service.ms.service.JourneyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(path = CommonConstant.DRIVER_URL_BASE)
public class JourneyRestController {

    private JourneyService journeyService;

    @Autowired
    public JourneyRestController(JourneyService journeyService) {
        this.journeyService = journeyService;
    }

    @GetMapping(path = JourneyConstant.JOURNEYS)
    public ResponseEntity<List<Journey>> getAllJourney() {
        List<Journey> journeys = journeyService.findAll();
        if (!CollectionUtils.isEmpty(journeys)) {
            return new ResponseEntity<>(journeys, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping(path = JourneyConstant.JOURNEY)
    public ResponseEntity<Journey> createJourney(@RequestBody Journey journey) {
        Journey createdJourney = journeyService.create(journey);
        if (createdJourney != null) {
            return new ResponseEntity<>(journey, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(path = JourneyConstant.JOURNEY + JourneyConstant.DELETE_JOURNEY_BY_ID)
    public ResponseEntity<Journey> deleteJourneyById(@PathVariable(name = "id") Long id) {
        if (id != null) {
            Journey journey = journeyService.deleteById(id);
            return new ResponseEntity<>(journey, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }
}
