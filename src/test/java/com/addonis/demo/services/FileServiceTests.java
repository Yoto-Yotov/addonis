package com.addonis.demo.services;

import com.addonis.demo.exceptions.InvalidDataException;
import com.addonis.demo.models.Addon;
import com.addonis.demo.services.contracts.AddonService;
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
public class FileServiceTests {

    @Mock
    AddonService addonService;

    @Mock
    MultipartFile file1;

    @InjectMocks
    FileServiceImpl fileService;

    @Test
    public void saveAddonFile_Should_ThrowException_When_FileThrowsIOException() throws IOException {
        //Arrange
        Addon addon = Addon.builder().id(1).name("test").build();
        Mockito.when(addonService.getById(1)).thenReturn(addon);

        //Act
        doThrow(IOException.class).when(file1).getBytes();

        //Assert
        Assert.assertThrows(InvalidDataException.class, () -> fileService.saveAddonFile(1, file1));
    }

    @Test
    public void saveAddonFile_Should_SaveFile_When_InputIsValid() {
        //Arrange
        Addon addon = Addon.builder().id(1).name("test").build();
        MultipartFile file = new MockMultipartFile("myfile", new byte[12]);
        Mockito.when(addonService.getById(1)).thenReturn(addon);

        //Act
        fileService.saveAddonFile(1, file);

        //Assert
        Mockito.verify(addonService, times(1)).update(addon);
    }
}
