package com.sparta.spartaminiproject.domain.board.entity;

import com.sparta.spartaminiproject.common.entity.Timestamped;
import com.sparta.spartaminiproject.domain.board.dto.BoardRequestDto;
import com.sparta.spartaminiproject.domain.comment.entity.Comment;
import com.sparta.spartaminiproject.domain.user.entity.UserDormitory;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Board extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String contents;

    @Column(nullable = false)
    private UserDormitory dormitory;

    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    List<Comment> commentList;

    public Board(BoardRequestDto.Write boardRequestDto) {
        this.title = boardRequestDto.getTitle();
        this.contents = boardRequestDto.getContents();
        this.dormitory = boardRequestDto.getDormitory();
    }

    public void update(BoardRequestDto.Edit boardRequestDto) {
        title = boardRequestDto.getTitle();
        contents = boardRequestDto.getContents();
    }
}
