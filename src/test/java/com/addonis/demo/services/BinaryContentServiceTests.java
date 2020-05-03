package com.addonis.demo.services;

import com.addonis.demo.models.BinaryContent;
import com.addonis.demo.models.LastCommit;
import com.addonis.demo.repository.contracts.BinaryContentRepository;
import com.addonis.demo.repository.contracts.LastCommitRepository;
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

import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class BinaryContentServiceTests {

    @Mock
    BinaryContentRepository binaryContentRepository;

    @InjectMocks
    BinaryContentServiceImpl binaryContentService;

    @Test
    public void getAll_Should_Return_AllFiles() {
        //Arrange
        BinaryContent binaryContent = BinaryContent.builder().id(1).docName("Contents").type("exe").build();
        List<BinaryContent> list = new ArrayList<>();
        list.add(binaryContent);

        Mockito.when(binaryContentRepository.findAll()).thenReturn(list);

        //Act
        List<BinaryContent> binaryContentList = binaryContentService.getAll();

        //Assert
        Assert.assertSame(binaryContentList, list);
    }

    @Test
    public void getLastCommitById_Should_ReturnContent() {
        //Arrange
        BinaryContent binaryContent = BinaryContent.builder().id(1).docName("Contents").type("exe").build();
        Mockito.when(binaryContentRepository.getOne(1)).thenReturn(binaryContent);

        //Act
        BinaryContent returnBinaryContent = binaryContentService.getById(1);

        //Assert
        Assert.assertSame(binaryContent, returnBinaryContent);
    }

    @Test
    public void deleteContent_Should_ReturnTrue_WhenUserExist() {
        //Arrange
        BinaryContent binaryContent = BinaryContent.builder().id(1).docName("Contents").type("exe").build();

        //Act
        binaryContentService.deleteById(binaryContent.getId());

        Mockito.verify(binaryContentRepository,
                times(1)).deleteById(binaryContent.getId());
    }

    @Test
    public void updateContent_Should_ReturnUpdatedContent_WhenExist() {
        //Arrange
        BinaryContent binaryContent = BinaryContent.builder().id(1).docName("Contents").type("exe").build();
        BinaryContent binaryContentToReturn = BinaryContent.builder().id(1).docName("Contents1").type("exe1").build();

        binaryContentService.update(binaryContentToReturn);

        Mockito.verify(binaryContentRepository,
                times(1)).save(binaryContentToReturn);
    }

    @Test
    public void createContent_ShouldCreateContent() {
        //Arrange
        BinaryContent binaryContent = BinaryContent.builder().id(1).docName("Contents").type("exe").build();

        Mockito.when(binaryContentRepository.save(binaryContent))
                .thenReturn(binaryContent);

        //Act
        BinaryContent binaryContentToReturn = binaryContentService.create(binaryContent);
        //Assert
        Assertions.assertEquals(binaryContentToReturn.getId(), binaryContent.getId());
        Assertions.assertEquals(binaryContentToReturn.getDocName(), binaryContent.getDocName());
        Assertions.assertEquals(binaryContentToReturn.getType(), binaryContent.getType());
    }
}
