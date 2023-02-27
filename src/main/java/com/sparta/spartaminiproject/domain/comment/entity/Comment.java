package com.sparta.spartaminiproject.domain.comment.entity;

import com.sparta.spartaminiproject.common.entity.Timestamped;
import com.sparta.spartaminiproject.domain.board.entity.Board;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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
    @JoinColumn(name = "board_id")
    Board board;

    public Comment(String contents, Board board) {
        this.contents = contents;
        this.board = board;
    }

    public void update(String contents) {
        this.contents = contents;
    }
}
