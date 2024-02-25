package com.demo.domain.board.service;

import com.demo.domain.board.dto.BoardPageRequest;
import com.demo.domain.board.dto.BoardPageResponse;
import com.demo.domain.board.dto.BoardRequest;
import com.demo.domain.board.dto.BoardResponse;
import com.demo.domain.board.entity.Board;
import com.demo.domain.board.model.BoardData;
import com.demo.domain.board.repository.BoardQueryDslRepository;
import com.demo.domain.board.repository.BoardRepository;
import com.demo.global.error.code.CommonErrorCode;
import com.demo.global.error.exception.InvalidValueException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final BoardQueryDslRepository boardQueryDslRepository;

    // 게시글 작성
    @Transactional
    public BoardResponse create(BoardRequest boardReq) {

        Board board = boardReq.toEntity();

        board.setCreatedDt(LocalDateTime.now());

        boardRepository.save(board);

        return BoardResponse.builder()
                .id(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .createdDt(board.getCreatedDt())
                .build();
    }

    // 게시글 전체 조회
    public List<BoardResponse> getAll() {
        List<Board> boardList = boardQueryDslRepository.findAllBoards();
        return boardList.stream()
                .map(board -> BoardResponse.builder()
                        .id(board.getId())
                        .title(board.getTitle())
                        .content(board.getContent())
                        .createdDt(board.getCreatedDt())
                        .modifiedDt(board.getModifiedDt())
                        .build())
                .collect(Collectors.toList());
    }

    // 게시판 페이징
    public BoardPageResponse boardDataPaging(BoardPageRequest request) {
        Page<BoardData> boardData = boardQueryDslRepository.boardDataPaging(request);

        return BoardPageResponse.builder()
                .totalPage(boardData.getTotalPages())
                .totalCount(boardData.getTotalElements())
                .pageNumber(boardData.getPageable().getPageNumber())
                .pageSize(boardData.getPageable().getPageSize())
                .boardData(boardData.getContent())
                .build();
    }

    // 게시글 번호별 조회(상세조회)
    public BoardResponse findBoardById(Long id) {
        Optional<Board> boardOptional = Optional.ofNullable(boardQueryDslRepository.findBoardById(id));

        Board board = boardOptional.orElseThrow(() -> new InvalidValueException(CommonErrorCode.POST_NOT_FOUND));

        return BoardResponse.builder()
                .id(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .createdDt(board.getCreatedDt())
                .modifiedDt(board.getModifiedDt())
                .build();
    }

    // 게시글 수정
    @Transactional
    public long updateBoard(Long id, BoardRequest request) {
        if (!boardRepository.existsById(id)) {
            throw new InvalidValueException(CommonErrorCode.POST_NOT_FOUND);
        }
        return boardQueryDslRepository.updateBoard(id, request);
    }

    // 게시글 완전 삭제
    @Transactional
    public void deleteBoard(Long id) {
        if (!boardRepository.existsById(id)) {
            throw new InvalidValueException(CommonErrorCode.POST_NOT_FOUND);
        }
        boardQueryDslRepository.deleteBoard(id);
    }

    // 삭제 시 UseYn 0(사용) -> 9(사용 안함) 변경 (데이터 삭제 X)
    @Transactional
    public void changeUseYnWhenDeleted(Long id) {
        if (!boardRepository.existsById(id)) {
            throw new InvalidValueException(CommonErrorCode.POST_NOT_FOUND);
        }

        Optional<Board> board = boardRepository.findById(id);

        if (board.isPresent() && board.get().getUseYn() == 9) {
            throw new InvalidValueException(CommonErrorCode.POST_NOT_FOUND);
        }

        boardQueryDslRepository.changeUseYnWhenDeleted(id);
    }
}
