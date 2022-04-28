package com.blogapp.blogapp.repository;

import com.blogapp.blogapp.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findByPostId(Long commentId);
    Comment findCommentByIdAndPersonIdAndPostId(Long commentId, Long personId,Long postId);
    List<Comment> findAllById(Long commentId);
}
