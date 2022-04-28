package com.blogapp.blogapp.service;


import com.blogapp.blogapp.dto.LikesDto;

public interface LikeService {
//    LikesDto createLike(LikesDto likesDto,Long postId,Long personId);
    void unLike(long likeId);
    String createLike(Long personId, Long postId);
}
