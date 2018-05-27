package com.kat.bachaat.controller;

import com.kat.bachaat.model.User;
import com.kat.bachaat.service.UserService;
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
        user = new User(1,"admin", "admin",
                "admin@admin.com", "admin",
                "9813131", "ram");
    }

    @Test
    public void Should_DeleteUserRecord() throws Exception {
        String id=user.getId().toString();
        given(userService.deleteUser(user.getId())).willReturn(true);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/user/delete")
                .param("id",id).contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        Assert.assertEquals(HttpStatus.OK.value(), response.getStatus());
    }


    @Test
    public void Should_ThrowException_When_NoRecordsOfSuchIdIsFound() throws Exception {
        String id=user.getId().toString();
        given(userService.deleteUser(user.getId())).willReturn(false);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("/user/delete")
                .param("id",id).contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        String outputInJson = response.getContentAsString();
        Boolean param=Boolean.parseBoolean(outputInJson);
        Assert.assertFalse(param);
        Assert.assertEquals(HttpStatus.CONFLICT.value(), response.getStatus());
    }
}
