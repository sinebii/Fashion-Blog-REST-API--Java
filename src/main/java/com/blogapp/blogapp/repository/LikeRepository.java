package com.blogapp.blogapp.repository;

import com.blogapp.blogapp.entity.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LikeRepository extends JpaRepository<Likes, Long> {
    Likes findLikesByPersonIdAndPostId(Long personId, Long postId);
    List<Likes> findAllByPostId(Long postId);
}
