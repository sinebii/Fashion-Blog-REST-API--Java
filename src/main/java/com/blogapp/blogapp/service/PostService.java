package com.blogapp.blogapp.service;

import com.blogapp.blogapp.dto.PostDto;
import com.blogapp.blogapp.entity.Post;

import java.util.List;

public interface PostService {

    PostDto createNewPost(Long categoryId,PostDto postDto);
    List<PostDto> getAllPost();
    PostDto getPostById(Long id);
    PostDto updatePost(PostDto postDto, Long id);
    void deletePost(Long id);
    Post getPostsById(Long id);
    void deleteCommentsOfAPost(Long postId);
}
