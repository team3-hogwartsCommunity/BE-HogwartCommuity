package com.sparta.spartaminiproject.domain.comment.service;

import com.sparta.spartaminiproject.common.dto.SendMessageDto;
import com.sparta.spartaminiproject.common.utill.SuccessCode;
import com.sparta.spartaminiproject.domain.board.entity.Board;
import com.sparta.spartaminiproject.domain.board.repository.BoardRepository;
import com.sparta.spartaminiproject.domain.comment.dto.CommentDto;
import com.sparta.spartaminiproject.domain.comment.entity.Comment;
import com.sparta.spartaminiproject.domain.comment.entity.CommentLike;
import com.sparta.spartaminiproject.domain.comment.repository.CommentLikeRepository;
import com.sparta.spartaminiproject.domain.comment.repository.CommentRepository;
import com.sparta.spartaminiproject.domain.user.entity.User;
import com.sparta.spartaminiproject.exception.CustomException;
import com.sparta.spartaminiproject.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<SendMessageDto> writeComment(Long boardId, CommentDto.Request commentRequestDto, User user) {
        Board board = boardRepository.findById(boardId).orElseThrow(() -> new CustomException(ErrorCode.NULL_BOARD_DATA));

        commentRepository.save(new Comment(commentRequestDto.getContents(), board, user));

        return ResponseEntity.ok()
                .body(SendMessageDto.of(SuccessCode.COMMENT_POST_SUCCESS));
    }

    // 댓글 수정
    public ResponseEntity<SendMessageDto> editComment(Long boardId, Long id, CommentDto.Request commentRequestDto, User user) {
        boardRepository.findById(boardId).orElseThrow(() -> new CustomException(ErrorCode.NULL_BOARD_DATA));

        Comment comment = commentRepository.findByBoardIdAndId(boardId, id).orElseThrow(() -> new CustomException(ErrorCode.NULL_COMMENT_DATA));

        if (!Objects.equals(user.getId(), comment.getUser().getId())) {
            throw new CustomException(ErrorCode.NOT_AUTHOR);
        }

        comment.update(commentRequestDto.getContents());

        return ResponseEntity.ok()
                .body(SendMessageDto.of(SuccessCode.COMMENT_PUT_SUCCESS));
    }

    // 댓글 삭제
    public ResponseEntity<SendMessageDto> removeComment(Long boardId, Long id, User user) {
        boardRepository.findById(boardId).orElseThrow(() -> new CustomException(ErrorCode.NULL_BOARD_DATA));

        Comment comment = commentRepository.findByBoardIdAndId(boardId, id).orElseThrow(() -> new CustomException(ErrorCode.NULL_COMMENT_DATA));

        if (!Objects.equals(user.getId(), comment.getUser().getId())) {
            throw new CustomException(ErrorCode.NOT_AUTHOR);
        }

        commentRepository.deleteById(id);
        return ResponseEntity.ok()
                .body(SendMessageDto.of(SuccessCode.COMMENT_DELETE_SUCCESS));
    }

    // 댓글 좋아요
    public ResponseEntity<SendMessageDto> toggleCommentLike(Long boardId, Long id, User user) {
        boardRepository.findById(boardId).orElseThrow(() -> new CustomException(ErrorCode.NULL_BOARD_DATA));

        Comment comment = commentRepository.findByBoardIdAndId(boardId, id).orElseThrow(() -> new CustomException(ErrorCode.NULL_COMMENT_DATA));

        Optional<CommentLike> commentLikeOptional = commentLikeRepository.findByCommentIdAndUserId(id, user.getId());
        if (commentLikeOptional.isPresent()) {
            CommentLike commentLike = commentLikeOptional.get();
            if (commentLike.getIsShow()) {
                commentLike.toggleLike();
                return ResponseEntity.ok()
                        .body(SendMessageDto.of(SuccessCode.NOT_LIKE_SUCCESS));
            }
            commentLike.toggleLike();
        } else {
            commentLikeRepository.save(new CommentLike(user, comment));
        }

        return ResponseEntity.ok()
                .body(SendMessageDto.of(SuccessCode.LIKE_SUCCESS));
    }
}
