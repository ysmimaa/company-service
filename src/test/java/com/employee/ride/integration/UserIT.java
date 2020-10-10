package com.employee.ride.integration;

import com.employee.ride.common.constant.UserConstant;
import com.employee.ride.common.constant.utils.JsonUtils;
import com.employee.ride.entity.User;
import com.employee.ride.service.UserService;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.hamcrest.collection.IsCollectionWithSize;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Test
    void should_return_a_list_of_users() throws Exception {
        User user = userService.createUser(User.builder()
                .id(1L)
                .firstname("TI1")
                .build());

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get(UserConstant.USER_URL_BASE.concat(UserConstant.USERS)))
                .andReturn();
        String content = result.getResponse().getContentAsString();

        List<User> users = JsonUtils.deserializeStringToList(content, User.class);

        MatcherAssert.assertThat(users, IsCollectionWithSize.hasSize(4));
    }
}
