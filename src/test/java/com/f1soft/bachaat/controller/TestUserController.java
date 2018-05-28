package com.f1soft.bachaat.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.f1soft.bachaat.entity.User;
import com.f1soft.bachaat.service.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

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
        user = new User(1l, "nitish", "Shrestha",
                "nitishrestha8848@gmail.com", "dhapakhel",
                "9849211041",
                "ilovenepal12345");
        user2 = new User(1l, "Shrestha",
                "nitishrestha8848@gmail.com", "dhapakhel",
                "9849211041",
                "ilovenepal12345");
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
        String userWithNoFirstName = "{\"lastName\":\"Khanal\",\"mobileNumber\":\"94999545989\",\"emailAddress\":\"aasis@gmail.com\",\"address\":\"jhapa\",\"password\":\"password\"}";
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/user")
                .accept(MediaType.APPLICATION_JSON).content(userWithNoFirstName).contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        Assert.assertEquals(result.getResponse().getStatus(), 400);
    }

    @Test
    public void Should_FailToAddUser_When_LastNameIsMissing() throws Exception {
        String userWithNoFirstName = "{\"firstName\":\"Aashis\",\"mobileNumber\":\"94999545989\",\"emailAddress\":\"aasis@gmail.com\",\"address\":\"jhapa\",\"password\":\"password\"}";
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/user")
                .accept(MediaType.APPLICATION_JSON).content(userWithNoFirstName).contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        Assert.assertEquals(result.getResponse().getStatus(), 400);
    }

    @Test
    public void Should_FailToAddUser_When_AddressIsMissing() throws Exception {
        String userWithNoFirstName = "{\"firstName\":\"Aashis\",\"mobileNumber\":\"94999545989\",\"emailAddress\":\"aasis@gmail.com\",\"password\":\"password\"}";
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/user")
                .accept(MediaType.APPLICATION_JSON).content(userWithNoFirstName).contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        Assert.assertEquals(result.getResponse().getStatus(), 400);
    }

    @Test
    public void Should_FailToAddUser_When_EmailAddressIsMissing() throws Exception {
        String userWithNoFirstName = "{\"firstName\":\"Aashis\",\"mobileNumber\":\"94999545989\",\"address\":\"jhapa\",\"password\":\"password\"}";
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/user")
                .accept(MediaType.APPLICATION_JSON).content(userWithNoFirstName).contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        Assert.assertEquals(result.getResponse().getStatus(), 400);
    }

    @Test
    public void Should_FailToAddUser_When_PasswordIsMissing() throws Exception {
        String userWithNoFirstName = "{\"firstName\":\"Aashis\",\"mobileNumber\":\"94999545989\",\"emailAddress\":\"aasis@gmail.com\",\"address\":\"jhapa\"}";
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/user")
                .accept(MediaType.APPLICATION_JSON).content(userWithNoFirstName).contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        Assert.assertEquals(result.getResponse().getStatus(), 400);
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
