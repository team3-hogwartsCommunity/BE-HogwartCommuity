package com.sparta.spartaminiproject.domain.recomment.controller;

import com.sparta.spartaminiproject.common.security.user.UserDetailsImpl;
import com.sparta.spartaminiproject.domain.recomment.dto.ReCommentDto;
import com.sparta.spartaminiproject.domain.recomment.service.ReCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comment/{commentId}/reComment")
public class ReCommentController {

    private final ReCommentService reCommentService;

    // 대댓글 작성
    @PostMapping
    public String postReComment(@PathVariable Long commentId, @RequestBody ReCommentDto.Request reCommentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        reCommentService.writeReComment(commentId, reCommentRequestDto, userDetails.getUser());
        return "작성 성공";
    }

    // 대댓글 수정
    @PutMapping("/{id}")
    public String putReComment(@PathVariable Long commentId, @PathVariable Long id, @RequestBody ReCommentDto.Request reCommentRequestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        reCommentService.editReComment(commentId, id, reCommentRequestDto, userDetails.getUser());
        return "수정 성공";
    }

    // 대댓글 삭제
    @DeleteMapping("/{id}")
    public String deleteReComment(@PathVariable Long commentId, @PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        reCommentService.removeReComment(commentId, id, userDetails.getUser());
        return "삭제 성공";
    }

    // 대댓글 좋아요
    @PostMapping("/{id}/like")
    public String postReCommentLike(@PathVariable Long commentId, @PathVariable Long id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return reCommentService.toggleReCommentLike(commentId, id, userDetails.getUser());
    }
}

