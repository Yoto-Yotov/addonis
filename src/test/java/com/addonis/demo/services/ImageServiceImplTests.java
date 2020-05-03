package com.addonis.demo.services;

import com.addonis.demo.models.Addon;
import com.addonis.demo.models.UserInfo;
import com.addonis.demo.repository.contracts.AddonRepository;
import com.addonis.demo.repository.contracts.UserInfoRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.Silent.class)
public class ImageServiceImplTests {

    @Mock
    UserInfoRepository userInfoRepository;

    @Mock
    MultipartFile file1;

    @Mock
    AddonRepository addonRepository;

    @InjectMocks
    ImageServiceImpl imageService;

    @Test
    public void setUserImageFile_Should_SetUserImage_When_InputValid() {
        //Arrange
        UserInfo userInfo = UserInfo.builder().name("john").build();
        MultipartFile file = new MockMultipartFile("myfile", new byte[12]);
        Mockito.when(userInfoRepository.getOne(1)).thenReturn(userInfo);

        //Act
        imageService.saveImageFile(1, file);

        //Assert
        Mockito.verify(userInfoRepository, times(1)).save(userInfo);
    }

    @Test
    public void setUserImageFile_Should_ThrowException_When_InputCausesIOException() throws IOException {
        //Arrange
        UserInfo userInfo = UserInfo.builder().name("john").build();
        Mockito.when(userInfoRepository.getOne(1)).thenReturn(userInfo);

        //Act
        doThrow(IOException.class).when(file1).getBytes();

        //Assert
        Assert.assertThrows(IllegalStateException.class, () -> imageService.saveImageFile(1, file1));
    }

    @Test
    public void setAddonImageFile_Should_ThrowException_When_InputCausesIOException() throws IOException {
        //Arrange
        Addon addon = Addon.builder().id(1).name("test").build();
        MultipartFile file = new MockMultipartFile("myfile", new byte[12]);
        Mockito.when(addonRepository.getOne(1)).thenReturn(addon);

        //Act
        doThrow(IOException.class).when(file1).getBytes();

        //Assert
        Assert.assertThrows(IllegalStateException.class, () -> imageService.saveImageFileToAddon(1, file1));
    }

    @Test
    public void setAddonImageFile_Should_SetUserImage_When_InputValid() {
        //Arrange
        Addon addon = Addon.builder().id(1).name("test").build();
        MultipartFile file = new MockMultipartFile("myfile", new byte[12]);
        Mockito.when(addonRepository.getOne(1)).thenReturn(addon);

        //Act
        imageService.saveImageFileToAddon(1, file);

        //Assert
        Mockito.verify(addonRepository, times(1)).save(addon);
    }
}
