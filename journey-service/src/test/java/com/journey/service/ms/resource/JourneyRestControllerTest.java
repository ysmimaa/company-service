package com.journey.service.ms.resource;

import com.journey.service.ms.common.constant.JourneyConstant;
import com.journey.service.ms.common.utils.JsonUtils;
import com.journey.service.ms.entity.Journey;
import com.journey.service.ms.service.JourneyService;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

class JourneyRestControllerTest {

    private final String BASE_URL = "http://localhost:8010/api/";

    @InjectMocks
    private JourneyRestController journeyRestController;

    @Mock
    private JourneyService journeyService;

    private MockMvc mockMvc;

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(journeyRestController).build();
    }

    @Test
    void should_return_all_journeys() throws Exception {
        //Given
        List<Journey> journeys = Arrays.asList(Journey.builder()
                        .id(1L)
                        .nbrOfPlaces(20)
                        .startAt(LocalDateTime.now())
                        .endAt(LocalDateTime.now().minusHours(1))
                        .build(),
                Journey.builder()
                        .id(2L)
                        .nbrOfPlaces(26)
                        .startAt(LocalDateTime.now())
                        .endAt(LocalDateTime.now().minusHours(1))
                        .build(),
                Journey.builder()
                        .id(3L)
                        .nbrOfPlaces(28)
                        .startAt(LocalDateTime.now())
                        .endAt(LocalDateTime.now().minusHours(1))
                        .build());

        //When
        Mockito.when(journeyService.findAll()).thenReturn(journeys);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(BASE_URL + JourneyConstant.JOURNEYS)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is2xxSuccessful())
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();

        List<Journey> returnedListOfJourney = JsonUtils.deserializeStringToList(content, Journey.class);

        //Then
        Assert.assertEquals(returnedListOfJourney.size(), journeys.size());

        Mockito.verify(journeyService, Mockito.times(1)).findAll();

    }

    @Test
    void should_create_a_journey() throws Exception {
        //Given
        Journey createdJourney = Journey.builder()
                .id(1L)
                .nbrOfPlaces(20)
                .startAt(LocalDateTime.now())
                .endAt(LocalDateTime.now()
                        .minusHours(1))
                .build();

        //When
        Mockito.when(journeyService.create(ArgumentMatchers.any(Journey.class))).thenReturn(createdJourney);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(BASE_URL + JourneyConstant.JOURNEY)
                .content(JsonUtils.serializeObjectToString(createdJourney))
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        Journey returnedJourney = JsonUtils.deserializeStringToObject(content, Journey.class);

        //Then
        Assert.assertEquals(returnedJourney.getNbrOfPlaces(), createdJourney.getNbrOfPlaces());

        Mockito.verify(journeyService, Mockito.times(1)).create(ArgumentMatchers.any(Journey.class));
    }


    @Test
    void should_delete_a_journey_by_its_id() throws Exception {
        //Given
        Journey journey = Journey.builder()
                .id(1L)
                .nbrOfPlaces(20)
                .startAt(LocalDateTime.now())
                .endAt(LocalDateTime.now()
                        .minusHours(1))
                .build();

        //When
        Mockito.when(journeyService.deleteById(ArgumentMatchers.anyLong())).thenReturn(journey);

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete(BASE_URL + JourneyConstant.JOURNEY + JourneyConstant.DELETE_JOURNEY_BY_ID, "1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        Journey returnedJourney = JsonUtils.deserializeStringToObject(content, Journey.class);

        //Then
        Assert.assertEquals(returnedJourney.getNbrOfPlaces(), journey.getNbrOfPlaces());

        Mockito.verify(journeyService, Mockito.times(1)).deleteById(ArgumentMatchers.anyLong());
    }
}