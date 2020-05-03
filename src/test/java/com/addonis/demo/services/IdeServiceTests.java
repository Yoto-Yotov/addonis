package com.addonis.demo.services;

import com.addonis.demo.models.IDE;
import com.addonis.demo.repository.contracts.IdeRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.Silent.class)
public class IdeServiceTests {

    @Mock
    IdeRepository ideRepository;

    @InjectMocks
    IdeServiceImpl ideService;

    @Test
    public void getAll_Should_CallRepository_When_Valid() {
    //Arrange
        Mockito.when(ideRepository.findAll()).thenReturn(new ArrayList<>());

    //Act
        ideService.getAll();

    //Assert
        Mockito.verify(ideRepository, times(1)).findAll();
    }

    @Test
    public void getIdeByName_Should_CreateIde_When_IdeNotExist() {
    //Arrange
        IDE ide = IDE.builder().ideName("ide").build();
        Mockito.when(ideRepository.existsByIdeNameIgnoreCase("ide")).thenReturn(false);
        Mockito.when(ideRepository.findByIdeNameIgnoreCase("ide")).thenReturn(ide);

    //Act
        IDE ideResult = ideService.getByName("ide");

    //Assert
        Assert.assertSame(ide, ideResult);
    }
}
