package com.addonis.demo.services;

import com.addonis.demo.models.Authorities;
import com.addonis.demo.models.User;
import com.addonis.demo.repository.contracts.AuthorityRepository;
import com.addonis.demo.repository.contracts.UserRepository;
import com.addonis.demo.services.contracts.AuthorityService;
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
public class AuthoritiesServiceTests {

    @Mock
    AuthorityRepository authorityRepository;

    @InjectMocks
    AuthorityServiceImpl authorityService;

    @Test
    public void getAll_Should_ReturnAllUsers() {
        //Arrange
        Authorities authority = Authorities.builder().username("teo").authority("ROLE_USER").build();
        List<Authorities> authorityList = new ArrayList<>();
        authorityList.add(authority);
        Mockito.when(authorityRepository.findAll()).thenReturn(authorityList);

        //Act
        List<Authorities> authorityListToReturn = authorityService.getAll();

        //Assert
        Assert.assertSame(authorityList, authorityListToReturn);
    }

    @Test
    public void getUserById_Should_ReturnAuthority() {
        //Arrange
        Authorities authority = Authorities.builder().id(1).username("teo").authority("ROLE_USER").build();
        Mockito.when(authorityRepository.getOne(1)).thenReturn(authority);

        //Act
        Authorities returnAuthority = authorityService.getById(1);

        //Assert
        Assert.assertSame(authority, returnAuthority);
    }

    @Test
    public void deleteAuthority_Should_ReturnTrue_WhenUserExist() {
        //Arrange
        Authorities authority = Authorities.builder().id(1).username("teo").authority("ROLE_USER").build();

        //Act
        authorityService.deleteById(1);

        Mockito.verify(authorityRepository,
                times(1)).deleteById(1);
    }

    @Test
    public void updateBeer_Should_ReturnUpdatedBeer_WhenExist() {
        //Arrange
        Authorities authority = Authorities.builder().username("teo").authority("ROLE_USER").build();
        Authorities updatedAuthority = Authorities.builder().username("teo").authority("ROLE_ADMIN").build();

        authorityService.update(updatedAuthority);

        Mockito.verify(authorityRepository,
                times(1)).save(updatedAuthority);
    }

    @Test
    public void createUser_ShouldCreateUser_WhenUser_NotExist() {
        //Arrange
        Authorities authority = Authorities.builder().username("teo").authority("ROLE_USER").build();

        Mockito.when(authorityRepository.save(authority))
                .thenReturn(authority);

        //Act
        Authorities authorityToReturn = authorityService.create(authority);
        //Assert
        Assertions.assertEquals(authorityToReturn.getUsername(), authority.getUsername());
        Assertions.assertEquals(authorityToReturn.getAuthority(), authority.getAuthority());
    }
}
