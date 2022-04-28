package com.blogapp.blogapp.controller;
import com.blogapp.blogapp.dto.PersonDto;
import com.blogapp.blogapp.dto.PostDto;
import com.blogapp.blogapp.entity.Post;
import com.blogapp.blogapp.service.CommentService;
import com.blogapp.blogapp.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<String> createPost(@PathVariable(name = "categoryId") Long categoryId, @RequestBody PostDto postDto, HttpSession session){

        PersonDto personDto = (PersonDto) session.getAttribute("person");

        if(personDto.getRole().equals("ADMIN")){
            postService.createNewPost(categoryId,postDto);
            ResponseEntity<String> responseEntity = new ResponseEntity<>("Post was successfully created",HttpStatus.CREATED);
            return responseEntity;
        }

        ResponseEntity<String> responseEntity = new ResponseEntity<>("Access Denied",HttpStatus.CREATED);
        return responseEntity;
    }

    @GetMapping("/get-all-post")
    public List<PostDto> getAllPost(HttpSession session){
        PersonDto personDto = (PersonDto) session.getAttribute("person");
        if(personDto.getRole().equals("ADMIN")){
            return postService.getAllPost();
        }
        return null;
    }

    @GetMapping("find-a-post/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable(name="id") Long id, HttpSession session){
        PersonDto personDto = (PersonDto) session.getAttribute("person");
        if(personDto.getRole().equals("ADMIN")){
            PostDto postDto1 = postService.getPostById(id);
            ResponseEntity<PostDto> responseEntity = new ResponseEntity<>(postDto1,HttpStatus.OK);
            return responseEntity;
        }
        return null;
    }

    @GetMapping("finds/{id}")
    public ResponseEntity<Post> getPostByIds(@PathVariable(name="id") Long id ){

        Post post1 = postService.getPostsById(id);



        ResponseEntity<Post> responseEntity = new ResponseEntity<>(post1,HttpStatus.OK);
        return responseEntity;
    }



    @PutMapping("/update-post/{id}")
    public  ResponseEntity<PostDto> updatePostById(@RequestBody PostDto postDto, @PathVariable(name = "id") Long id){
        PostDto postDto1 = postService.updatePost(postDto,id);
        ResponseEntity<PostDto> responseEntity = new ResponseEntity<>(postDto1,HttpStatus.OK);
        return responseEntity;
    }

    @DeleteMapping("/delete-post/{id}")
    public ResponseEntity<String> deletePost(@PathVariable(name = "id") Long id){

        postService.deletePost(id);
        ResponseEntity<String> responseEntity = new ResponseEntity<>("Post deleted successfully",HttpStatus.OK);
        return responseEntity;
    }



}
