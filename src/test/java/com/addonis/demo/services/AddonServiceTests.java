package com.addonis.demo.services;

import com.addonis.demo.exceptions.DuplicateEntityException;
import com.addonis.demo.exceptions.EntityNotFoundException;
import com.addonis.demo.exceptions.NotAuthorizedException;
import com.addonis.demo.models.Addon;
import com.addonis.demo.models.Authorities;
import com.addonis.demo.models.User;
import com.addonis.demo.models.UserInfo;
import com.addonis.demo.models.enums.Sortby;
import com.addonis.demo.models.enums.Status;
import com.addonis.demo.repository.contracts.AddonRepository;
import com.addonis.demo.repository.contracts.UserRepository;
import com.addonis.demo.services.contracts.UserService;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.Silent.class)
public class AddonServiceTests {

    @Mock
    AddonRepository addonRepository;

    @Mock
    UserService userService;

    @Mock
    UserRepository userRepository;

    @InjectMocks
    AddonServiceImpl addonService;

    @Test
    public void getAll_Should_CallRepository(){
        //Arrange
        Mockito.when(addonRepository.findAll()).thenReturn(new ArrayList<>());

        //Act
        addonService.getAll();

        Mockito.verify(addonRepository,
                times(1)).findAll();
    }

    @Test
    public void getById_Should_ThrowException_WhenAddonDoesNotExist(){
    //Arrange
        Mockito.when(addonRepository.findById(1)).thenReturn(java.util.Optional.of(new Addon()));

    //Act
    //Assert
        Assert.assertThrows(EntityNotFoundException.class, () -> addonService.getById(2));
    }

    @Test
    public void getAddonByName_Should_ThrowException_When_addonNotExists() {
        //Arrange
        Mockito.when(addonRepository.getByName("test")).thenReturn(null);

        //Act
        //Assert
        Assert.assertThrows(EntityNotFoundException.class, () -> addonService.getAddonByName("test"));
    }

    @Test
    public void getAddonByName_Should_ReturnAddon_When_addonExists() {
        //Arrange
        Addon addon = Addon.builder().name("test").build();
        Mockito.when(addonRepository.getByName("test")).thenReturn(addon);

        //Act
        Addon addonResult = addonService.getAddonByName("test");

        //Assert
        Assert.assertSame(addon, addonResult);
    }

    @Test
    public void getAllPendingAddons_Should_CallRepository() {
        //Arrange
        Mockito.when(addonRepository.getAddonByStatus(Status.PENDING)).thenReturn(new ArrayList<>());

        //Act
        addonService.getAllPendingAddons();

        Mockito.verify(addonRepository,
                times(1)).getAddonByStatus(Status.PENDING);
    }

    @Test
    public void getAllApprovedAddons_Should_CallRepository() {
        //Arrange
        Mockito.when(addonRepository.getAddonByStatus(Status.APPROVED)).thenReturn(new ArrayList<>());

        //Act
        addonService.getAllApprovedAddons();

        Mockito.verify(addonRepository,
                times(1)).getAddonByStatus(Status.APPROVED);
    }

    @Test
    public void getCreatorName_Should_returnCreatorName_WhenAddonExcists() {
        //Arrange
        UserInfo userInfo = UserInfo.builder().name("john").build();
        Addon addon = Addon.builder().id(1).userInfo(userInfo).build();

        Mockito.when(addonRepository.findById(1)).thenReturn(java.util.Optional.ofNullable(addon));

        //Act //Assert
        Assert.assertEquals("john", addonService.getCreatorName(1));
    }

    @Test
    public void changeDownloadCount_shouldUpdateDOwnloadCount_WHenAddonExists(){
    //Arrange
        Addon addon = Addon.builder().id(1).downloadsCount(4).build();
        Mockito.when(addonRepository.findById(1)).thenReturn(java.util.Optional.ofNullable(addon));

    //Act
        addonService.changeDownloadCount(1);

    //Assert
        Mockito.verify(addonRepository,
                times(1)).save(addon);

    }

    @Test
    public void softDeleteAddon_should_ThrowException_When_AddonNotExist(){
    //Arrange
        UserInfo user = UserInfo.builder().name("john").build();
        Mockito.when(addonRepository.existsByName("test")).thenReturn(false);

    //Act //Assert
        Assert.assertThrows(EntityNotFoundException.class, () -> addonService.softDeleteAddon("test", user));
    }

    @Test
    public void softDeleteAddon_Should_throwException_WhenUserNotAuthorized() {
        //Arrange
        UserInfo userInfo = UserInfo.builder().name("john1").build();
        User user = User.builder().username("john").build();
        Addon addon = Addon.builder().userInfo(userInfo).name("test").build();


        Mockito.when(addonRepository.existsByName("test")).thenReturn(true);
        Mockito.when(addonRepository.getByName("test")).thenReturn(addon);
        Mockito.when(userService.isAdmin("john1")).thenReturn(false);
        Mockito.when(userService.getUserByName("john1")).thenReturn(user);
        Mockito.when(userRepository.findUserByUsername("john")).thenReturn(user);

        //Act //Assert
        Assert.assertThrows(NotAuthorizedException.class, () -> addonService.softDeleteAddon("test", userInfo));
    }

    @Test
    public void softDeleteAddon_Should_callRepository_WhenUserIsAuthorized() {
        //Arrange
        UserInfo userInfo = UserInfo.builder().enabled(1).name("john").build();
        User user = User.builder().enabled(1).username("john").build();
        Addon addon = Addon.builder().enabled(1).userInfo(userInfo).name("test").build();


        Mockito.when(addonRepository.existsByName("test")).thenReturn(true);
        Mockito.when(addonRepository.getByName("test")).thenReturn(addon);
        Mockito.when(userService.isAdmin("john")).thenReturn(true);
        Mockito.when(userService.getUserByName("john")).thenReturn(user);
        Mockito.when(userRepository.findUserByUsername("john")).thenReturn(user);

        //Act
         addonService.softDeleteAddon("test", userInfo);

        //Assert
        Mockito.verify(addonRepository, times(1)).softDeleteAddonInfo("test");
    }

    @Test
    public void getNewest_should_CallRepository_When_Called() {
        //Arrange
        Mockito.when(addonRepository.findTop6ByStatusOrderByIdDesc(Status.APPROVED)).thenReturn(new ArrayList<>());

        //Act
        addonService.getNewest();

        //Assert
        Mockito.verify(addonRepository,
                times(1)).findTop6ByStatusOrderByIdDesc(Status.APPROVED);
    }

    @Test
    public void getTopByDownloads_should_CallRepository_When_Called() {
        //Arrange
        Mockito.when(addonRepository.findTop6ByStatusOrderByDownloadsCountDesc(Status.APPROVED)).thenReturn(new ArrayList<>());

        //Act
        addonService.getTopByDownloads();

        //Assert
        Mockito.verify(addonRepository,
                times(1)).findTop6ByStatusOrderByDownloadsCountDesc(Status.APPROVED);
    }

    @Test
    public void getRandomAddons_should_CallRepository_When_Called() {
        //Arrange
        Mockito.when(addonRepository.get6Random()).thenReturn(new ArrayList<>());

        //Act
        addonService.get6Random();

        //Assert
        Mockito.verify(addonRepository,
                times(1)).get6Random();
    }

    @Test
    public void deleteById_Should_ThrowException_When_AddonNotExist() {
    //Arrange
        Mockito.when(addonRepository.existsById(1)).thenReturn(false);

    //Act //Assert
        Assert.assertThrows(EntityNotFoundException.class, () -> addonService.deleteById(1));
    }

    @Test
    public void deleteById_Should_CallRepository_When_AddonNExist() {
        //Arrange
        Mockito.when(addonRepository.existsById(1)).thenReturn(true);

        //Act
        addonService.deleteById(1);

        //Assert
        Mockito.verify(addonRepository, times(1)).deleteById(1);
    }

    @Test
    public void findByNameContaining_Should_CallRepository_When_Called() {
    //Arrange
        Mockito.when(addonRepository.findAllByStatusAndNameContaining(Status.APPROVED, "tes")).thenReturn(new ArrayList<>());

    //Act
        addonService.findByNameContaining("tes");

    //Assert
        Mockito.verify(addonRepository, times(1)).findAllByStatusAndNameContaining(Status.APPROVED, "tes");
    }

    @Test
    public void getAllFilterByIdeName_Should_CallRepository_When_Called() {
        //Arrange
        Mockito.when(addonRepository.findAllByStatusAndIdeId_IdeName(Status.APPROVED, "ide")).thenReturn(new ArrayList<>());

        //Act
        addonService.getAllFilterByIdeName("ide");

        //Assert
        Mockito.verify(addonRepository, times(1)).findAllByStatusAndIdeId_IdeName(Status.APPROVED, "ide");
    }

    @Test
    public void getAllFilterByTagName_Should_CallRepository_When_Called() {
    //Arrange
        Mockito.when(addonRepository.getAllByTagName(Status.APPROVED, "tag")).thenReturn(new ArrayList<>());
    //Act
        addonService.getAllFilterByTagName("tag");

    //Assert
        Mockito.verify(addonRepository, times(1)).getAllByTagName(Status.APPROVED, "tag");
    }

    @Test
    public void update_Should_Throw_Exception_When_AddonIsDuplicate() {
    //Arrange
        Addon addon = Addon.builder().name("test1").build();
        Mockito.when(addonRepository.save(addon)).thenThrow(org.springframework.dao.DataIntegrityViolationException.class);

    //Act   //Assert
        Assert.assertThrows(DuplicateEntityException.class, () -> addonService.update(addon));
    }

    @Test
    public void checkAddonExistById_Should_ReturnFalse_When_AddonNotExist() {
    //Arrange
        Mockito.when(addonRepository.existsById(1)).thenReturn(false);

    //Act
    //Assert
        Assert.assertFalse(addonService.checkAddonExistsById(1));
    }

    @Test
    public void enableAddon_Should_EnableAddon_When_InputIsValid() {
    //Arrange
        Addon addon = Addon.builder().id(1).name("test").build();
        Mockito.when(addonRepository.getByName("test")).thenReturn(addon);

    //Act
        addonService.enableAddon("test");

    //Assert
        Mockito.verify(addonRepository, times(1)).save(addon);
    }

    @Test
    public void getMyAddon_Should_returnUserAddons_When_InputIsValid() {
    //Arrange
        UserInfo userInfo = UserInfo.builder().name("john").build();
        Mockito.when(addonRepository.getMyAddons(userInfo)).thenReturn(new ArrayList<>());

    //Act
        addonService.getMyAddons(userInfo);

    //Assert
        Mockito.verify(addonRepository, times(1)).getMyAddons(userInfo);
    }

    @Test
    public void checkExistByName_Should_ReturnFalse_When_AddonNotExist() {
        //Arrange
            Mockito.when(addonRepository.existsByName("test")).thenReturn(false);

        //Act
            addonService.checkAddonExistsByName("test");

        //Assert
            Mockito.verify(addonRepository, times(1)).existsByName("test");
    }

    @Test
    public void getAllSortBy_Should_CallRepository_When_Called() {
    //Arrange
        Mockito.when(addonRepository.findAllByStatus(Status.APPROVED, Sort.by(Sort.Direction.valueOf("ASC"), "name"))).thenReturn(new ArrayList<>());

    //Act
        addonService.getAllSortBy("ASC", Sortby.getByParam("name"));

    //Assert
        Mockito.verify(addonRepository, times(1)).findAllByStatus(Status.APPROVED, Sort.by(Sort.Direction.valueOf("ASC"), "name"));
    }

    @Test
    public void create_Should_ThrowException_When_AddonWIthSameNameExists() {
    //Arrange
        Addon addon = Addon.builder().name("test").build();
        Mockito.when(addonRepository.existsByName("test")).thenReturn(true);

    //Act
    //Assert
        Assert.assertThrows(DuplicateEntityException.class, () -> addonService.create(addon));
    }
}
