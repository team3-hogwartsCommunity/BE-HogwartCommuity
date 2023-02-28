package com.sparta.spartaminiproject.domain.board.entity;

import com.sparta.spartaminiproject.domain.user.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class BoardLike {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "board_id", nullable = false)
    private Board board;

    @Column(nullable = false)
    private Integer isShow;

    public BoardLike(User user, Board board) {
        this.user = user;
        this.board = board;
        isShow = 1;
    }

    public void toggleLike(Integer isLike) {
        this.isShow = 0;
    }
}
