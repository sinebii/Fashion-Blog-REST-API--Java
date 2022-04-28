package com.blogapp.blogapp.dataconverter;

import com.blogapp.blogapp.dto.CommentDto;
import com.blogapp.blogapp.entity.Comment;
import org.springframework.stereotype.Component;

@Component
public class CommentConverter {

    public Comment convertDtoToEntity(CommentDto commentDto){

        Comment comment = new Comment();
        comment.setId(comment.getId());

        comment.setPersonId(comment.getPersonId());
        comment.setPostId(comment.getPostId());
//        comment.setName(commentDto.getName());
//        comment.setEmail(commentDto.getEmail());
        comment.setMessage(commentDto.getMessage());
        return comment;
    }

    public CommentDto convertEntityToDto(Comment comment){
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
//        commentDto.setName(comment.getName());
//        commentDto.setEmail(comment.getEmail());
        commentDto.setMessage(comment.getMessage());
        return commentDto;
    }
}
