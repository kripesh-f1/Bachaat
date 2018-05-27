package com.kat.bachaat.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kat.bachaat.model.User;
import com.kat.bachaat.service.UserService;
import org.hamcrest.CoreMatchers;
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
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class TestUserController {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    User user;

    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {

        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
        user = new User("Nitish", "raj", "Shrestha",
                "nitishrestha8848@gmail.com", "dhapakhel",
                "9849211041",
                "ilovenepal12345");
    }

    @Test
    public void Should_ReturnStatusOK() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(user);
        given(userService.addUser(user)).willReturn(user);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/user")
                .accept(MediaType.APPLICATION_JSON).content(jsonString).
                        contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        String outputInJson = response.getContentAsString();
        User user = objectMapper.readValue(outputInJson, objectMapper.getTypeFactory().constructType(User.class));
        Assert.assertNotNull(user);
        Assert.assertEquals(HttpStatus.OK.value(), response.getStatus());
        Assert.assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    public void Should_FailToAddUser_When_SomeUserPropertyIsMissing() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(null);
        System.out.println(jsonString);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/user")
                .accept(MediaType.APPLICATION_JSON).content(jsonString).contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        Assert.assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    public void Should_FailToAddUser_When_FirstNameIsMissing() throws Exception {
        user = getUser();
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(user);
        System.out.println(jsonString);

        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("/user")
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonString).contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(jsonPath("$.key",is(CoreMatchers.nullValue())));
        MockHttpServletResponse response = result.getResponse();
        Assert.assertEquals(response.getErrorMessage(), "Please enter your first name!");
    }

    private User getUser() {
        User user = new User();
        user.setLastName("last name");
        user.setAddress("address");
        user.setMobileNumber("9898989898");
        user.setAddress("address");
        user.setEmailAddress("address@eemail.com");
        return user;
    }
}
