package com.f1soft.bachaat.controller;

import com.f1soft.bachaat.dto.request.UserRequestDTO;
import com.f1soft.bachaat.dto.response.UserResponseDTO;
import com.f1soft.bachaat.dto.response.UserResponseDTOList;
import com.f1soft.bachaat.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
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
@EnableSpringDataWebSupport
public class TestUserController {

    private static Logger logger = Logger.getLogger(TestUserController.class.getName());

    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    UserRequestDTO userRequestDTO;

    UserResponseDTO userResponseDTO;

    Pageable pageable;

    Page<UserResponseDTO> pagedResponse;

    UserResponseDTOList userResponseDTOList;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver()).build();
        userRequestDTO = new UserRequestDTO(1l, "admin", "admin",
                "admin", "admin@admin.com",
                "admin",
                "1234567890", "admin");
        userResponseDTO = new UserResponseDTO( "admin", "admin",
                "admin", "admin@admin.com", "admin", "1234567890", "admin");
        pageable = PageRequest.of(0, 1);
        pagedResponse = new PageImpl<>(Arrays.asList(userResponseDTO));
        userResponseDTOList=new UserResponseDTOList(8,Arrays.asList(userResponseDTO));
    }

    @Test
    public void Should_ReturnStatusOK() throws Exception {
        logger.info("Inside User Add should return status 200");
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(userRequestDTO);
        given(userService.addUser(userRequestDTO)).willReturn(userResponseDTO);
        RequestBuilder requestBuilder = post(API_VER + USERS_PATH)
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
        String id = userRequestDTO.getId().toString();
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(API_VER + USERS_PATH + DELETE_PATH)
                .param("id", id).contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        String outputInJson = response.getContentAsString();
        Assert.assertTrue(outputInJson.contains("User with id: 1 has been deleted successfully."));
    }

    @Test(expected = IllegalArgumentException.class)
    public void Should_ThrowException_When_ThereisNoSuchId() throws Exception {
        logger.info("Inside User Delete When Invalid Argument Is Passed");
        MockMvcRequestBuilders.post(API_VER + USERS_PATH + DELETE_PATH)
                .param("id", (String[]) null).contentType(MediaType.APPLICATION_JSON);
    }

    @Test
    public void Should_ReturnListOfUsers() throws Exception {
        logger.info("Inside Get User Controller To Fetch All User");

        String pageNumber=String.valueOf(pageable.getPageNumber());
        String size=String.valueOf(pageable.getPageSize());
        given(userService.getUsers(pageable,"firstName","DESC")).willReturn(userResponseDTOList);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(API_VER + USERS_PATH)
                .param("page", pageNumber).param("size",size).param("sort","firstName")
                .param("order","DESC").contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        Assert.assertEquals(HttpStatus.OK.value(), response.getStatus());
    }

    @Test
    public void Should_FailToAddUser_When_FirstNameIsMissing() throws Exception {
        logger.info("Inside Add User ");
        String userWithNoFirstName = "{\"lastName\":\"Khanal\",\"mobileNumber\":\"94999545989\",\"emailAddress\":\"aasis@gmail.com\",\"address\":\"jhapa\",\"password\":\"password\"}";
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(API_VER + USERS_PATH)
                .accept(MediaType.APPLICATION_JSON).content(userWithNoFirstName).contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        Assert.assertEquals(result.getResponse().getStatus(), 400);
    }

    @Test
    public void Should_FailToAddUser_When_LastNameIsMissing() throws Exception {
        logger.info("Inside Add User ");
        String userWithNoFirstName = "{\"firstName\":\"Aashis\",\"mobileNumber\":\"94999545989\",\"emailAddress\":\"aasis@gmail.com\",\"address\":\"jhapa\",\"password\":\"password\"}";
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(API_VER + USERS_PATH)
                .accept(MediaType.APPLICATION_JSON).content(userWithNoFirstName).contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        Assert.assertEquals(result.getResponse().getStatus(), 400);
    }

    @Test
    public void Should_FailToAddUser_When_AddressIsMissing() throws Exception {
        logger.info("Inside Add User ");
        String userWithNoFirstName = "{\"firstName\":\"Aashis\",\"mobileNumber\":\"94999545989\",\"emailAddress\":\"aasis@gmail.com\",\"password\":\"password\"}";
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(API_VER + USERS_PATH)
                .accept(MediaType.APPLICATION_JSON).content(userWithNoFirstName).contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        Assert.assertEquals(result.getResponse().getStatus(), 400);
    }

    @Test
    public void Should_FailToAddUser_When_EmailAddressIsMissing() throws Exception {
        logger.info("Inside Add User ");
        String userWithNoFirstName = "{\"firstName\":\"Aashis\",\"mobileNumber\":\"94999545989\",\"address\":\"jhapa\",\"password\":\"password\"}";
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(API_VER + USERS_PATH)
                .accept(MediaType.APPLICATION_JSON).content(userWithNoFirstName).contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        Assert.assertEquals(result.getResponse().getStatus(), 400);
    }

    @Test
    public void Should_FailToAddUser_When_PasswordIsMissing() throws Exception {
        logger.info("Inside Add User ");
        String userWithNoFirstName = "{\"firstName\":\"Aashis\",\"mobileNumber\":\"94999545989\",\"emailAddress\":\"aasis@gmail.com\",\"address\":\"jhapa\"}";
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(API_VER + USERS_PATH)
                .accept(MediaType.APPLICATION_JSON).content(userWithNoFirstName).contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        Assert.assertEquals(result.getResponse().getStatus(), 400);
    }

    @Test
    public void updateUser_thenReturnStatusOK() throws Exception {
        logger.info("Inside Update User ");
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(userRequestDTO);
        given(userService.updateUser(userRequestDTO)).willReturn(userResponseDTO);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(API_VER + USERS_PATH + UPDATE_PATH)
                .accept(MediaType.APPLICATION_JSON).content(jsonString).contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        String outputInJson = response.getContentAsString();
        Assert.assertTrue(outputInJson.contains("User with id: 1 has been updated successfully."));
    }

    @Test
    public void Should_ReturnUserResponseDto() throws Exception {
        logger.info("Inside Get User ");
        ObjectMapper mapper = new ObjectMapper();
        given(userService.getUser(userRequestDTO.getId())).willReturn(userResponseDTO);
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(API_VER + USERS_PATH + "/1")
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        String outputInJson = response.getContentAsString();
        UserResponseDTO userResponseDTO=mapper.readValue(outputInJson,UserResponseDTO.class);
        Assert.assertNotNull(userResponseDTO);

    }
}
