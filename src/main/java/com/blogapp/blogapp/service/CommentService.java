package com.blogapp.blogapp.service;

import com.blogapp.blogapp.dto.CommentDto;

import java.util.List;

public interface CommentService {

    CommentDto createComment(Long post,Long person, CommentDto commentDto);
    List<CommentDto > getCommentsByPostId(Long postId);
    CommentDto editComment(Long commentId, CommentDto commentDto, Long id,Long postId);
}
