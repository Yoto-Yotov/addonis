package com.addonis.demo.services;

import com.addonis.demo.models.LastCommit;
import com.addonis.demo.models.User;
import com.addonis.demo.repository.contracts.LastCommitRepository;
import com.addonis.demo.repository.contracts.UserRepository;
import com.addonis.demo.services.contracts.LastCommitService;
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

import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.Silent.class)
public class LastCommitServicesTests {

    @Mock
    LastCommitRepository lastCommitRepository;

    @InjectMocks
    LastCommitServiceImpl lastCommitService;

    @Test
    public void getAll_Should_ReturnAllUsers() {
        //Arrange
        LastCommit lastCommit = LastCommit.builder().title("LastCommit").lastCommitId(1).build();
        List<LastCommit> lastCommitList = new ArrayList<>();
        lastCommitList.add(lastCommit);
        Mockito.when(lastCommitRepository.findAll()).thenReturn(lastCommitList);

        //Act
        List<LastCommit> lastCommitListToReturn = lastCommitService.getAll();

        //Assert
        Assert.assertSame(lastCommitList, lastCommitListToReturn);
    }

    @Test
    public void getLastCommitById_Should_ReturnUser_WhenExist() {
        //Arrange
        LastCommit lastCommit = LastCommit.builder().title("LastCommit").lastCommitId(1).build();
        Mockito.when(lastCommitService.existsById(1)).thenReturn(true);
        Mockito.when(lastCommitRepository.getOne(1)).thenReturn(lastCommit);

        //Act
        LastCommit returnLastCommit = lastCommitService.getById(1);

        //Assert
        Assert.assertSame(lastCommit, returnLastCommit);
    }

    @Test
    public void deleteUser_Should_ReturnTrue_WhenUserExist() {
        //Arrange
        LastCommit lastCommit = LastCommit.builder().title("LastCommit").lastCommitId(1).build();

        //Act
        lastCommitService.deleteById(lastCommit.getLastCommitId());

        Mockito.verify(lastCommitRepository,
                times(1)).deleteById(lastCommit.getLastCommitId());
    }

    @Test
    public void updateLastCommit_Should_ReturnUpdatedUser_WhenExist() {
        //Arrange
        LastCommit lastCommit = LastCommit.builder().title("LastCommit").lastCommitId(1).build();
        LastCommit lastCommitUpdate = LastCommit.builder().title("LastCommit New").lastCommitId(1).build();

        lastCommitService.update(lastCommitUpdate);

        Mockito.verify(lastCommitRepository,
                times(1)).save(lastCommitUpdate);
    }

    @Ignore
    @Test
    public void createLastCommit_ShouldCreateCommit() {
        //Arrange
        LastCommit lastCommit = LastCommit.builder().title("LastCommit").lastCommitId(1).build();

        Mockito.when(lastCommitRepository.save(lastCommit))
                .thenReturn(lastCommit);

        //Act
        LastCommit lastCommitToReturn = lastCommitService.create(lastCommit);
        //Assert
        Assertions.assertEquals(lastCommitToReturn.getLastCommitId(), lastCommit.getLastCommitId());
        Assertions.assertEquals(lastCommitToReturn.getTitle(), lastCommit.getTitle());
    }
}
