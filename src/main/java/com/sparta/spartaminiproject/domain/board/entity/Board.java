package com.sparta.spartaminiproject.domain.board.entity;

import com.sparta.spartaminiproject.common.entity.Timestamped;
import com.sparta.spartaminiproject.domain.board.dto.BoardRequestDto;
import com.sparta.spartaminiproject.domain.comment.entity.Comment;
import com.sparta.spartaminiproject.domain.user.entity.User;
import com.sparta.spartaminiproject.common.utill.UserDormitory;
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

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<Comment> commentList;

    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    private List<BoardLike> boardLikes;

    public Board(BoardRequestDto.Write boardRequestDto, User user) {
        this.title = boardRequestDto.getTitle();
        this.contents = boardRequestDto.getContents();
        this.dormitory = boardRequestDto.getDormitory();
        this.user = user;
    }

    public void update(BoardRequestDto.Edit boardRequestDto) {
        title = boardRequestDto.getTitle();
        contents = boardRequestDto.getContents();
    }
}
