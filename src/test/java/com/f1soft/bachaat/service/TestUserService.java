package com.f1soft.bachaat.service;

import com.f1soft.bachaat.entity.User;
import com.f1soft.bachaat.exception.DataNotFoundException;
import com.f1soft.bachaat.repository.UserRepository;
import com.f1soft.bachaat.service.impl.UserServiceImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Optional;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.BDDMockito.when;


import static org.mockito.Mockito.when;

public class TestUserService {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    UserServiceImpl userService;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    User user;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        user = new User(1, "admin", "admin",
                "admin@admin.com", "admin",
                "9813131", "ram");
    }

    @Test
    public void Should_DeleteUserOfThatId() {
        doNothing().when(userRepository).deleteById(user.getId());
        Assert.assertTrue(userService.deleteUser(user.getId()));
    }

    @Test(expected = DataNotFoundException.class)
    public void Should_ThrowException_WhenInvalidArgumentIsPassed() {
        doThrow(new IllegalArgumentException()).when(userRepository).deleteById(user.getId());
        userService.deleteUser(user.getId());
    }

    @Test
    public void Should_ReturnListOfUser() {
        when(userRepository.findAll()).thenReturn(Arrays.asList(user));
        Assert.assertNotNull(userService.getUsers());
    }

    @Test(expected = DataNotFoundException.class)
    public void Should_ThrowException_When_NoRecordsAreFound() {
        when(userService.getUsers()).thenReturn(Arrays.asList((User[]) null));
        userService.getUsers();
    }

    @Test
    public void Should_ReturnUpdatedUser() {
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);
        Assert.assertEquals(userService.updateUser(user), user);
    }

    @Test
    public void Should_ThrowException_WhenThereIsNoSuchId() {
        expectedException.expect(DataNotFoundException.class);
        userService.updateUser(user);
    }

}
