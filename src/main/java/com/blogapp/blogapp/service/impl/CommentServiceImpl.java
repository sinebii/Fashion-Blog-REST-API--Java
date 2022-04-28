package com.blogapp.blogapp.service.impl;

import com.blogapp.blogapp.dataconverter.CommentConverter;
import com.blogapp.blogapp.dto.CommentDto;
import com.blogapp.blogapp.entity.Comment;
import com.blogapp.blogapp.entity.Person;
import com.blogapp.blogapp.entity.Post;
import com.blogapp.blogapp.exceptions.ResourceNotFoundException;
import com.blogapp.blogapp.repository.CommentRepository;
import com.blogapp.blogapp.repository.PersonRepository;
import com.blogapp.blogapp.repository.PostRepository;
import com.blogapp.blogapp.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private CommentConverter commentConverter;

    @Override
    public ResponseEntity<String> createComment(Long postId, CommentDto commentDto, HttpSession session) {
        Person loggedPerson = (Person) session.getAttribute("person");
        if(loggedPerson!=null){
            Comment comment = commentConverter.convertDtoToEntity(commentDto);

            Post post = postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","id",postId));
            Person person = personRepository.findById(loggedPerson.getId()).orElseThrow(()->new ResourceNotFoundException("Post","id",loggedPerson.getId()));
            comment.setPostId(post.getId());
            comment.setPersonId(person.getId());
            commentRepository.save(comment);
            return  new ResponseEntity<>("The operation was successful", HttpStatus.CREATED);
        }

        return new ResponseEntity<>("Access Denied", HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<List<CommentDto>> getCommentsByPostId(Long postId, HttpSession session) {
        Person person = (Person) session.getAttribute("person");
        if(person!=null){
            List<Comment> comments = commentRepository.findByPostId(postId);
            return new ResponseEntity<>(comments.stream().map(comment->commentConverter.convertEntityToDto(comment)).collect(Collectors.toList()),HttpStatus.OK);
        }
       return  null;
    }

    @Override
    public ResponseEntity<CommentDto> editComment(Long commentId, CommentDto commentDto, Long postId, HttpSession session) {
        Person person = (Person) session.getAttribute("person");
        if(person!=null){
            Comment comment = commentRepository.findCommentByIdAndPersonIdAndPostId(commentId,person.getId(),postId);
            comment.setMessage(commentDto.getMessage());
            Comment updatedComment = commentRepository.save(comment);
            return  new ResponseEntity<>(commentConverter.convertEntityToDto(updatedComment),HttpStatus.OK);
        }

        return null;
    }

}
