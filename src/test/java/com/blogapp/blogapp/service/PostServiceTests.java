package com.blogapp.blogapp.service;

import com.blogapp.blogapp.dataconverter.PostConverter;
import com.blogapp.blogapp.dto.PostDto;
import com.blogapp.blogapp.entity.Category;
import com.blogapp.blogapp.entity.Person;
import com.blogapp.blogapp.entity.Post;
import com.blogapp.blogapp.repository.CategoryRepository;
import com.blogapp.blogapp.repository.CommentRepository;
import com.blogapp.blogapp.repository.LikeRepository;
import com.blogapp.blogapp.repository.PostRepository;
import com.blogapp.blogapp.service.impl.CategoryServiceImpl;
import com.blogapp.blogapp.service.impl.PostServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Objects;

import static java.util.Optional.of;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PostServiceTests {
    @Mock
    PostRepository postRepository;
    @Mock
    LikeRepository likeRepository;

    @Mock
    CommentRepository commentRepository;

    @InjectMocks
    PostServiceImpl postService;

    @Mock
    PostConverter converter;

    @InjectMocks
    CategoryServiceImpl categoryService;

    @Mock
    CategoryRepository categoryRepository;

    @Mock
    PostConverter postConverter;

    PostDto postDto;
    Post post;
    Person person;
    Category category;
    @Mock
    private HttpSession session;

    @BeforeEach
    public void setup(){

        category=new Category();
        category.setId(1L);
        category.setName("My fashion");
        category.setDescription("Sinebi innazo description");

        postDto = new PostDto();
        postDto.setName("Fashion");
        postDto.setDescription("This is the Description");
        postDto.setPrice(3444.444);

        post = new Post();
        post.setName("Nigeria");
        post.setDescription("This is mine");
        post.setPrice(4566.990);
        post.setCategory(category);
        post.setLikeCount(3);

        person = new Person();
        person.setId(1L);
        person.setName("CUSTOMER");
        person.setEmail("ebube@yahoo.com");
        person.setRole("ADMIN");
        person.setPassword("12345");


    }

    @Test
    public void shouldSaveANewPost(){
        when(postConverter.convertDtoToEntity(postDto)).thenReturn(post);
        Mockito.when(categoryRepository.findById(1L)).thenReturn(of(category));
        Mockito.when(postRepository.save(any())).thenReturn(post);
        when(postConverter.convertEntityToDto(post)).thenReturn(postDto);
        when(session.getAttribute("person")).thenReturn(person);
        ResponseEntity<PostDto> responseEntity =  postService.createNewPost(1L,postDto,session);
        Assertions.assertThat(Objects.requireNonNull(responseEntity.getBody()).getName()).isNotNull();
        verify(postRepository, times(1)).save(any());
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);

    }

    @Test
    public void shouldGetAllPosts() {
        List<Post> postList = List.of(new Post(2l,"Name","Description",2.455,category,3));
        when(postRepository.findAll()).thenReturn(postList);
        when(session.getAttribute("person")).thenReturn(person);
        ResponseEntity<List<PostDto>> response = postService.getAllPost(session);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(Objects.requireNonNull(response.getBody()).size()).isEqualTo(postList.size());
    }

    @Test
    public void shouldEditAPost(){
        Mockito.when(postRepository.findById(1L)).thenReturn(of(post));
        when(postRepository.save(post)).thenReturn(post);
        when(postConverter.convertEntityToDto(post)).thenReturn(postDto);
        when(session.getAttribute("person")).thenReturn(person);
        ResponseEntity<PostDto> responseEntity =  postService.updatePost(postDto,1l,session);
        Assertions.assertThat(Objects.requireNonNull(responseEntity.getBody()).getName()).isEqualTo("Fashion");
    }

    @Test
    public void shouldGetPostById(){
        Mockito.when(postRepository.findById(1L)).thenReturn(of(post));
        when(postConverter.convertEntityToDto(post)).thenReturn(postDto);
        when(session.getAttribute("person")).thenReturn(person);
        ResponseEntity<PostDto> responseEntity =  postService.getPostById(1l,session);
        Assertions.assertThat(Objects.requireNonNull(responseEntity.getBody()).getName()).isEqualTo("Fashion");
    }


    @Test
    public void shouldDeletePost(){

        Mockito.when(postRepository.findById(1L)).thenReturn(of(post));
        when(session.getAttribute("person")).thenReturn(person);
        ResponseEntity<String> responseEntity =  postService.deletePost(1l,session);
        Assertions.assertThat(Objects.requireNonNull(responseEntity.getBody())).isEqualTo("Post deleted successfully");

    }



}
