package com.f1soft.bachaat.service;

import com.f1soft.bachaat.entity.Menu;
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

import java.util.logging.Logger;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TestMenuService {

    private static Logger logger = Logger.getLogger(TestMenuService.class.getName());

    @Mock
    private MenuRepository menuRepository;

    @InjectMocks
    MenuServiceImpl menuService;

    private Menu menu;

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
        when(menuService.addMenu(menu)).thenReturn(menu);
        Assert.assertNotNull(menuService.addMenu(menu));
    }

}
