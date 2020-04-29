package com.addonis.demo.services;

import com.addonis.demo.exceptions.EntityNotFoundException;
import com.addonis.demo.models.Addon;
import com.addonis.demo.models.Rating;
import com.addonis.demo.models.UserInfo;
import com.addonis.demo.repository.contracts.RatingRepository;
import com.addonis.demo.services.contracts.AddonService;
import com.addonis.demo.services.contracts.UserInfoService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class RatingServiceTests {

    @Mock
    RatingRepository ratingRepository;

    @Mock
    AddonService addonService;

    @Mock
    UserInfoService userInfoService;

    @InjectMocks
    RatingServiceImpl ratingServiceImpl;

    @Test
    public void  rateAddon_Should_ThrowException_When_UserNotExist(){
    //Arrange
        Mockito.when(userInfoService.getUserByUsername("test")).thenThrow(EntityNotFoundException.class);

    //Act //Assert
        Assert.assertThrows(EntityNotFoundException.class, () -> ratingServiceImpl.rateAddon(1, "test", 4));
    }

    @Test
    public void  rateAddon_Should_ThrowException_When_AddonNotExist(){
        //Arrange
        UserInfo john = UserInfo.builder().id(1).name("john").build();
        Mockito.when(userInfoService.getUserByUsername("john")).thenReturn(john);
        Mockito.when(addonService.getById(1)).thenThrow(EntityNotFoundException.class);

        //Act //Assert
        Assert.assertThrows(EntityNotFoundException.class, () -> ratingServiceImpl.rateAddon(1, "john", 4));
    }

    @Test
    public void  rateAddon_Should_saveRating_When_InputIsValidAndRatingExists(){
        //Arrange
        UserInfo john = UserInfo.builder().id(1).name("john").build();
        Addon addon = Addon.builder().id(1).name("zoom").build();

        Mockito.when(userInfoService.getUserByUsername("john")).thenReturn(john);
        Mockito.when(addonService.getById(1)).thenReturn(addon);
        Mockito.when(ratingRepository.getByUserInfoIdAndAddonId(1, 1)).thenReturn(null);
        Rating rating = Rating.builder().rating(4).userInfo(john).addon(addon).build();

        //Act
        ratingServiceImpl.rateAddon(1, "john", 4);

        //Assert
        Mockito.verify(ratingRepository, times(1)).save(rating);
    }

    @Test
    public void  rateAddon_Should_saveRating_When_InputIsValidAndRatingNotExists(){
        //Arrange
        UserInfo john = UserInfo.builder().id(1).name("john").build();
        Addon addon = Addon.builder().id(1).name("zoom").build();
        Rating rating = Rating.builder().rating(4).userInfo(john).addon(addon).build();

        Mockito.when(userInfoService.getUserByUsername("john")).thenReturn(john);
        Mockito.when(addonService.getById(1)).thenReturn(addon);
        Mockito.when(ratingRepository.getByUserInfoIdAndAddonId(1, 1)).thenReturn(rating);

        //Act
        ratingServiceImpl.rateAddon(1, "john", 4);

        //Assert
        Mockito.verify(ratingRepository, times(1)).save(rating);
    }

    @Test
    public void getAddonRating_ShouldReturn_AddonRating_WhenAddonExist(){
    //Arrange
    Mockito.when(ratingRepository.getAverageRating(1)).thenReturn(4.0);

    //Act //Assert
        Assert.assertEquals(4.0, ratingServiceImpl.getAddonRating(1),1);
    }

    @Test
    public void getUserRating_Should_ThrowException_When_UserNotExist(){
    //Arrange
        Mockito.when(userInfoService.getUserByUsername("john")).thenThrow(EntityNotFoundException.class);

    //Act //Assert
        Assert.assertThrows(EntityNotFoundException.class, () -> ratingServiceImpl.getUserRating(1, "john"));
    }

    @Test
    public void getUserRating_Should_ThrowException_When_AddonNotExist(){
        //Arrange
        UserInfo john = UserInfo.builder().id(1).name("john").build();
        Mockito.when(userInfoService.getUserByUsername("john")).thenReturn(john);
        Mockito.when(addonService.getById(1)).thenThrow(EntityNotFoundException.class);

        //Act //Assert
        Assert.assertThrows(EntityNotFoundException.class, () -> ratingServiceImpl.getUserRating(1, "john"));
    }

    @Test
    public void getUserRating_Should_ReturnZero_When_AddonNotRated(){
        //Arrange
        UserInfo john = UserInfo.builder().id(1).name("john").build();
        Addon addon = Addon.builder().id(1).name("addon").build();
        Mockito.when(userInfoService.getUserByUsername("john")).thenReturn(john);
        Mockito.when(addonService.getById(1)).thenReturn(addon);

        //Act //Assert
        Assert.assertEquals(0, ratingServiceImpl.getUserRating(1, "john"), 1);
    }

    @Test
    public void getUserRating_Should_ReturnAvarageRating_When_AddonIsRated(){
        //Arrange
        UserInfo john = UserInfo.builder().id(1).name("john").build();
        Addon addon = Addon.builder().id(1).name("addon").build();
        Rating rating = Rating.builder().rating(4).userInfo(john).addon(addon).build();

        Mockito.when(userInfoService.getUserByUsername("john")).thenReturn(john);
        Mockito.when(addonService.getById(1)).thenReturn(addon);
        Mockito.when(ratingRepository.getByUserInfoIdAndAddonId(1, 1)).thenReturn(rating);

        //Act //Assert
        Assert.assertEquals(4, ratingServiceImpl.getUserRating(1, "john"), 1);
    }
}
