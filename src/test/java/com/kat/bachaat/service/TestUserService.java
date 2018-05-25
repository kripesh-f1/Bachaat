package com.kat.bachaat.service;

import com.kat.bachaat.dao.UserRepository;
import com.kat.bachaat.exception.DataNotFoundException;
import com.kat.bachaat.model.Role;
import com.kat.bachaat.model.User;
import com.kat.bachaat.service.impl.UserServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;

import static org.mockito.Mockito.when;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class TestUserService {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    UserServiceImpl userService;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    User user;

    @Before
    public void setUp() throws Exception{
        MockitoAnnotations.initMocks(this);
        user = new User("admin", "admin",
                "admin@admin.com", "admin",
                "9813131", "ram");
    }

    @Test
    public void Should_ReturnListOfUser(){
        when(userRepository.findAll()).thenReturn(Arrays.asList(user));
        Assert.assertNotNull(userService.getUsers());
    }

    @Test(expected = DataNotFoundException.class)
    public void Should_ThrowException_When_NoRecordsAreFound(){
        when(userService.getUsers()).thenReturn(Arrays.asList(null));
        userService.getUsers();
    }
}
