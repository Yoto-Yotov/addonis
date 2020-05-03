package com.addonis.demo.services;

import com.addonis.demo.exceptions.DuplicateEntityException;
import com.addonis.demo.exceptions.EntityNotFoundException;
import com.addonis.demo.exceptions.NotAuthorizedException;
import com.addonis.demo.models.Addon;
import com.addonis.demo.models.Authorities;
import com.addonis.demo.models.User;
import com.addonis.demo.models.UserInfo;
import com.addonis.demo.repository.contracts.AuthorityRepository;
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

    @Mock
    AuthorityRepository authorityRepository;

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
        User originalUser = User.builder().id(1).username("teo").password("123456").build();
        Mockito.when(userRepository.findById(1)).thenReturn(java.util.Optional.ofNullable(originalUser));

        //Act
        User returnUser = userService.getById(1);

        //Assert
        Assert.assertSame(originalUser, returnUser);
    }

    @Test
    public void deleteUser_Should_ReturnTrue_WhenUserExist() {
        //Arrange
        User originalUser = User.builder().id(1).username("teo").password("123456").build();
        Mockito.when(userRepository.findById(1)).thenReturn(java.util.Optional.ofNullable(originalUser));


        //Act
        userService.deleteById(originalUser.getId());

        Mockito.verify(userRepository,
                times(1)).deleteById(originalUser.getId());
    }

    @Test
    public void updateUser_Should_ReturnUpdatedUser_WhenExist() {
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
        Mockito.when(userRepository.existsByUsername(anyString()))
                .thenReturn(true);
        //Act, Assert
        Assertions.assertThrows(DuplicateEntityException.class,
                () -> userService.create(user));

    }

    @Test
    public void getUserByUsername_ShouldThrowException_WhenUserNotExist() {
        //Arrange
        Mockito.when(userRepository.findUserByUsername(anyString())).thenReturn(null);

        //Act //Assert
        Assertions.assertThrows(EntityNotFoundException.class,
                () -> userService.getUserByName(anyString()));
    }

    @Test
    public void getUserByUsername_ShouldReturnUser_WhenUserExist() {
        //Arrange
        User user = User.builder().username("test").build();
        Mockito.when(userRepository.findUserByUsername(anyString())).thenReturn(user);

        //Act
        User result = userService.getUserByName(anyString());

        // Assert
        Assert.assertSame(user, result);
    }

    @Test
    public void getUserAuthorities_should_ThrowException_When_UserNotFound() {
        //Arrange
        Mockito.when(userRepository.existsByUsername(anyString())).thenReturn(false);

        //Act //Assert
        Assertions.assertThrows(EntityNotFoundException.class,
                () -> userService.getUserAuthorities(anyString()));
    }

    @Test
    public void getUserAuthorities_should_returnAuthorities_When_UserExists() {
        //Arrange
        Mockito.when(userRepository.existsByUsername(anyString())).thenReturn(true);
        Mockito.when(authorityRepository.getByUsername(anyString())).thenReturn(new ArrayList<Authorities>());

        //Act
        userService.getUserAuthorities(anyString());

        // Assert
        Mockito.verify(authorityRepository,
                times(1)).getByUsername(anyString());
    }

    @Test
    public void softDeleteUser_Should_deleteUser_WhenInputIsValid() {
        //Arrange
        User user = User.builder().id(1).username("john").build();
        Mockito.when(userRepository.getByName("john")).thenReturn(user);

        //Act
        userService.softDeleteUser("john");
        //Assert
        Mockito.verify(userRepository,
                times(1)).save(user);
    }

    @Test
    public void isAdmin_Should_returnTrue_When_UserIsAdmin() {
        //Arrange
        Authorities authority = Authorities.builder().authority("ROLE_ADMIN").build();
        List<Authorities> authorities = new ArrayList<>();
        authorities.add(authority);
        User user = User.builder().id(1).username("john").build();
        Mockito.when(userRepository.existsByUsername("john")).thenReturn(true);
        Mockito.when(authorityRepository.getByUsername("john")).thenReturn(authorities);

        //Act
        //Assert
       Assert.assertTrue(userService.isAdmin("john"));
    }

    @Test
    public void checkRights_Should_THrowException_WhenUserIsNotAdminOrAddonCreator() {
        //Arrange
        UserInfo user = UserInfo.builder().name("james").build();
        Addon addon = Addon.builder().userInfo(user).build();
        Mockito.when(userRepository.existsByUsername("john")).thenReturn(true);

        //Act
        //Assert
        Assert.assertThrows(NotAuthorizedException.class,
                () -> userService.checkRights("john", addon));

    }
}
