package com.blogapp.blogapp.service;

import com.blogapp.blogapp.dto.PostDto;
import com.blogapp.blogapp.entity.Post;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface PostService {

    ResponseEntity<PostDto> createNewPost(Long categoryId, PostDto postDto, HttpSession session);
    ResponseEntity<List<PostDto>> getAllPost(HttpSession session);
    ResponseEntity<PostDto> getPostById(Long id,HttpSession session);
    ResponseEntity<PostDto> updatePost(PostDto postDto, Long id,HttpSession session);
    ResponseEntity<String> deletePost(Long id,HttpSession session);
    Post getPostsById(Long id);
    void deleteCommentsOfAPost(Long postId);
}
