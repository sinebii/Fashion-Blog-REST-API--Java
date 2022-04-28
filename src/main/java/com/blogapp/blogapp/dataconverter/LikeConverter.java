package com.blogapp.blogapp.dataconverter;

import com.blogapp.blogapp.dto.LikesDto;
import com.blogapp.blogapp.entity.Likes;
import org.springframework.stereotype.Component;

@Component
public class LikeConverter {

    public Likes convertDtoToEntity(LikesDto likesDto){

        Likes likes = new Likes();
        likes.setId(likesDto.getId());
        return likes;
    }

    public LikesDto convertEntityToDto(Likes likes){
        LikesDto likesDto = new LikesDto();
        likesDto.setId(likes.getId());
        return likesDto;
    }
}
