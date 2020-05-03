package com.addonis.demo.services;

import com.addonis.demo.exceptions.DuplicateEntityException;
import com.addonis.demo.exceptions.EntityNotFoundException;
import com.addonis.demo.exceptions.InvalidDataException;
import com.addonis.demo.models.UserInfo;
import com.addonis.demo.repository.contracts.UserInfoRepository;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static com.addonis.demo.constants.Constants.USER_U;
import static com.addonis.demo.validation.EmailValidator.isValidEmailAddress;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.Silent.class)
public class UserInfoServiceTests {

    @Mock
    UserInfoRepository userInfoRepository;

    @InjectMocks
    UserInfoServiceImpl userInfoService;

    @Test
    public void getAll_Should_ReturnAllUsers() {
        //Arrange
        UserInfo originalUser = UserInfo.builder().name("Teo").id(1).email("teo@abv.bg").build();
        List<UserInfo> userInfoList = new ArrayList<>();
        userInfoList.add(originalUser);
        Mockito.when(userInfoRepository.findAll()).thenReturn(userInfoList);

        //Act
        List<UserInfo> userListToReturn = userInfoService.getAll();

        //Assert
        Assert.assertSame(userInfoList, userListToReturn);
    }

    @Test
    public void getUserById_Should_ReturnUser_WhenExist() {
        //Arrange
        UserInfo originalUser = UserInfo.builder().name("Teo").id(1).email("teo@abv.bg").build();
        Mockito.when(userInfoRepository.getOne(1)).thenReturn(originalUser);

        //Act
        Mockito.when(userInfoRepository.existsById(1)).thenReturn(true);
        UserInfo returnUser = userInfoService.getById(1);

        //Assert
        Assert.assertSame(originalUser, returnUser);
    }

    @Test(expected = EntityNotFoundException.class)
    public void getUserById_Should_ThrowException_WhenNotExist() {
        //Arrange
        UserInfo originalUser = UserInfo.builder().name("Teo").id(1).email("teo@abv.bg").build();

        //Act
        doThrow(EntityNotFoundException.class).when(userInfoRepository).getOne(15);

        userInfoService.getById(15);
    }

    @Test(expected = EntityNotFoundException.class)
    public void deletUser_Should_ThrowException_WhenUserNotExist() {
        UserInfo originalUser = UserInfo.builder().name("Teo").id(1).email("teo@abv.bg").build();
        UserInfo userToDel = UserInfo.builder().name("Teo").id(2).email("teo@abv.bg").build();

        doThrow(new EntityNotFoundException("User", 2))
                .when(userInfoRepository).deleteById(userToDel.getId());

        userInfoService.deleteById(userToDel.getId());
    }

    @Test
    public void deleteUser_Should_ReturnTrue_WhenUserExist() {
        //Arrange
        UserInfo user = UserInfo.builder().name("Teo").id(1).email("teo@abv.bg").build();

        Mockito.when(userInfoRepository.existsById(1)).thenReturn(true);

        //Act
        userInfoService.deleteById(user.getId());

        Mockito.verify(userInfoRepository,
                times(1)).deleteById(user.getId());
    }

    @Test(expected = EntityNotFoundException.class)
    public void updateUser_Should_ThrowException_WhenUserIsNotExist() {
        //Arrange
        UserInfo user = UserInfo.builder().name("Teo").id(1).email("teo@abv.bg").build();
        UserInfo updateUser = UserInfo.builder().name("Teo").id(5).email("teo1@abv.bg").build();

        doThrow(new EntityNotFoundException("User", updateUser.getEmail()))
                .when(userInfoRepository).save(updateUser);

        userInfoService.update(updateUser);

    }

    @Test
    public void updateUser_Should_ReturnUpdatedUser_WhenExist() {
        //Arrange
        UserInfo user = UserInfo.builder().name("Teo").id(1).email("teo@abv.bg").build();
        UserInfo updateUser = UserInfo.builder().name("Teo").id(5).email("teo@abv.bg").build();

        Mockito.when(userInfoRepository.existsByEmail("teo@abv.bg")).thenReturn(true);

        userInfoService.update(updateUser);

        Mockito.verify(userInfoRepository,
                times(1)).save(updateUser);
    }

    @Test
    public void createUser_ShouldCreateUser_WhenUser_NotExist() {
        //Arrange
        UserInfo user = UserInfo.builder().name("Teo").id(1).email("teo@abv.bg").build();

        Mockito.when(userInfoRepository.save(user))
                .thenReturn(user);

        //Act
        UserInfo userToReturn = userInfoService.create(user);
        //Assert
        Assertions.assertEquals(userToReturn.getId(), user.getId());
        Assertions.assertEquals(userToReturn.getName(), user.getName());
        Assertions.assertEquals(userToReturn.getEmail(), user.getEmail());
    }

    @Test
    public void createShould_ThrowException_WhenUserAlreadyExist() {
        UserInfo user = UserInfo.builder().name("Teo").id(1).email("teo@abv.bg").build();

        //Arrange
        Mockito.when(userInfoRepository.existsByEmail(anyString()))
                .thenReturn(true);
        //Act, Assert
        Assertions.assertThrows(DuplicateEntityException.class,
                () -> userInfoService.create(user));

    }

    @Test
    public void createShould_ThrowException_WhensUserExistByName() {
        UserInfo user = UserInfo.builder().name("Teo").id(1).email("teo@abv.bg").build();

        //Arrange
        Mockito.when(userInfoRepository.existsByName(anyString()))
                .thenReturn(true);
        //Act, Assert
        Assertions.assertThrows(DuplicateEntityException.class,
                () -> userInfoService.create(user));

    }

    @Test
    public void getUserByUsername_ShouldGetUser_WhenUserExist() {
        //Arrange
        UserInfo originalUser = UserInfo.builder().name("Teo").id(1).email("teo@abv.bg").build();

        Mockito.when(userInfoRepository.existsByName("Teo")).thenReturn(true);
        Mockito.when(userInfoRepository.getByUserName(originalUser.getName())).thenReturn(originalUser);

        //Act
        UserInfo returnUser = userInfoService.getUserByUsername(originalUser.getName());

        //Assert
        Assert.assertSame(originalUser, returnUser);
    }

    @Test(expected = EntityNotFoundException.class)
    public void getUserByUsernameShould_ThrowException_WhenUserNotExist() {
        //Arrange
        UserInfo user = UserInfo.builder().name("Teo").id(1).email("teo@abv.bg").build();
        UserInfo updateUser = UserInfo.builder().name("Teo1").id(5).email("teo1@abv.bg").build();

        doThrow(new EntityNotFoundException("User", updateUser.getName()))
                .when(userInfoRepository).save(updateUser);

        userInfoService.getUserByUsername("Teo1");

    }

    @Ignore
    @Test
    public void softDelete_ShouldDisableUser_WhenExist() {
        UserInfo user = UserInfo.builder().name("Teo").id(1).email("teo@abv.bg").build();
        user.setEnabled(0);

        Mockito.when(userInfoRepository.getByUserName("Teo"))
                .thenReturn(user);

        userInfoService.softDeleteUserInfo(user.getName());

        Mockito.verify(userInfoRepository,
                times(1)).softDeleteUserInfo(user.getName());
    }

    @Test
    public void checkIfUserExist_ByName() {
        //Arrange
        UserInfo user = UserInfo.builder().name("Teo").id(1).email("teo@abv.bg").build();

        Mockito.when(userInfoRepository.existsByName("Teo"))
                .thenReturn(true);

        Assert.assertTrue(userInfoService.checkUserExistByName(user.getName()));
    }

    @Test
    public void checkIfUserExist_ByEmail() {
        //Arrange
        UserInfo user = UserInfo.builder().name("Teo").id(1).email("teo@abv.bg").build();

        Mockito.when(userInfoRepository.existsByEmail("teo@abv.bg"))
                .thenReturn(true);

        Assert.assertTrue(userInfoService.checkUserExistByEmail(user.getEmail()));
    }
}
