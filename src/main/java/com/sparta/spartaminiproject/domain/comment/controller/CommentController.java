package com.sparta.spartaminiproject.domain.comment.controller;

import com.sparta.spartaminiproject.common.security.user.UserDetailsImpl;
import com.sparta.spartaminiproject.domain.comment.dto.CommentDto;
import com.sparta.spartaminiproject.domain.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/board/{boardId}/comment")
public class CommentController {

    private final CommentService commentService;

    // 댓글 작성
    @PostMapping
    public String postComment(@PathVariable Long boardId, @RequestBody CommentDto.Request commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        commentService.writeComment(boardId, commentRequestDto, userDetails.getUser());
        return "작성 성공";
    }

    // 댓글 수정
    @PutMapping("/{id}")
    public String putComment(@PathVariable Long boardId, @PathVariable Long id, @RequestBody CommentDto.Request commentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        commentService.editComment(boardId, id, commentRequestDto, userDetails.getUser());
        return "수정 성공";
    }

    // 댓글 삭제
    @DeleteMapping("/{id}")
    public String deleteComment(@PathVariable Long boardId, @PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        commentService.removeComment(boardId, id, userDetails.getUser());
        return "삭제 성공";
    }

    // 댓글 좋아요
    @PostMapping("/{id}/like")
    public String postCommentLike(@PathVariable Long boardId, @PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return commentService.toggleCommentLike(boardId, id, userDetails.getUser());
    }
}
