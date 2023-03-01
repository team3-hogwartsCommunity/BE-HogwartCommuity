package com.sparta.spartaminiproject.domain.comment.service;

import com.sparta.spartaminiproject.domain.board.entity.Board;
import com.sparta.spartaminiproject.domain.board.repository.BoardRepository;
import com.sparta.spartaminiproject.domain.comment.dto.CommentDto;
import com.sparta.spartaminiproject.domain.comment.entity.Comment;
import com.sparta.spartaminiproject.domain.comment.entity.CommentLike;
import com.sparta.spartaminiproject.domain.comment.repository.CommentLikeRepository;
import com.sparta.spartaminiproject.domain.comment.repository.CommentRepository;
import com.sparta.spartaminiproject.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;
    private final CommentLikeRepository commentLikeRepository;

    // 댓글 작성
    public void writeComment(Long boardId, CommentDto.Request commentRequestDto, User user) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

        commentRepository.save(new Comment(commentRequestDto.getContents(), board, user));
    }

    // 댓글 수정
    public void editComment(Long boardId, Long id, CommentDto.Request commentRequestDto, User user) {
        boardRepository.findById(boardId).orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

        Comment comment = commentRepository.findByBoardIdAndId(boardId, id).orElseThrow(() -> new IllegalArgumentException("게시글에 달린 댓글이 존재하지 않습니다."));

        if (!Objects.equals(user.getId(), comment.getUser().getId())) {
            throw new IllegalArgumentException("댓글을 작성한 회원이 아닙니다.");
        }

        comment.update(commentRequestDto.getContents());
    }

    // 댓글 삭제
    public void removeComment(Long boardId, Long id, User user) {
        boardRepository.findById(boardId).orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

        Comment comment = commentRepository.findByBoardIdAndId(boardId, id).orElseThrow(() -> new IllegalArgumentException("게시글에 달린 댓글이 존재하지 않습니다."));

        if (!Objects.equals(user.getId(), comment.getUser().getId())) {
            throw new IllegalArgumentException("댓글을 작성한 회원이 아닙니다.");
        }

        commentRepository.deleteById(id);
    }

    // 댓글 좋아요
    public String toggleCommentLike(Long boardId, Long id, User user) {
        boardRepository.findById(boardId).orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

        Comment comment = commentRepository.findByBoardIdAndId(boardId, id).orElseThrow(() -> new IllegalArgumentException("게시글에 달린 댓글이 존재하지 않습니다."));

        Optional<CommentLike> commentLikeOptional = commentLikeRepository.findByCommentIdAndUserId(id, user.getId());
        if (commentLikeOptional.isPresent()) {
            CommentLike commentLike = commentLikeOptional.get();
            if (commentLike.getIsShow() == 1) {
                commentLike.toggleLike(0);
                return "안 좋아요";
            } else {
                commentLike.toggleLike(1);
            }
        } else {
            commentLikeRepository.save(new CommentLike(user, comment));
        }

        return "좋아요";
    }
}
