package com.kat.bachaat.service;

import com.kat.bachaat.dao.RoleRepository;
import com.kat.bachaat.dao.UserRepository;
import com.kat.bachaat.model.Role;
import com.kat.bachaat.model.User;
import com.kat.bachaat.service.impl.UserServiceImpl;
import com.kat.bachaat.util.ActivationCodeUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.when;

public class TestUserService {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private ActivationCodeUtil activationCodeUtil;

    @InjectMocks
    UserServiceImpl userService;

    User user;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
        user = new User( "Nitish", "raj", "Shrestha",
                "nitishrestha8848@gmail.com", "dhapakhel",
                "9849211041", "ilovenepal12345");
    }

    @Test
    public void addUser() {
        when(activationCodeUtil.getActivationCode()).thenReturn(anyInt());
        when(userRepository.findByMobileNumber(user.getMobileNumber())).thenReturn(null);
        when(roleRepository.findByName("USER")).thenReturn(new Role());
        when(userRepository.save(user)).thenReturn(user);
        when(userService.addUser(user)).thenReturn(user);
        Assert.assertNotNull(userService.addUser(user));
    }
}