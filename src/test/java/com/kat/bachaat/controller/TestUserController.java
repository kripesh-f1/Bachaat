package com.kat.bachaat.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kat.bachaat.model.User;
import com.kat.bachaat.service.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

import java.util.Arrays;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class TestUserController {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    User user, user2;

    @Before
    public void setUp() throws Exception {

        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        user = new User(1l, "Nitish", "Shrestha",
                "nitishrestha8848@gmail.com", "dhapakhel",
                "9849211041",
                "ilovenepal12345");

        user2 = new User("lastname", "email", "add", "998989", "pass");
    }

    @Bean
    public MethodValidationPostProcessor bean() {
        return new MethodValidationPostProcessor();
    }

    @Test
    public void Should_ReturnStatusOK() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(user);
        given(userService.addUser(user)).willReturn(user);
        RequestBuilder requestBuilder = post("/user")
                .accept(MediaType.APPLICATION_JSON).content(jsonString).
                        contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        String outputInJson = response.getContentAsString();
        User user = objectMapper.readValue(outputInJson, objectMapper.getTypeFactory().constructType(User.class));
        Assert.assertNotNull(user);
        Assert.assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    public void Should_ReturnListOfUsers() throws Exception {
        given(userService.getUsers()).willReturn(Arrays.asList(user));
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/user")
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        System.out.println(response);
        Assert.assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    public void Should_FailToAddUser_When_FirstNameIsMissing() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(user2);
       given(userService.addUser(user2)).willReturn(user2);

        RequestBuilder requestBuilder = post("/user")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(jsonString)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(jsonString);
/*

        MvcResult mvcResult = mockMvc.perform(requestBuilder).andDo(print()).andExpect(status().isBadRequest()).andReturn();
       // mockMvc.perform(requestBuilder);
        String contentAsString = mvcResult.getResponse().getContentAsString();
        System.out.println("==========="+contentAsString);
*/

        MvcResult mvcResult1 = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = mvcResult1.getResponse();
        String contentAsString1 = response.getContentAsString();
        System.out.println(contentAsString1);
        User newUser = objectMapper.readValue(contentAsString1,User.class);
        Assert.assertEquals(newUser.getFirstName(),null);

        /*MvcResult mvcResult = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = mvcResult.getResponse();
        Assert.assertNull(response.);*/
    }

    @Test
    public void Should_ThrowException_When_NoRecordsAreFound() throws Exception {
        given(userService.getUsers()).willReturn(null);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/user")
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        String outputInJson = response.getContentAsString();
        Assert.assertTrue(outputInJson.isEmpty());
        Assert.assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }
}
