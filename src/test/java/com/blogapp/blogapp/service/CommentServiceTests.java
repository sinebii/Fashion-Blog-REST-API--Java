package com.blogapp.blogapp.service;

import com.blogapp.blogapp.dataconverter.CommentConverter;
import com.blogapp.blogapp.dto.CommentDto;
import com.blogapp.blogapp.dto.PostDto;
import com.blogapp.blogapp.entity.Comment;
import com.blogapp.blogapp.entity.Person;
import com.blogapp.blogapp.entity.Post;
import com.blogapp.blogapp.exceptions.ResourceNotFoundException;
import com.blogapp.blogapp.repository.CommentRepository;
import com.blogapp.blogapp.repository.PersonRepository;
import com.blogapp.blogapp.repository.PostRepository;
import com.blogapp.blogapp.service.impl.CommentServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpSession;
import java.util.Objects;

import static java.util.Optional.of;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTests {
    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private CommentServiceImpl commentService;

    @Mock
    private PersonRepository personRepository;

    @Mock
    private PostRepository postRepository;
    @Mock
    private CommentConverter commentConverter;

    Comment comment;
    CommentDto commentDto;


    Post post;
    Person person;
    Person person1;

    @Mock
    HttpSession session;

    @BeforeEach
    public void setup(){

        post = new Post();
        post.setId(1L);
        post.setName("My first post");
        post.setDescription("Desc for 1st post");
        post.setPrice(34.778);

        comment = new Comment();
        comment.setId(1l);
        comment.setMessage("Hello World again");
        comment.setPostId(1L);
        comment.setPersonId(1L);

        commentDto = new CommentDto();
        commentDto.setId(1L);
        commentDto.setMessage("This the first comment");



        person = new Person();
        person.setId(1L);
        person.setName("CUSTOMER");
        person.setEmail("ebube@yahoo.com");
        person.setRole("CUSTOMER");
        person.setPassword("12345");

        person1 = new Person();
        person1.setId(1L);
        person1.setName("CUSTOMER");
        person1.setEmail("ebube@yahoo.com");
        person1.setRole("ADMIN");
        person1.setPassword("12345");

    }

    @Test
    public void shouldThrowExceptionWhenYouTryToCreatCommentWithNoPostId(){
        System.out.println(post.getId());
        when(commentConverter.convertDtoToEntity(commentDto)).thenReturn(comment);
        Mockito.when(postRepository.findById(post.getId())).thenReturn(of(post));

        when(session.getAttribute("person")).thenReturn(person);
        org.junit.jupiter.api.Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            ResponseEntity<String> responseEntity = commentService.createComment(1L,commentDto,session);
        });
    }


    @Test
    public void shouldCreateANewComment(){
        System.out.println(post.getId());
        when(commentConverter.convertDtoToEntity(commentDto)).thenReturn(comment);
        Mockito.when(postRepository.findById(post.getId())).thenReturn(of(post));
        Mockito.when(commentRepository.save(any())).thenReturn(comment);
       // when(commentConverter.convertEntityToDto(comment)).thenReturn(commentDto);
        when(session.getAttribute("person")).thenReturn(person);
        when(personRepository.findById(1l)).thenReturn(of(person));


        ResponseEntity<String> responseEntity = commentService.createComment(1L,commentDto,session);
        Assertions.assertThat(responseEntity.getBody()).isEqualTo("The operation was successful");
        //verify(postRepository, times(1)).save(any());
        Assertions.assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    public void shouldEditAComment(){

        when(commentRepository.save(comment)).thenReturn(comment);
        when(commentConverter.convertEntityToDto(comment)).thenReturn(commentDto);
        when(commentRepository.findCommentByIdAndPersonIdAndPostId(1l,1l,1l)).thenReturn(comment);
        when(session.getAttribute("person")).thenReturn(person);

        System.out.println(comment.getMessage());
        ResponseEntity<CommentDto> responseEntity =  commentService.editComment(1l,commentDto,1L,session);
        Assertions.assertThat(Objects.requireNonNull(responseEntity.getBody()).getMessage()).isEqualTo("This the first comment");
    }

}
