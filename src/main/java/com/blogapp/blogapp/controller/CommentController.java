package com.blogapp.blogapp.controller;

import com.blogapp.blogapp.dto.CommentDto;
import com.blogapp.blogapp.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
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
        return commentService.createComment(postId,commentDto,session);
    }

    @PutMapping("/{postId}/edit-comment/{commentId}")
    public ResponseEntity<CommentDto> editComment(@PathVariable(value = "postId") Long postId, @RequestBody CommentDto commentDto, @PathVariable(value = "commentId") Long commentId,HttpSession session){
        return commentService.editComment(commentId,commentDto,postId,session);
    }

    @GetMapping("/comments/{postId}")
    public ResponseEntity<List<CommentDto>> getCommentsByPostId(@PathVariable(name = "postId") Long postId, HttpSession session){
        return commentService.getCommentsByPostId(postId,session);
    }
}
