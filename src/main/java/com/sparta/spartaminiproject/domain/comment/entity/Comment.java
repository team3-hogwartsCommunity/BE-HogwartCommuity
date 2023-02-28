package com.sparta.spartaminiproject.domain.comment.entity;

import com.sparta.spartaminiproject.common.entity.Timestamped;
import com.sparta.spartaminiproject.domain.board.entity.Board;
import com.sparta.spartaminiproject.domain.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Comment extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String contents;

    @ManyToOne
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "comment", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<CommentLike> commentLikeList;

    public Comment(String contents, Board board, User user) {
        this.contents = contents;
        this.board = board;
        this.user = user;
    }

    public void update(String contents) {
        this.contents = contents;
    }
}
