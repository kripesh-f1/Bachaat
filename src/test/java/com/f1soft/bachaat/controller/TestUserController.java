package com.f1soft.bachaat.controller;

import com.f1soft.bachaat.entity.User;
import com.f1soft.bachaat.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import java.util.logging.Logger;

import static com.f1soft.bachaat.utils.ApiConstant.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(MockitoJUnitRunner.class)
public class TestUserController {

    private static Logger logger = Logger.getLogger(TestUserController.class.getName());

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    User user;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        user = new User(1l, "nitish", "Shrestha",
                "nitishrestha8848@gmail.com", "dhapakhel",
                "9849211041",
                "ilovenepal12345");
    }

    @Test
    public void Should_ReturnStatusOK() throws Exception {
        logger.info("Inside User Add should return status 200");
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(user);
        given(userService.addUser(user)).willReturn(user);
        RequestBuilder requestBuilder = post(API_VER + USER_PATH)
                .accept(MediaType.APPLICATION_JSON).content(jsonString).
                        contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        String outputInJson = response.getContentAsString();
        Assert.assertTrue(outputInJson.contains("User has been added successfully."));
    }

    @Test
    public void Should_DeleteUserRecord() throws Exception {
        logger.info("Inside User Delete should return status 200");
        String id = user.getId().toString();
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(API_VER + USER_PATH + DELETE_PATH)
                .param("id", id).contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        String outputInJson = response.getContentAsString();
        Assert.assertTrue(outputInJson.contains("User with id 1 has been deleted"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void Should_ThrowException_When_ThereisNoSuchId() throws Exception {
        logger.info("Inside User Delete When Invalid Argument Is Passed");
        MockMvcRequestBuilders.post(API_VER + USER_PATH + DELETE_PATH)
                .param("id", (String[]) null).contentType(MediaType.APPLICATION_JSON);
    }

    @Test
    public void Should_ReturnListOfUsers() throws Exception {
        logger.info("Inside Get User Controller To Fetch All User");
        given(userService.getUsers()).willReturn(Arrays.asList(user));
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(API_VER + USER_PATH)
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        System.out.println(response);
        Assert.assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    public void Should_FailToAddUser_When_FirstNameIsMissing() throws Exception {
        logger.info("Inside Add User ");
        String userWithNoFirstName = "{\"lastName\":\"Khanal\",\"mobileNumber\":\"94999545989\",\"emailAddress\":\"aasis@gmail.com\",\"address\":\"jhapa\",\"password\":\"password\"}";
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(API_VER + USER_PATH)
                .accept(MediaType.APPLICATION_JSON).content(userWithNoFirstName).contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        Assert.assertEquals(result.getResponse().getStatus(), 400);
    }

    @Test
    public void Should_FailToAddUser_When_LastNameIsMissing() throws Exception {
        logger.info("Inside Add User ");
        String userWithNoFirstName = "{\"firstName\":\"Aashis\",\"mobileNumber\":\"94999545989\",\"emailAddress\":\"aasis@gmail.com\",\"address\":\"jhapa\",\"password\":\"password\"}";
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(API_VER + USER_PATH)
                .accept(MediaType.APPLICATION_JSON).content(userWithNoFirstName).contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        Assert.assertEquals(result.getResponse().getStatus(), 400);
    }

    @Test
    public void Should_FailToAddUser_When_AddressIsMissing() throws Exception {
        logger.info("Inside Add User ");
        String userWithNoFirstName = "{\"firstName\":\"Aashis\",\"mobileNumber\":\"94999545989\",\"emailAddress\":\"aasis@gmail.com\",\"password\":\"password\"}";
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(API_VER + USER_PATH)
                .accept(MediaType.APPLICATION_JSON).content(userWithNoFirstName).contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        Assert.assertEquals(result.getResponse().getStatus(), 400);
    }

    @Test
    public void Should_FailToAddUser_When_EmailAddressIsMissing() throws Exception {
        logger.info("Inside Add User ");
        String userWithNoFirstName = "{\"firstName\":\"Aashis\",\"mobileNumber\":\"94999545989\",\"address\":\"jhapa\",\"password\":\"password\"}";
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(API_VER + USER_PATH)
                .accept(MediaType.APPLICATION_JSON).content(userWithNoFirstName).contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        Assert.assertEquals(result.getResponse().getStatus(), 400);
    }

    @Test
    public void Should_FailToAddUser_When_PasswordIsMissing() throws Exception {
        logger.info("Inside Add User ");
        String userWithNoFirstName = "{\"firstName\":\"Aashis\",\"mobileNumber\":\"94999545989\",\"emailAddress\":\"aasis@gmail.com\",\"address\":\"jhapa\"}";
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(API_VER + USER_PATH)
                .accept(MediaType.APPLICATION_JSON).content(userWithNoFirstName).contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        Assert.assertEquals(result.getResponse().getStatus(), 400);
    }

        @Test
    public void updateFood_thenReturnStatusOK() throws Exception {
            logger.info("Inside Update User ");
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(user);
        given(userService.updateUser(user)).willReturn(user);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(API_VER + USER_PATH + UPDATE_PATH)
                .accept(MediaType.APPLICATION_JSON).content(jsonString).contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        String outputInJson = response.getContentAsString();
        Assert.assertTrue(outputInJson.contains("User has been updated successfully."));
    }
}
