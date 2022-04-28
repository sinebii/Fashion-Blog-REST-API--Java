package com.blogapp.blogapp.controller;

import com.blogapp.blogapp.dto.CommentDto;
import com.blogapp.blogapp.dto.PersonDto;
import com.blogapp.blogapp.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/blog/post")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping("/comment/{postId}")
    public ResponseEntity<String> createComment(@PathVariable(name = "postId") Long postId, @RequestBody CommentDto commentDto, HttpSession session){
        PersonDto personDto = (PersonDto) session.getAttribute("person");
        if(personDto!=null){
            commentService.createComment(postId,personDto.getId(),commentDto);
            ResponseEntity<String> responseEntity = new ResponseEntity<>("Post Was Successful", HttpStatus.CREATED);
            return responseEntity;
        }
        ResponseEntity<String> responseEntity = new ResponseEntity<>("Guest cannot post comment, Please login or register ", HttpStatus.CREATED);
        return responseEntity;
    }

    @PutMapping("/{postId}/edit-comment/{commentId}")
    public ResponseEntity<String> editComment(@PathVariable(value = "postId") Long postId, @RequestBody CommentDto commentDto, @PathVariable(value = "commentId") Long commentId,HttpSession session){
        PersonDto personDto = (PersonDto) session.getAttribute("person");
        if(personDto!=null){
            commentService.editComment(commentId,commentDto, personDto.getId(),postId);
            ResponseEntity<String> responseEntity = new ResponseEntity<>("Comment edited Successfully", HttpStatus.OK);
            return responseEntity;
        }
        ResponseEntity<String> responseEntity = new ResponseEntity<>("Cannot edit comment please login or register", HttpStatus.OK);
        return responseEntity;
    }

    @GetMapping("/comments/{postId}")
    public List<CommentDto> getCommentsByPostId(@PathVariable(name = "postId") Long postId, HttpSession session){
        PersonDto personDto = (PersonDto) session.getAttribute("person");
        if(personDto!=null){
            return commentService.getCommentsByPostId(postId);
        }
        return null;
    }
}
