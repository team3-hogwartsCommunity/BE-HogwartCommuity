package com.sparta.spartaminiproject.domain.board.service;

import com.sparta.spartaminiproject.common.dto.SendMessageDto;
import com.sparta.spartaminiproject.common.utill.SuccessCode;
import com.sparta.spartaminiproject.domain.board.dto.BoardRequestDto;
import com.sparta.spartaminiproject.domain.board.dto.BoardResponseDto;
import com.sparta.spartaminiproject.domain.board.entity.Board;
import com.sparta.spartaminiproject.domain.board.entity.BoardLike;
import com.sparta.spartaminiproject.domain.board.repository.BoardLikeRepository;
import com.sparta.spartaminiproject.domain.board.repository.BoardRepository;
import com.sparta.spartaminiproject.domain.comment.dto.CommentDto;
import com.sparta.spartaminiproject.domain.comment.entity.Comment;
import com.sparta.spartaminiproject.domain.comment.repository.CommentLikeRepository;
import com.sparta.spartaminiproject.domain.comment.repository.CommentRepository;
import com.sparta.spartaminiproject.domain.recomment.dto.ReCommentDto;
import com.sparta.spartaminiproject.domain.recomment.entity.ReComment;
import com.sparta.spartaminiproject.domain.recomment.repository.ReCommentRepository;
import com.sparta.spartaminiproject.domain.user.entity.User;
import com.sparta.spartaminiproject.common.utill.UserDormitory;
import com.sparta.spartaminiproject.exception.CustomException;
import com.sparta.spartaminiproject.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;
    private final BoardLikeRepository boardLikeRepository;
    private final CommentLikeRepository commentLikeRepository;
    private final ReCommentRepository reCommentRepository;

    // 기숙사로 게시글 리스트 조회
    @Transactional(readOnly = true)
    public BoardResponseDto.BoardListWithTotalCount showBoardListFilterDormitory(UserDormitory dormitory, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));

        Page<Board> boardPaging = boardRepository.findAllByDormitory(dormitory, pageable);
        long boardTotalCount = boardPaging.getTotalElements();
        List<Board> boardList = boardPaging.getContent();   // getContent()로 List로 반환 받을 수 있음

//        if (boardList.size() == 0) {
//            throw new NullPointerException("아직 게시글이 존재하지 않습니다.");
//        }

        List<BoardResponseDto.BoardList> boardDtoList = new ArrayList<>(boardList.size());
        for (Board board : boardList) {
            boardDtoList.add(new BoardResponseDto.BoardList(board, boardLikeRepository.countByBoardIdAndIsShow(board.getId(), true)));
        }

        return new BoardResponseDto.BoardListWithTotalCount(boardTotalCount, boardDtoList);
    }

    // 게시글 하나 조회
    @Transactional(readOnly = true)
    public BoardResponseDto.OneBoard showBoard(Long id) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.NULL_BOARD_DATA));

        List<Comment> commentList = commentRepository.findAllByBoardId(id);
        if (commentList.size() == 0) {
            return new BoardResponseDto.OneBoard(board, boardLikeRepository.countByBoardIdAndIsShow(id, true), new ArrayList<>());
        }

        List<CommentDto.Response> commentResponseDtoList = new ArrayList<>(commentList.size());

        for (Comment comment : commentList) {
            List<ReComment> reComments = reCommentRepository.findAllByCommentId(comment.getId());
            List<ReCommentDto.Response> reCommentDtoList = new ArrayList<>(comment.getReCommentList().size());

            for (ReComment reComment : reComments) {
                reCommentDtoList.add(new ReCommentDto.Response(reComment, 0L));
            }
            commentResponseDtoList.add(new CommentDto.Response(comment, commentLikeRepository.countByCommentIdAndIsShow(comment.getId(), true), reCommentDtoList));
        }

        return new BoardResponseDto.OneBoard(board, boardLikeRepository.countByBoardIdAndIsShow(id, true), commentResponseDtoList);
    }

    // 게시글 작성
    @Transactional
    public ResponseEntity<SendMessageDto> writeBoard(BoardRequestDto.Write boardWriteRequestDto, User user) {
        if (user.getDormitory() != boardWriteRequestDto.getDormitory()) {
            throw new CustomException(ErrorCode.PERMISSION_DINED);
        }

        boardRepository.save(new Board(boardWriteRequestDto, user));
        return ResponseEntity.ok()
                .body(SendMessageDto.of(SuccessCode.BLOG_POST_SUCCESS));
    }

    // 게시글 수정
    @Transactional
    public ResponseEntity<SendMessageDto> editBoard(Long id, BoardRequestDto.Edit boardEditRequestDto, User user) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.NULL_BOARD_DATA));

        if (!Objects.equals(user.getId(), board.getUser().getId())) {
            throw new CustomException(ErrorCode.NOT_AUTHOR);
        }

        board.update(boardEditRequestDto);
        return ResponseEntity.ok()
                .body(SendMessageDto.of(SuccessCode.BLOG_PUT_SUCCESS));
    }

    // 게시글 삭제
    @Transactional
    public ResponseEntity<SendMessageDto> removeBoard(Long id, User user) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.NULL_BOARD_DATA));

        if (!Objects.equals(user.getId(), board.getUser().getId())) {
            throw new CustomException(ErrorCode.NOT_AUTHOR);
        }

        boardRepository.deleteById(id);
        return ResponseEntity.ok()
                .body(SendMessageDto.of(SuccessCode.BLOG_DELETE_SUCCESS));
    }

    // 게시글 좋아요
    @Transactional
    public ResponseEntity<SendMessageDto> toggleBoardLike(Long id, User user) {
        Board board = boardRepository.findById(id).orElseThrow(() -> new CustomException(ErrorCode.NULL_BOARD_DATA));

        Optional<BoardLike> boardLikeOptional = boardLikeRepository.findByBoardIdAndUserId(id, user.getId());
        if (boardLikeOptional.isPresent()) {
            BoardLike boardLike = boardLikeOptional.get();
            if (boardLike.getIsShow()) {
                boardLike.toggleLike();
                return ResponseEntity.ok()
                        .body(SendMessageDto.of(SuccessCode.NOT_LIKE_SUCCESS));
            }
            boardLike.toggleLike();
        } else {
            boardLikeRepository.save(new BoardLike(user, board));
        }

        return ResponseEntity.ok()
                .body(SendMessageDto.of(SuccessCode.LIKE_SUCCESS));
    }
}
