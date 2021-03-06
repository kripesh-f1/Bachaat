package com.f1soft.bachaat.controller;

import com.f1soft.bachaat.dto.response.MenuResponseDTO;
import com.f1soft.bachaat.entity.Menu;
import com.f1soft.bachaat.service.MenuService;
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

import java.util.logging.Logger;

import static com.f1soft.bachaat.utils.ApiConstant.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(MockitoJUnitRunner.class)
public class TestMenuController {

    private static Logger logger = Logger.getLogger(TestMenuController.class.getName());

    private MockMvc mockMvc;

    @Mock
    private MenuService menuService;

    @InjectMocks
    private MenuController menuController;

    private Menu menu;

    @Before
    public void setUp() throws Exception {
        mockMvc = MockMvcBuilders.standaloneSetup(menuController).build();
        menu = new Menu(1l, "Add User", 2l, "/add/user");
    }

    @Test
    public void Should_ReturnStatusOK_When_MenuAdded() throws Exception {
        logger.info("Inside Menu Add ");
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(menu);
        given(menuService.addMenu(menu)).willReturn(menu);
        RequestBuilder requestBuilder = post(API_VER + MENUS_PATH)
                .accept(MediaType.APPLICATION_JSON).content(jsonString).
                        contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        String outputInJson = response.getContentAsString();
        Assert.assertTrue(outputInJson.contains("Menu has been added successfully."));
    }

    @Test
    public void Should_FailToAddMenu_When_NameIsMissing() throws Exception {
        logger.info("Inside Add Menu ");
        String menuWithNoName = "{\"parentId\":2,\"link\":\"/home\"}";
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(API_VER + MENUS_PATH)
                .accept(MediaType.APPLICATION_JSON).content(menuWithNoName).contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        Assert.assertEquals(result.getResponse().getStatus(), 400);
    }

    @Test
    public void Should_FailToAddMenu_When_LinkIsMissing() throws Exception {
        logger.info("Inside Add Menu ");
        String menuWithNoName = "{\"name\":\"home\",\"parentId\":2}";
        RequestBuilder requestBuilder = MockMvcRequestBuilders.post(API_VER + MENUS_PATH)
                .accept(MediaType.APPLICATION_JSON).content(menuWithNoName).contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        Assert.assertEquals(result.getResponse().getStatus(), 400);
    }

    @Test(expected = IllegalArgumentException.class)
    public void Should_ThrowException_When_ThereIsNoSuchId() throws Exception {
        logger.info("Inside Get Menu When Invalid/Non Existing Id Is Passed");
        MockMvcRequestBuilders.post(API_VER + MENUS_PATH + MENU_PATH)
                .param("id", (String[]) null).contentType(MediaType.APPLICATION_JSON);
    }

    @Test
    public void getMenuDTOBydId_thenReturnStatusOK() throws Exception {
        logger.info("Inside Get Menu To Fetch Menu successfully");
        String param = String.valueOf(menu.getId());
        given(menuService.getMenuById(menu.getId())).willReturn(new MenuResponseDTO(menu));
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(API_VER + MENUS_PATH + MENU_PATH)
                .param("id", param).contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        Assert.assertEquals(HttpStatus.OK.value(), response.getStatus());
    }
}
