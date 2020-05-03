package com.addonis.demo.services;

import com.addonis.demo.exceptions.DuplicateEntityException;
import com.addonis.demo.exceptions.EntityNotFoundException;
import com.addonis.demo.exceptions.NotAuthorizedException;
import com.addonis.demo.models.Tag;
import com.addonis.demo.repository.contracts.TagRepository;
import com.addonis.demo.services.contracts.AddonService;
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

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TagServiceTests {

    @Mock
    TagRepository tagRepository;

    @Mock
    AddonService addonService;

    @Mock
    UserService userService;

    @InjectMocks
    TagServiceImpl tagService;

    @Test
    public void getAll_Should_callRepository_When_Valid() {
        //Arrange
        Mockito.when(tagRepository.findAll()).thenReturn(new ArrayList<Tag>());

        //Act
        tagService.getAll();

        //Assert
        Mockito.verify(tagRepository, times(1)).findAll();
    }

    @Test
    public void getTagById_Should_GetTag_WhenTagExists() {
        //Arrange
        Tag tag = Tag.builder().tagName("test").build();
        Mockito.when(tagRepository.findById(anyInt())).thenReturn(java.util.Optional.ofNullable(tag));

        //Act Assert
        Assert.assertSame(tag, tagService.getById(anyInt()));
    }

    @Test
    public void getTagById_Should_ThrowException_WhenTagDoesNotExist() {
        //Arrange
        Tag tag = Tag.builder().tagName("test").build();
        Mockito.when(tagRepository.findById(anyInt())).thenThrow(EntityNotFoundException.class);

        //Act Assert
        Assert.assertThrows(EntityNotFoundException.class, () -> tagService.getById(anyInt()));
    }


    @Test
    public void deleteById_Should_throwException_When_TagDoesNotExist() {
        //Arrange
        Mockito.when(tagRepository.existsById(anyInt())).thenReturn(false);

        //Act Assert
        Assert.assertThrows(EntityNotFoundException.class, () -> tagService.deleteById(anyInt()));
    }

    @Test
    public void deleteById_Should_deleteTagFromAllAddons_When_TagExists() {
        //Arrange
        Mockito.when(tagRepository.existsById(anyInt())).thenReturn(true);
        doNothing().when(tagRepository).deleteTagFromAllAddons(anyInt());
        doNothing().when(tagRepository).deleteById(anyInt());

        //Act
        tagService.deleteById(anyInt());

        //Assert
        Mockito.verify(tagRepository, times(1)).deleteTagFromAllAddons(anyInt());
    }

    @Test
    public void deleteById_Should_deleteTag_When_TagExists() {
        //Arrange
        Mockito.when(tagRepository.existsById(anyInt())).thenReturn(true);
        doNothing().when(tagRepository).deleteTagFromAllAddons(anyInt());
        doNothing().when(tagRepository).deleteById(anyInt());

        //Act
        tagService.deleteById(anyInt());

        //Assert
        Mockito.verify(tagRepository, times(1)).deleteById(anyInt());
    }

    @Test
    public void update_Should_callRepository_When_Valid() {
        //Arrange
        Tag tag = Tag.builder().tagName("test").build();
        Mockito.when(tagRepository.save(tag)).thenReturn(tag);

        //Act
        tagService.update(tag);

        //Assert
        Mockito.verify(tagRepository, times(1)).save(tag);
    }

    @Test
    public void createTag_Should_ThrowException_When_TagExistByName() {
        //Arrange
        Tag tag = Tag.builder().tagName("test").build();

        Mockito.when(tagRepository.existsByTagName(anyString())).thenReturn(true);

        //Act //Assert
        Assert.assertThrows(DuplicateEntityException.class, () -> tagService.create(tag));
    }

    @Test
    public void createTag_Should_createTag_When_TagDOesNotExist() {
        //Arrange
        Tag tag = Tag.builder().tagName("test").build();

        Mockito.when(tagRepository.existsByTagName(anyString())).thenReturn(false);
        Mockito.when(tagRepository.save(tag)).thenReturn(tag);
        //Act
        Tag result = tagService.create(tag);

        // Assert
        Assert.assertSame(tag, result);
    }

    @Test
    public void deleteTagByName_Should_ThrowException_When_TagDoesNotExist() {
        //Arrange
        Mockito.when(tagRepository.getTagByTagName(anyString())).thenReturn(null);
        Mockito.when(userService.isAdmin("john")).thenReturn(true);

        //Act //Assert
        Assert.assertThrows(EntityNotFoundException.class, () -> tagService.deleteTagByName(anyString(), "john"));
    }

    @Test
    public void deleteTagByName_Should_deleteTagFromAllAddons_When_TagExists() {
        //Arrange
        Tag tag = Tag.builder().tagId(1).build();
        Mockito.when(tagRepository.getTagByTagName(anyString())).thenReturn(tag);
        Mockito.when(userService.isAdmin("john")).thenReturn(true);

        //Act
        tagService.deleteTagByName(anyString(), "john");

        //Assert
        Mockito.verify(tagRepository, times(1)).deleteTagFromAllAddons(tag.getTagId());
    }

    @Test
    public void deleteTagByName_Should_deleteTagByName_When_TagExists() {
        //Arrange
        Tag tag = Tag.builder().tagId(1).tagName("test1").build();
        Mockito.when(tagRepository.getTagByTagName(anyString())).thenReturn(tag);
        doNothing().when(tagRepository).deleteTagFromAllAddons(anyInt());
        Mockito.when(userService.isAdmin("john")).thenReturn(true);

        //Act
        tagService.deleteTagByName(anyString(), "john");

        //Assert
        Mockito.verify(tagRepository, times(1)).deleteTagByName(anyString());
    }

    @Test
    public void removeTagFromAddon_Should_callRepository_When_InputIsValid() {
        //Arrange
        Tag tag = Tag.builder().tagName("test2").tagId(2).build();
        Mockito.when(tagRepository.getTagByTagName(anyString())).thenReturn(tag);
        Mockito.when(userService.isAdmin("john")).thenReturn(true);

        //Act
        tagService.removeTagFromAddon(2, "test2", "john");

        //Assert
        Mockito.verify(tagRepository, times(1)).removeTagFromAddon(anyInt(), anyInt());

    }

    @Test
    public void addTagToAddon_Should_ThrowException_When_AddonDoesNotExist() {
        //Arrange
        Tag tag = Tag.builder().tagName("test1").tagId(1).build();
        Mockito.when(tagRepository.getTagByTagName("test1")).thenReturn(tag);
        Mockito.when(addonService.checkAddonExistsById(anyInt())).thenReturn(false);
        Mockito.when(userService.isAdmin("john")).thenReturn(true);

        //Act //Assert
        Assert.assertThrows(EntityNotFoundException.class, () -> tagService.addTagToAddon(anyInt(), "test1", "john"));
    }

    @Test
    public void addTagToAddon_Should_CreateNewTag_When_TagDoesNotExist() {
        //Arrange
        Tag tag = Tag.builder().tagName("test1").tagId(1).build();
        Mockito.when(tagRepository.getTagByTagName("test1")).thenReturn(null);
        Mockito.when(addonService.checkAddonExistsById(anyInt())).thenReturn(false);
        Mockito.when(userService.isAdmin("john")).thenReturn(true);

        //Act //Assert
        Assert.assertThrows(EntityNotFoundException.class, () -> tagService.addTagToAddon(anyInt(), "test1", "john"));
    }

    @Test
    public void addTagToAddon_Should_ThrowException_When_AddonHasTag() {
        //Arrange
        Tag tag = Tag.builder().tagName("test1").tagId(1).build();
        Mockito.when(tagRepository.getTagByTagName("test1")).thenReturn(tag);
        Mockito.when(addonService.checkAddonExistsById(anyInt())).thenReturn(true);
        doThrow(DataIntegrityViolationException.class).when(tagRepository).addTagToAddon(1,1);
        Mockito.when(userService.isAdmin("john")).thenReturn(true);

        //Act //Assert
        Assert.assertThrows(DuplicateEntityException.class, () -> tagService.addTagToAddon(1, "test1", "john"));
    }

    @Test
    public void addTagToAddon_Should_ReturnTag_When_InputIsValid() {
        //Arrange
        Tag tag = Tag.builder().tagName("test1").tagId(1).build();
        Mockito.when(tagRepository.getTagByTagName("test1")).thenReturn(tag);
        Mockito.when(addonService.checkAddonExistsById(anyInt())).thenReturn(true);
        doNothing().when(tagRepository).addTagToAddon(1,1);
        Mockito.when(userService.isAdmin("john")).thenReturn(true);

        //Act //Assert
        Assert.assertSame(tag, tagService.addTagToAddon(1, "test1", "john"));
    }

    @Test
    public void renameTag_Should_renameTag_When_InputIsValid() {
        //Arrange
        Tag tag = Tag.builder().tagName("test1").tagId(1).build();
        Mockito.when(userService.isAdmin("john")).thenReturn(true);
        Mockito.when(tagRepository.getOne(1)).thenReturn(tag);

        //Act
         tagService.renameTag(1, "test1", "john");

        //Assert
        Mockito.verify(tagRepository, times(1)).save(tag);
    }

    @Test
    public void renameTag_Should_ThrowException_When_UserIsNotAuthorized() {
        //Arrange
        Tag tag = Tag.builder().tagName("test1").tagId(1).build();
        Mockito.when(userService.isAdmin("john")).thenReturn(false);

        //Act //Assert
        Assert.assertThrows(NotAuthorizedException.class, () -> tagService.renameTag(1, "test1", "john"));
    }

    @Test
    public void removeTagFromAddon_Should_ThrowException_When_UserIsNotAuthorized() {
        //Arrange
        Tag tag = Tag.builder().tagName("test1").tagId(1).build();
        Mockito.when(userService.isAdmin("john")).thenReturn(false);
        Mockito.when(addonService.getCreatorName(1)).thenReturn("");

        //Act //Assert
        Assert.assertThrows(NotAuthorizedException.class, () -> tagService.removeTagFromAddon(1, "test1", "john"));
    }

    @Test
    public void addTagToAddon_Should_ThrowException_When_UserIsNotAuthorized() {
        //Arrange
        Tag tag = Tag.builder().tagName("test1").tagId(1).build();
        Mockito.when(userService.isAdmin("john")).thenReturn(false);
        Mockito.when(addonService.getCreatorName(1)).thenReturn("");

        //Act //Assert
        Assert.assertThrows(NotAuthorizedException.class, () -> tagService.addTagToAddon(1, "test1", "john"));
    }

    @Test
    public void renameTag_Should_ThrowException_When_AddonHasTag() {
        //Arrange
        Tag tag = Tag.builder().tagName("test1").tagId(1).build();
        Mockito.when(tagRepository.getOne(1)).thenReturn(tag);
        doThrow(DataIntegrityViolationException.class).when(tagRepository).save(tag);
        Mockito.when(userService.isAdmin("john")).thenReturn(true);

        //Act //Assert
        Assert.assertThrows(DuplicateEntityException.class, () -> tagService.renameTag(1, "test1", "john"));
    }

    @Test
    public void deleteTagByName_Should_ThrowException_When_UserNotAuthorized() {
        //Arrange
        Tag tag = Tag.builder().tagName("test1").tagId(1).build();
        Mockito.when(userService.isAdmin("john")).thenReturn(false);

        //Act //Assert
        Assert.assertThrows(NotAuthorizedException.class, () -> tagService.deleteTagByName( "test1", "john"));
    }
}
