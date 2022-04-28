package com.blogapp.blogapp.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
//    @Column(nullable = false)
//    private String name;
//    @Column(nullable = false)
//    private String email;
    @Column(nullable = false)
    private String message;

//    @ManyToOne(cascade = CascadeType.ALL)
//    @JoinColumn(name = "post_id",nullable = false)
//    private Post post;
//
//    @OneToOne(cascade = CascadeType.ALL)
//    private Person person;

    private Long postId;
    private Long personId;

}
