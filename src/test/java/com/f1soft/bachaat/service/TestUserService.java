package com.f1soft.bachaat.service;

import com.f1soft.bachaat.entity.Role;
import com.f1soft.bachaat.entity.User;
import com.f1soft.bachaat.exception.DataNotFoundException;
import com.f1soft.bachaat.repository.UserRepository;
import com.f1soft.bachaat.service.impl.UserServiceImpl;
import com.f1soft.bachaat.repository.RoleRepository;
import com.f1soft.bachaat.utils.ActivationCodeUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.BDDMockito.when;

import static org.mockito.Matchers.anyInt;

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
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        user = new User(1l, "admin", "admin",
                "admin@admin.com", "admin",
                "9813131", "ram");
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

    public void Should_DeleteUserOfThatId(){
        doNothing().when(userRepository).deleteById(user.getId());
        Assert.assertTrue(userService.deleteUser(user.getId()));
    }

    @Test(expected = DataNotFoundException.class)
    public void Should_ThrowException_WhenInvalidArgumentIsPassed() {
        doThrow(new IllegalArgumentException()).when(userRepository).deleteById(user.getId());
        userService.deleteUser(user.getId());
    }

    @Test
    public void Should_ReturnListOfUser(){
        when(userRepository.findAll()).thenReturn(Arrays.asList(user));
        Assert.assertNotNull(userService.getUsers());
    }

    @Test(expected = DataNotFoundException.class)
    public void Should_ThrowException_When_NoRecordsAreFound() {
        when(userService.getUsers()).thenReturn(Arrays.asList((User[]) null));
        userService.getUsers();
    }
}
