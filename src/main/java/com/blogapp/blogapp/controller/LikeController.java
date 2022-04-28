package com.blogapp.blogapp.controller;
import com.blogapp.blogapp.dto.PersonDto;
import com.blogapp.blogapp.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/blog/post")
public class LikeController {
    @Autowired
    private LikeService likeService;


    @PostMapping("/like/{postId}")
    public ResponseEntity<String> createLike(@PathVariable(value = "postId") Long postId, HttpSession session){
        PersonDto personDto = (PersonDto) session.getAttribute("person");
        if(personDto.getRole().equals("CUSTOMER")){
            String message = likeService.createLike(personDto.getId(), postId);
            return new ResponseEntity<>(message, HttpStatus.CREATED);
        }
        String message = "Cannot edit comment please login or register";
        return new ResponseEntity<>(message,HttpStatus.CREATED);

    }

    @DeleteMapping("/unlike/{likeId}")
    public ResponseEntity<String> unLike(@PathVariable(value = "likeId")Long likeId){
        likeService.unLike(likeId);
        ResponseEntity<String> responseEntity = new ResponseEntity<>("Unliked", HttpStatus.OK);
        return responseEntity;
    }
}
