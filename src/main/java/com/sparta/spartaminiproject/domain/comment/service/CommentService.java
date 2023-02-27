package com.sparta.spartaminiproject.domain.comment.service;

import com.sparta.spartaminiproject.domain.board.entity.Board;
import com.sparta.spartaminiproject.domain.board.repository.BoardRepository;
import com.sparta.spartaminiproject.domain.comment.dto.CommentDto;
import com.sparta.spartaminiproject.domain.comment.entity.Comment;
import com.sparta.spartaminiproject.domain.comment.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    // 댓글 작성
    public void writeComment(Long boardId, CommentDto.Request commentRequestDto) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

        commentRepository.save(new Comment(commentRequestDto.getContents(), board));
    }

    // 댓글 수정
    public void editComment(Long boardId, Long id, CommentDto.Request commentRequestDto) {
        boardRepository.findById(boardId).orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

        // board[boardId]에 달린 comment[id]가 존재하는지
        Comment comment = commentRepository.findByBoardIdAndId(boardId, id).orElseThrow(() -> new IllegalArgumentException("게시글에 달린 댓글이 존재하지 않습니다."));
        comment.update(commentRequestDto.getContents());
    }

    // 댓글 삭제
    public void removeComment(Long boardId, Long id) {
        boardRepository.findById(boardId).orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

        // board[boardId]에 달린 comment[id]가 존재하는지
        commentRepository.findByBoardIdAndId(boardId, id).orElseThrow(() -> new IllegalArgumentException("게시글에 달린 댓글이 존재하지 않습니다."));
        commentRepository.deleteById(id);
    }
}
