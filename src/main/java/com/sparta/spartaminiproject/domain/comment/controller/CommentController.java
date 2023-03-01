package com.sparta.spartaminiproject.domain.comment.controller;

import com.sparta.spartaminiproject.common.dto.SendMessageDto;
import com.sparta.spartaminiproject.security.user.UserDetailsImpl;
import com.sparta.spartaminiproject.domain.comment.dto.CommentDto;
import com.sparta.spartaminiproject.domain.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/board/{boardId}/comment")
public class CommentController {

    private final CommentService commentService;

    // 댓글 작성
    @PostMapping
    public ResponseEntity<SendMessageDto> postComment(@PathVariable Long boardId, @RequestBody CommentDto.Request commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.writeComment(boardId, commentRequestDto, userDetails.getUser());
    }

    // 댓글 수정
    @PutMapping("/{id}")
    public ResponseEntity<SendMessageDto> putComment(@PathVariable Long boardId, @PathVariable Long id, @RequestBody CommentDto.Request commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.editComment(boardId, id, commentRequestDto, userDetails.getUser());
    }

    // 댓글 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<SendMessageDto> deleteComment(@PathVariable Long boardId, @PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.removeComment(boardId, id, userDetails.getUser());
    }

    // 댓글 좋아요
    @PostMapping("/{id}/like")
    public ResponseEntity<SendMessageDto> postCommentLike(@PathVariable Long boardId, @PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.toggleCommentLike(boardId, id, userDetails.getUser());
    }
}
