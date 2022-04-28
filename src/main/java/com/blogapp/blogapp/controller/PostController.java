package com.blogapp.blogapp.controller;
import com.blogapp.blogapp.dto.PostDto;
import com.blogapp.blogapp.service.CommentService;
import com.blogapp.blogapp.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/blog/post")
public class PostController {

    @Autowired
    private PostService postService;
    @Autowired
    private CommentService commentService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PostMapping("/create-post/{categoryId}")
    public ResponseEntity<PostDto> createPost(@PathVariable(name = "categoryId") Long categoryId, @RequestBody PostDto postDto, HttpSession session){
        return  postService.createNewPost(categoryId,postDto,session);
    }

    @GetMapping("/get-all-post")
    public ResponseEntity<List<PostDto>> getAllPost(HttpSession session){
        return  postService.getAllPost(session);
    }

    @GetMapping("find-a-post/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable(name="id") Long id, HttpSession session){
        return  postService.getPostById(id, session);
    }


    @PutMapping("/update-post/{id}")
    public  ResponseEntity<PostDto> updatePostById(@RequestBody PostDto postDto, @PathVariable(name = "id") Long id, HttpSession session){
       return postService.updatePost(postDto,id,session);
    }

    @DeleteMapping("/delete-post/{id}")
    public ResponseEntity<String> deletePost(@PathVariable(name = "id") Long id, HttpSession session){
        return postService.deletePost(id,session);
    }



}
