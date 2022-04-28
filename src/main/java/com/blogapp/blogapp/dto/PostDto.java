package com.blogapp.blogapp.dto;

import com.blogapp.blogapp.entity.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private long likes;
    private List<Comment> commentSet = new ArrayList<>();
}
