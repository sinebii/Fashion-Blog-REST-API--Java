package com.blogapp.blogapp.dataconverter;

import com.blogapp.blogapp.dto.PostDto;
import com.blogapp.blogapp.entity.Post;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PostConverter {
    @Autowired
    private ModelMapper mapper;

    public Post convertDtoToEntity(PostDto postDto){

        Post post = mapper.map(postDto,Post.class);
//        Post post = new Post();
//        post.setName(postDto.getName());
//        post.setDescription(postDto.getDescription());
//        post.setPrice(postDto.getPrice());

        return post;
    }

    public PostDto convertEntityToDto(Post post){

        PostDto postDto = mapper.map(post, PostDto.class);
//        PostDto postDtoResponse = new PostDto();
//        postDtoResponse.setId(post.getId());
//        postDtoResponse.setName(post.getName());
//        postDtoResponse.setDescription(post.getDescription());
//        postDtoResponse.setPrice(post.getPrice());

        return postDto;
    }
}
