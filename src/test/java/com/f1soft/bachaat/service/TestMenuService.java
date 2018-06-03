
package com.f1soft.bachaat.service;

import com.f1soft.bachaat.entity.Menu;
import com.f1soft.bachaat.exception.MenuAlreadyExistsException;
import com.f1soft.bachaat.repository.MenuRepository;
import com.f1soft.bachaat.service.impl.MenuServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.logging.Logger;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TestMenuService {

    private static Logger logger = Logger.getLogger(TestMenuService.class.getName());

    @InjectMocks
    MenuServiceImpl menuService;

    @Mock
    MenuRepository menuRepository;

    Menu menu;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        menu = new Menu(1l, "Add User", 2l, "/add/user");
    }

    @Test
    public void Should_ReturnStatusOK_When_MenuAdded() {
        logger.info("Inside Test Menu Add Service To Add Menu Successfully");
        when(menuRepository.save(menu)).thenReturn(menu);
        when(menuService.addMenu(menu)).thenReturn(menu);
        Assert.assertNotNull(menuService.addMenu(menu));
    }

    @Test
    public void Should_ReturnListOfMenu() {
        logger.info("Inside Test Menu Get All to fetch all Users");
        when(menuRepository.findAll()).thenReturn(Arrays.asList(menu));
        Assert.assertNotNull(menuRepository.findAll());
    }

    @Test(expected = MenuAlreadyExistsException.class)
    public void Should_ThrowException_WhenMenuDataIsPassed() {
        logger.info("Inside Test Menu Add when same menu data is passed");
        when(menuRepository.findByNameAndLinkAndParentId(menu.getName(), menu.getLink(), menu.getParentId())).thenThrow(MenuAlreadyExistsException.class);
        menuService.addMenu(menu);
    }

}
