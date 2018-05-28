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

import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class TestUserController {

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    User user;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        user = new User(1, "admin", "admin",
                "admin@admin.com", "admin",
                "9813131", "ram");
    }

    @Test
    public void Should_DeleteUserRecord() throws Exception {
        String id = user.getId().toString();
        given(userService.deleteUser(user.getId())).willReturn(true);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/user/delete")
                .param("id", id).contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        String outputInJson = response.getContentAsString();
        Assert.assertTrue(outputInJson.contains("User of id 1 has been deleted"));
    }

    @Test
    public void Should_ThrowException_When_NoRecordsOfSuchIdIsFound() throws Exception {
        String id = user.getId().toString();
        given(userService.deleteUser(user.getId())).willReturn(false);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/user/delete")
                .param("id", id).contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        String outputInJson = response.getContentAsString();
        Boolean param = Boolean.parseBoolean(outputInJson);
        Assert.assertFalse(param);
        Assert.assertEquals(HttpStatus.CONFLICT.value(), response.getStatus());
    }

    @Test(expected = IllegalArgumentException.class)
    public void Should_ThrowException_When_ThereisNoSuchId() throws Exception {
        MockMvcRequestBuilders.post("/user/delete")
                .param("id", (String[]) null).contentType(MediaType.APPLICATION_JSON);
    }

    @Test
    public void Should_ReturnListOfUsers() throws Exception {
        given(userService.getUsers()).willReturn(Arrays.asList(user));
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/user")
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        Assert.assertEquals(HttpStatus.OK.value(), response.getStatus());
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

    @Test
    public void updateFood_thenReturnStatusOK() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(user);
        given(userService.updateUser(user)).willReturn(user);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/user/update")
                .accept(MediaType.APPLICATION_JSON).content(jsonString).contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        String outputInJson = response.getContentAsString();
        Assert.assertTrue(outputInJson.contains("User has been updated successfully!"));
    }

    @Test
    public void Should_ThrowException_When_NoUserIsFound() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(user);
        given(userService.updateUser(user)).willReturn(null);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/user/update")
                .accept(MediaType.APPLICATION_JSON).content(jsonString).contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        Assert.assertEquals(response.getStatus(), 409);
    }

}
