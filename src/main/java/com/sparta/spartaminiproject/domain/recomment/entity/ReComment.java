package com.sparta.spartaminiproject.domain.recomment.entity;

import com.sparta.spartaminiproject.common.entity.Timestamped;
import com.sparta.spartaminiproject.domain.comment.entity.Comment;
import com.sparta.spartaminiproject.domain.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class ReComment extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String contents;
    @ManyToOne
    @JoinColumn(name = "comment_id", nullable = false)
    private Comment comment;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public ReComment(String contents, Comment comment, User user) {
        this.contents = contents;
        this.comment = comment;
        this.user = user;
    }

    public void update(String contents){
        this.contents = contents;
    }
}
