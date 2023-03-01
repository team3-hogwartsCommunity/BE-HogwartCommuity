package com.sparta.spartaminiproject.domain.recomment.controller;

import com.sparta.spartaminiproject.common.dto.SendMessageDto;
import com.sparta.spartaminiproject.security.user.UserDetailsImpl;
import com.sparta.spartaminiproject.domain.recomment.dto.ReCommentDto;
import com.sparta.spartaminiproject.domain.recomment.service.ReCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comment/{commentId}/reComment")
public class ReCommentController {

    private final ReCommentService reCommentService;

    // 대댓글 작성
    @PostMapping
    public ResponseEntity<SendMessageDto> postReComment(@PathVariable Long commentId, @RequestBody ReCommentDto.Request reCommentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return reCommentService.writeReComment(commentId, reCommentRequestDto, userDetails.getUser());
    }

    // 대댓글 수정
    @PutMapping("/{id}")
    public ResponseEntity<SendMessageDto> putReComment(@PathVariable Long commentId, @PathVariable Long id, @RequestBody ReCommentDto.Request reCommentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return reCommentService.editReComment(commentId, id, reCommentRequestDto, userDetails.getUser());
    }

    // 대댓글 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<SendMessageDto> deleteReComment(@PathVariable Long commentId, @PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return reCommentService.removeReComment(commentId, id, userDetails.getUser());
    }

    // 대댓글 좋아요
    @PostMapping("/{id}/like")
    public ResponseEntity<SendMessageDto> postReCommentLike(@PathVariable Long commentId, @PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return reCommentService.toggleReCommentLike(commentId, id, userDetails.getUser());
    }
}

