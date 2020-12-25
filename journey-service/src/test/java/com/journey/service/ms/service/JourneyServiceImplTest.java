package com.journey.service.ms.service;

import com.journey.service.ms.entity.Address;
import com.journey.service.ms.entity.Company;
import com.journey.service.ms.entity.Journey;
import com.journey.service.ms.repository.JourneyRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

class JourneyServiceImplTest {

    @Mock
    private JourneyRepository journeyRepository;

    @InjectMocks
    private JourneyService journeyService = new JourneyServiceImpl(journeyRepository);

    @BeforeEach
    public void init() {

        MockitoAnnotations.initMocks(this);
    }

    @Test
    void should_create_a_journey() {
        //Given
        Journey journey = Journey.builder()
                .id(1L)
                .startAt(LocalDateTime.now())
                .endAt(LocalDateTime.now().minusHours(5))
                .address(
                        Address.builder()
                                .street("street1")
                                .city("city1")
                                .build())
                .nbrOfPlaces(22)
                .company(
                        Company.builder()
                                .name("company1")
                                .activity("activité")
                                .build())
                .build();

        //When
        Mockito.when(journeyRepository.save(ArgumentMatchers.any(Journey.class))).thenReturn(journey);

        Journey createdJourney = journeyService.create(journey);

        //Then
        Assertions.assertThat(createdJourney).isEqualToComparingFieldByField(journey);

        Mockito.verify(journeyRepository, Mockito.times(1)).save(ArgumentMatchers.any(Journey.class));

    }

    @Test
    void findById() {

    }

    @Test
    void should_return_a_list_of_journey() {
        //Given
        List<Journey> journeys = Arrays.asList(Journey.builder()
                .id(1L)
                .startAt(LocalDateTime.now())
                .endAt(LocalDateTime.now().minusHours(5))
                .address(
                        Address.builder()
                                .street("street1")
                                .city("city1")
                                .build())
                .nbrOfPlaces(22)
                .company(
                        Company.builder()
                                .name("company1")
                                .activity("activité")
                                .build())
                .build()
        );
        //When
        Mockito.when(journeyRepository.findAll()).thenReturn(journeys);

        List<Journey> listOfJourneys = journeyService.findAll();

        //Then
        Assertions.assertThat(listOfJourneys).contains(journeys.get(0));

        Mockito.verify(journeyRepository, Mockito.times(1)).findAll();


    }

    @Test
    void should_delete_journey_by_its_id() {
        //Given
        Journey journeyToDelete = Journey.builder()
                .id(1L)
                .startAt(LocalDateTime.now())
                .endAt(LocalDateTime.now().minusHours(5))
                .address(
                        Address.builder()
                                .street("street1")
                                .city("city1")
                                .build())
                .nbrOfPlaces(22)
                .company(
                        Company.builder()
                                .name("company1")
                                .activity("activité")
                                .build())
                .build();

        //When
        Mockito.doNothing().when(journeyRepository).deleteById(ArgumentMatchers.anyLong());
        Mockito.when(journeyRepository.findById(ArgumentMatchers.anyLong())).thenReturn(Optional.of(journeyToDelete));

        Journey deletedJourney = journeyService.deleteById(journeyToDelete.getId());

        //Then
        Assertions.assertThat(deletedJourney).isEqualToComparingFieldByField(journeyToDelete);

        Mockito.verify(journeyRepository, Mockito.times(1)).deleteById(ArgumentMatchers.anyLong());
        Mockito.verify(journeyRepository, Mockito.times(1)).findById(ArgumentMatchers.anyLong());


    }

    @Test
    void update() {
    }
}