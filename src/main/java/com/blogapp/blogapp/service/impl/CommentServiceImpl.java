package com.blogapp.blogapp.service.impl;

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
import com.blogapp.blogapp.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public CommentDto createComment(Long postId, Long personId,CommentDto commentDto) {
        Comment comment = commentConverter.convertDtoToEntity(commentDto);

        Post post = postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","id",postId));
        Person person = personRepository.findById(personId).orElseThrow(()->new ResourceNotFoundException("Post","id",personId));
        comment.setPostId(post.getId());
        comment.setPersonId(person.getId());
        Comment newComment = commentRepository.save(comment);
        CommentDto commentDto1 = commentConverter.convertEntityToDto(newComment);

        return commentDto1;
    }

    @Override
    public List<CommentDto> getCommentsByPostId(Long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
        return comments.stream().map(comment->commentConverter.convertEntityToDto(comment)).collect(Collectors.toList());
    }

    @Override
    public CommentDto editComment(Long commentId, CommentDto commentDto, Long personId, Long postId) {
        Comment comment = commentRepository.findCommentByIdAndPersonIdAndPostId(commentId,personId,postId);
        comment.setMessage(commentDto.getMessage());
        Comment updatedComment = commentRepository.save(comment);
        CommentDto updatedCommentDto = commentConverter.convertEntityToDto(updatedComment);
        return updatedCommentDto;

    }

}
