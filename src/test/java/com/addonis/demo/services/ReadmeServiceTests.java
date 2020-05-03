package com.addonis.demo.services;

import com.addonis.demo.models.LastCommit;
import com.addonis.demo.models.Readme;
import com.addonis.demo.repository.contracts.ReadmeRepository;
import com.addonis.demo.repository.contracts.UserInfoRepository;
import com.addonis.demo.services.contracts.ReadmeService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyByte;
import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.Silent.class)
public class ReadmeServiceTests {

    @Mock
    ReadmeRepository readmeRepository;

    @InjectMocks
    ReadmeServiceImpl readmeService;

    @Test
    public void getAll_ShouldReturn_All() {
        //Arrange
        Readme readme = Readme.builder().readmeId(1).build();
        List<Readme> readmeList = new ArrayList<>();
        readmeList.add(readme);

        Mockito.when(readmeRepository.findAll()).thenReturn(readmeList);

        //Act
        List<Readme> readmeListToReturn = readmeService.getAll();

        //Assert
        Assert.assertSame(readmeList, readmeListToReturn);
    }

    @Test
    public void getById_ShouldGet_Readme_When_ReadmeExist() {
        //Arrange
        Readme readme = Readme.builder().readmeId(1).build();
        Mockito.when(readmeRepository.getOne(1)).thenReturn(readme);

        //Act
        Readme returnReadme = readmeService.getById(1);

        //Assert
        Assert.assertSame(readme, returnReadme);
    }

    @Test
    public void deleteById_ShouldDelete() {
        //Arrange
        Readme readme = Readme.builder().readmeId(1).build();

        //Act
        readmeService.deleteById(readme.getReadmeId());

        Mockito.verify(readmeRepository,
                times(1)).deleteById(readme.getReadmeId());
    }

    @Test
    public void updateReadme_Should_ReturnUpdatedReadme_WhenExist() {
        //Arrange
        Readme readme = Readme.builder().readmeId(1).build();
        Readme readmeToUpdate = Readme.builder().readmeId(2).build();

        readmeService.update(readmeToUpdate);

        Mockito.verify(readmeRepository,
                times(1)).save(readmeToUpdate);
    }

    @Test
    public void createReadMe_ShouldCreateFile() {
        //Arrange
        Readme readme = Readme.builder().readmeId(1).build();

        Mockito.when(readmeRepository.save(readme))
                .thenReturn(readme);

        //Act
        Readme readmeToReturn = readmeService.create(readme);
        //Assert
        Assertions.assertEquals(readme.getReadmeId(), readmeToReturn.getReadmeId());
    }

    @Test
    public void getReadmeString_ShouldReturn_Readme() {
        //Arrange
        Readme readme = Readme.builder().readmeId(1).text(new byte[10]).build();

        Mockito.when(readmeRepository.getOne(1)).thenReturn(readme);

        byte[] code = Base64.getMimeDecoder().decode(readme.getText());

        String readmeString = new String(code);

        String toReturn = readmeService.gerReadmeString(1);

        Assertions.assertEquals(toReturn, readmeString);

    }
}
