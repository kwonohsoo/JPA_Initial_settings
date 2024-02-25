package com.demo.domain.board.controller;

import com.demo.domain.board.dto.*;
import com.demo.domain.board.service.BoardService;
import com.demo.global.dto.ResponseService;
import com.demo.global.dto.SingleResult;
import com.demo.global.message.CommonMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
@Tag(name = "게시판", description = "swagger Test 게시판")
public class BoardController {

    private final ResponseService responseService;
    private final BoardService boardService;

    @PostMapping(value = "/create")
    @Operation(summary = "등록", description = "swagger Test 게시글 등록")
    public SingleResult<BoardResponse> create(@RequestBody BoardRequest boardRequest) {
        return responseService.getSingleResult(boardService.create(boardRequest), CommonMessage.CREATE_SUCCESS);
    }

    @PostMapping("/view/read")
    @Operation(summary = "전체 조회", description = "swagger Test 게시판 전체 조회")
    public SingleResult<List<BoardResponse>> getAll() {
        return responseService.getSingleResult(boardService.getAll(), CommonMessage.SELECT_SUCCESS);
    }

    @PostMapping("/view/page")
    @Operation(summary = "페이지별 조회", description = "Swagger Test 게시판 페이지 조회")
    public SingleResult<BoardPageResponse> boardDataPaging(@RequestBody BoardPageRequest request) {
        BoardPageResponse response = boardService.boardDataPaging(request);
        return responseService.getSingleResult(response, CommonMessage.SELECT_SUCCESS);
    }

    @PostMapping("/view/read/{id}")
    @Operation(summary = "게시글 번호별 조회", description = "swagger Test 게시판 번호별 조회")
    public SingleResult<BoardResponse> findBoardById(@PathVariable("id") Long id) {
        return responseService.getSingleResult(boardService.findBoardById(id), CommonMessage.SELECT_SUCCESS);
    }

    @PostMapping("/update/{id}")
    @Operation(summary = "수정", description = "swagger Test 게시글 수정")
    public SingleResult<Long> update(@PathVariable("id") Long id, @RequestBody BoardRequest boardRequest) {
        return responseService.getSingleResult(boardService.updateBoard(id, boardRequest), CommonMessage.UPDATE_SUCCESS);
    }

    @PostMapping("/delete/{id}")
    @Operation(summary = "완전 삭제(Completely delete)", description = "Swagger Test 게시글 완전 삭제")
    public SingleResult<String> deleteBoard(@PathVariable("id") Long id) {
        boardService.deleteBoard(id);
        return responseService.getSingleResult(CommonMessage.DELETE_SUCCESS);
    }

    @PostMapping("/delete/UseYnChange/{id}")
    @Operation(summary = "삭제(Data left behind)", description = "swagger Test 게시글 삭제(삭제 시 UseYn: 0(사용) -> 9(사용 안함)로 변경, 데이터 남겨둠)")
    public SingleResult<String> changeUseYnWhenDeleted(@PathVariable("id") Long id) {
        boardService.changeUseYnWhenDeleted(id);
        return responseService.getSingleResult(CommonMessage.DELETE_SUCCESS);
    }
}

