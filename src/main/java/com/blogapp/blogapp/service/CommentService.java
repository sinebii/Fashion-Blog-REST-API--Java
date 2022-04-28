package com.blogapp.blogapp.service;

import com.blogapp.blogapp.dto.CommentDto;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface CommentService {

    ResponseEntity<String> createComment(Long post, CommentDto commentDto, HttpSession session);
    ResponseEntity<List<CommentDto >> getCommentsByPostId(Long postId,HttpSession session);
    ResponseEntity<CommentDto> editComment(Long commentId, CommentDto commentDto,Long postId,HttpSession session);
}
