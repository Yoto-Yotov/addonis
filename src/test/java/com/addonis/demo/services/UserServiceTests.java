package com.addonis.demo.services;

import com.addonis.demo.exceptions.DuplicateEntityException;
import com.addonis.demo.models.User;
import com.addonis.demo.models.UserInfo;
import com.addonis.demo.repository.contracts.UserInfoRepository;
import com.addonis.demo.repository.contracts.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.Silent.class)
public class UserServiceTests {

    @Mock
    UserRepository userRepository;

    @InjectMocks
    UserServiceImpl userService;

    @Test
    public void getAll_Should_ReturnAllUsers() {
        //Arrange
        User originalUser = User.builder().username("teo").password("123456").build();
        List<User> userList = new ArrayList<>();
        userList.add(originalUser);
        Mockito.when(userRepository.findAll()).thenReturn(userList);

        //Act
        List<User> userListToReturn = userService.getAll();

        //Assert
        Assert.assertSame(userList, userListToReturn);
    }

    @Test
    public void getUserById_Should_ReturnUser_WhenExist() {
        //Arrange
        User originalUser = User.builder().username("teo").password("123456").build();
        Mockito.when(userRepository.getOne("1")).thenReturn(originalUser);

        //Act
        User returnUser = userService.getById("1");

        //Assert
        Assert.assertSame(originalUser, returnUser);
    }

    @Test
    public void deleteUser_Should_ReturnTrue_WhenUserExist() {
        //Arrange
        User originalUser = User.builder().username("teo").password("123456").build();

        //Act
        userService.deleteById(originalUser.getUsername());

        Mockito.verify(userRepository,
                times(1)).deleteById(originalUser.getUsername());
    }

    @Test
    public void updateBeer_Should_ReturnUpdatedBeer_WhenExist() {
        //Arrange
        User originalUser = User.builder().username("teo").password("123456").build();
        User updateUser = User.builder().username("teo").password("123456").build();

        userService.update(updateUser);

        Mockito.verify(userRepository,
                times(1)).save(updateUser);
    }

    @Test
    public void createUser_ShouldCreateUser_WhenUser_NotExist() {
        //Arrange
        User user = User.builder().username("teo").password("123456").build();

        Mockito.when(userRepository.save(user))
                .thenReturn(user);

        //Act
        User userToReturn = userService.create(user);
        //Assert
        Assertions.assertEquals(userToReturn.getUsername(), user.getUsername());
        Assertions.assertEquals(userToReturn.getPassword(), user.getPassword());
    }

    @Test
    public void createShould_ThrowException_WhenBeerAlreadyExist() {
        User user = User.builder().username("teo").password("123456").build();

        //Arrange
        Mockito.when(userRepository.existsByName(anyString()))
                .thenReturn(true);
        //Act, Assert
        Assertions.assertThrows(DuplicateEntityException.class,
                () -> userService.create(user));

    }
}
