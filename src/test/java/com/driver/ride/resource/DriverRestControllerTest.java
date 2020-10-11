package com.driver.ride.resource;

import com.driver.ride.common.constant.utils.JsonUtils;
import com.driver.ride.service.DriverService;
import com.driver.ride.common.constant.DriverConstant;
import com.driver.ride.entity.Driver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.AssertionErrors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class DriverRestControllerTest {

    @InjectMocks
    private DriverRestController driverRestController;

    @Mock
    private DriverService driverService;

    private MockMvc mockMvc;

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(driverRestController).build();
    }

    @Test
    void should_return_a_list_of_user() throws Exception {
        //When
        Mockito.when(driverService.getListOfDriver()).thenReturn(Arrays.asList(
                Driver.builder()
                        .id(1L)
                        .firstname("firstname1")
                        .build(),
                Driver.builder()
                        .id(2L)
                        .firstname("firstname2")
                        .build())
        );

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(DriverConstant.DRIVER_URL_BASE + DriverConstant.DRIVERS))
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        List<Driver> driverList = JsonUtils.deserializeStringToList(content, Driver.class);

        //Then
        AssertionErrors.assertEquals("Two users found ", 2, driverList.size());

    }

    @Test
    void should_return_an_empty_list_of_user() throws Exception {
        //When
        Mockito.when(driverService.getListOfDriver()).thenReturn(new ArrayList<>());

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(DriverConstant.DRIVER_URL_BASE + DriverConstant.DRIVERS))
                .andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        List<Driver> driverList = JsonUtils.deserializeStringToList(content, Driver.class);

        //Then
        AssertionErrors.assertEquals("No user found ", 0, driverList.size());

    }

}