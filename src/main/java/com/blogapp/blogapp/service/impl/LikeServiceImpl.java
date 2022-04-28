package com.blogapp.blogapp.service.impl;

import com.blogapp.blogapp.dataconverter.LikeConverter;
import com.blogapp.blogapp.dto.LikesDto;
import com.blogapp.blogapp.entity.Likes;
import com.blogapp.blogapp.entity.Person;
import com.blogapp.blogapp.entity.Post;
import com.blogapp.blogapp.exceptions.ResourceNotFoundException;
import com.blogapp.blogapp.repository.LikeRepository;
import com.blogapp.blogapp.repository.PersonRepository;
import com.blogapp.blogapp.repository.PostRepository;
import com.blogapp.blogapp.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LikeServiceImpl implements LikeService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private LikeRepository likeRepository;
    @Autowired
    private LikeConverter likeConverter;

//    @Override
//    public LikesDto createLike(LikesDto likesDto,Long postId,Long personId) {
//        Post post = postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","id",postId));
//        Person person = personRepository.findById(personId).orElseThrow(()->new ResourceNotFoundException("Post","id",personId));
//        Likes likes = likeConverter.convertDtoToEntity(likesDto);
//        likes.setPost(post);
//        likes.setPerson(person);
//        Likes likes1 = likeRepository.save(likes);
//        LikesDto likesDto1 = likeConverter.convertEntityToDto(likes1);
//        return likesDto1;
//    }

    @Override
    public void unLike (long likeId) {
        //Post post = postRepository.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","id",postId));
        //Person person = personRepository.findById(personId).orElseThrow(()->new ResourceNotFoundException("Post","id",personId));
        Likes likes = likeRepository.findById(likeId).orElseThrow(()->new ResourceNotFoundException("Like","id",likeId));
        likeRepository.delete(likes);
    }

    @Override
    public String createLike(Long personId, Long postId) {
        Likes like = null;
        try {
            like = likeRepository.findLikesByPersonIdAndPostId(personId,postId);
        }catch (Exception ex){

        }
        if(like != null) return "Already Liked";

        like = new Likes();
        like.setPersonId(personId);
        like.setPostId(postId);
        likeRepository.save(like);
            return "Like successful";
    }
}
