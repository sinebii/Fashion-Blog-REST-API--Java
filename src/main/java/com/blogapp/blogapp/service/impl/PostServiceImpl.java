package com.blogapp.blogapp.service.impl;

import com.blogapp.blogapp.dataconverter.PostConverter;
import com.blogapp.blogapp.dto.PostDto;
import com.blogapp.blogapp.entity.Category;
import com.blogapp.blogapp.entity.Comment;
import com.blogapp.blogapp.entity.Post;
import com.blogapp.blogapp.exceptions.ResourceNotFoundException;
import com.blogapp.blogapp.repository.CategoryRepository;
import com.blogapp.blogapp.repository.CommentRepository;
import com.blogapp.blogapp.repository.LikeRepository;
import com.blogapp.blogapp.repository.PostRepository;
import com.blogapp.blogapp.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private PostConverter postConverter;

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private LikeRepository likeRepository;

    @Override
    public PostDto createNewPost(Long categoryId, PostDto postDto) {
        Post post = postConverter.convertDtoToEntity(postDto);
        Category category = categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","id",categoryId));
        post.setCategory(category);
        Post newPost = postRepository.save(post);

       PostDto response = postConverter.convertEntityToDto(newPost);
        return response;
    }

    @Override
    public List<PostDto> getAllPost() {

        List<Post> postList = postRepository.findAll();
        return postList.stream().map(post -> postConverter.convertEntityToDto(post)).collect(Collectors.toList());
    }

    @Override
    public PostDto getPostById(Long id) {
        Post post = postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Post","id",id));
        PostDto postDto1 = postConverter.convertEntityToDto(post);

        postDto1.setLikes(likeRepository.findAllByPostId(postDto1.getId()).size());
        postDto1.setCommentSet(commentRepository.findByPostId(postDto1.getId()));
        return postDto1;
    }

    @Override
    public PostDto updatePost(PostDto postDto, Long id) {
        Post post = postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Product","id",id));
        post.setName(postDto.getName());
        post.setDescription(postDto.getDescription());
        post.setPrice(postDto.getPrice());

        Post updatedPost = postRepository.save(post);
        PostDto postDto1 = postConverter.convertEntityToDto(updatedPost);
        return postDto1;

    }

    @Override
    public void deletePost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Post","id",id));
        deleteCommentsOfAPost(id);
        postRepository.delete(post);


    }

    @Override
    public Post getPostsById(Long id) {

        return postRepository.findById(id).get();
    }

    @Override
    public void deleteCommentsOfAPost(Long postId) {
        List<Comment> comments = commentRepository.findByPostId(postId);
        commentRepository.deleteAll(comments);
    }


}
