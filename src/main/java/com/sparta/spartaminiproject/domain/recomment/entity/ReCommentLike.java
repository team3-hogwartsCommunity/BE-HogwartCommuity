package com.sparta.spartaminiproject.domain.recomment.entity;

import com.sparta.spartaminiproject.domain.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class ReCommentLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "comment_id", nullable = false)
    private ReComment reComment;

    @Column(nullable = false)
    private Boolean isShow;

    public ReCommentLike(User user, ReComment reComment) {
        this.user = user;
        this.reComment = reComment;
        this.isShow = true;
    }

    public void toggleLike() {
        isShow = !isShow;
    }
}
