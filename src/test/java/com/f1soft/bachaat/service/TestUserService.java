package com.f1soft.bachaat.service;

import com.f1soft.bachaat.entity.Role;
import com.f1soft.bachaat.entity.User;
import com.f1soft.bachaat.exception.DataNotFoundException;
import com.f1soft.bachaat.exception.UserAlreadyExistsException;
import com.f1soft.bachaat.repository.RoleRepository;
import com.f1soft.bachaat.repository.UserRepository;
import com.f1soft.bachaat.service.impl.UserServiceImpl;
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
import java.util.Optional;
import java.util.logging.Logger;

import static org.mockito.BDDMockito.when;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;

public class TestUserService {

    private static Logger logger = Logger.getLogger(TestUserService.class.getName());

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
        logger.info("Inside Test User Add Service To Add User Successfully");
        when(activationCodeUtil.getActivationCode()).thenReturn(anyInt());
        when(userRepository.findByMobileNumber(user.getMobileNumber())).thenReturn(null);
        when(roleRepository.findByName("USER")).thenReturn(new Role());
        when(userRepository.save(user)).thenReturn(user);
        when(userService.addUser(user)).thenReturn(user);
        Assert.assertNotNull(userService.addUser(user));
    }

    @Test
    public void Should_DeleteUserOfThatId() {
        logger.info("Inside Test User Delete to delete user successfully");
        doNothing().when(userRepository).deleteById(user.getId());
        userService.deleteUser(user.getId());
    }

    @Test(expected = DataNotFoundException.class)
    public void Should_ThrowException_WhenInvalidArgumentIsPassed() {
        logger.info("Inside Test User Delete when invalid argument is passed");
        doThrow(new IllegalArgumentException()).when(userRepository).deleteById(user.getId());
        userService.deleteUser(user.getId());
    }

    @Test
    public void Should_ReturnListOfUser() {
        logger.info("Inside Test User Get All to fetch all Users");
        when(userRepository.findAll()).thenReturn(Arrays.asList(user));
        Assert.assertNotNull(userService.getUsers());
    }

    @Test(expected = DataNotFoundException.class)
    public void Should_ThrowException_When_NoRecordsAreFound() {
        logger.info("Inside Test User Get All when there is no record");
        when(userService.getUsers()).thenReturn(Arrays.asList((User[]) null));
        userService.getUsers();
    }

    @Test
    public void Should_ReturnUpdatedUser() {
        logger.info("Inside Test User Update to update user successfully");
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);
        Assert.assertEquals(userService.updateUser(user), user);
    }

    @Test(expected = DataNotFoundException.class)
    public void Should_ThrowException_WhenThereIsNoSuchId() {
        logger.info("Inside User Update");
        user.setId(null);
        userService.updateUser(user);
    }

    @Test(expected = DataNotFoundException.class)
    public void Should_ThrowException_WhenThereIsNoSuchIdPresent() {
        logger.info("Inside User Update");
        when(userRepository.findById(user.getId())).thenThrow(DataNotFoundException.class);
        userService.updateUser(user);
    }

    @Test(expected = UserAlreadyExistsException.class)
    public void Should_ThrowException_WhenSameMobileNumberIsPassed() {
        logger.info("Inside Test User Add when same mobile number is passed");
        when(userRepository.findByMobileNumber(user.getMobileNumber())).thenThrow(UserAlreadyExistsException.class);
        userService.addUser(user);
    }


}
