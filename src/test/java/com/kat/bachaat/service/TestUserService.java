package com.kat.bachaat.service;

import com.kat.bachaat.dao.UserRepository;
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

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;

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
        user = new User(1,"admin", "admin",
                "admin@admin.com", "admin",
                "9813131", "ram");
    }

    @Test
    public void Should_DeleteUserOfThatId(){
        doNothing().when(userRepository).deleteById(user.getId());
        Assert.assertTrue(userService.deleteUser(user.getId()));
    }

    @Test(expected = IllegalArgumentException.class)
    public void Should_Throw(){
        doThrow(new IllegalArgumentException()).when(userRepository).deleteById(user.getId());
        userService.deleteUser(user.getId());
    }
}
