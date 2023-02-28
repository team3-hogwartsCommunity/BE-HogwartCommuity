package com.sparta.spartaminiproject.domain.comment.entity;

import com.sparta.spartaminiproject.domain.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class CommentLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "comment_id", nullable = false)
    private Comment comment;

    @Column(nullable = false)
    private Integer isShow;

    public CommentLike(User user, Comment comment) {
        this.user = user;
        this.comment = comment;
        this.isShow = 1;
    }

    public void toggleLike(Integer isShow) {
        this.isShow = isShow;
    }
}
