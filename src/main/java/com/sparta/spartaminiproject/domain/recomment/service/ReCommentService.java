package com.sparta.spartaminiproject.domain.recomment.service;

import com.sparta.spartaminiproject.domain.comment.entity.Comment;
import com.sparta.spartaminiproject.domain.comment.repository.CommentRepository;
import com.sparta.spartaminiproject.domain.recomment.dto.ReCommentDto;
import com.sparta.spartaminiproject.domain.recomment.entity.ReComment;
//import com.sparta.spartartaminiproject.domain.recomment.repository.ReCommentLikeRepository;
import com.sparta.spartaminiproject.domain.recomment.entity.ReCommentLike;
import com.sparta.spartaminiproject.domain.recomment.repository.ReCommentLikeRepository;
import com.sparta.spartaminiproject.domain.recomment.repository.ReCommentRepository;
import com.sparta.spartaminiproject.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class ReCommentService {
    private final ReCommentRepository reCommentRepository;
    private final CommentRepository commentRepository;
    private final ReCommentLikeRepository reCommentLikeRepository;

    @Transactional
    public void writeReComment(Long commentId, ReCommentDto.Request reCommentRequestDto, User user){
        // 대댓글을 작성할 댓글이 존재하는지
        Comment comment = commentRepository.findById(commentId).orElseThrow(() -> new IllegalArgumentException("댓글이 존재하지 않습니다."));

        reCommentRepository.save(new ReComment(reCommentRequestDto.getContents(), comment, user));
    }

    @Transactional
    public void editReComment(Long commentId, Long id, ReCommentDto.Request reCommentRequestDto,User user) {
        commentRepository.findById(commentId).orElseThrow(() -> new IllegalArgumentException("댓글이 존재하지 않습니다."));

        ReComment reComment = reCommentRepository.findByCommentIdAndId(commentId, id).orElseThrow(() -> new IllegalArgumentException("댓글에 달린 대댓글이 존재하지 않습니다."));
        if (user.getId() != reComment.getUser().getId()) {
            throw new IllegalArgumentException("해당 대댓글의 작성자가 아닙니다.");
        }else
        reComment.update(reCommentRequestDto.getContents());
    }

    @Transactional
    public void removeReComment(Long commentId, Long id, User user) {
        commentRepository.findById(commentId).orElseThrow(() -> new IllegalArgumentException("댓글이 존재하지 않습니다."));

        ReComment reComment = reCommentRepository.findByCommentIdAndId(commentId, id).orElseThrow(() -> new IllegalArgumentException("댓글에 달린 대댓글이 존재하지 않습니다."));
        if (user.getId() != reComment.getUser().getId()) {
            throw new IllegalArgumentException("해당 대댓글의 작성자가 아닙니다.");
        }else reCommentRepository.deleteById(id);

    }

    @Transactional
    public String toggleReCommentLike(Long commentId, Long id, User user) {
        commentRepository.findById(commentId).orElseThrow(() -> new IllegalArgumentException("댓글이 존재하지 않습니다."));
        ReComment reComment = reCommentRepository.findByCommentIdAndId(commentId, id).orElseThrow(() -> new IllegalArgumentException("댓글에 달린 대댓글이 존재하지 않습니다."));
        Optional<ReCommentLike> reCommentLikeOptional = reCommentLikeRepository.findByReCommentIdAndUserId(id, user.getId());
        if (reCommentLikeOptional.isPresent()) {
            ReCommentLike recommentLike = reCommentLikeOptional.get();
            if (recommentLike.getIsShow() == 1) {
                recommentLike.toggleLike(0);
                return "안 좋아요";
            } else {
                recommentLike.toggleLike(1);
            }
        } else {
            reCommentLikeRepository.save(new ReCommentLike(user, reComment));
        }
        return "좋아요";
    }
}