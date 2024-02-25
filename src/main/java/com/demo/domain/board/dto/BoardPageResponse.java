package com.demo.domain.board.dto;

import com.demo.domain.board.model.BoardData;
import com.demo.global.dto.CommonPageResponseDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Schema(description = "게시글 리스트 및 페이징 정보")
public class BoardPageResponse extends CommonPageResponseDTO {
    @Schema(description = "게시글 리스트")
    private List<BoardData> boardData;

    @Builder
    public BoardPageResponse(int totalPage, Long totalCount, int pageNumber, int pageSize, List<BoardData> boardData) {
        super(totalPage, totalCount, pageNumber, pageSize);
        this.boardData = boardData;
    }
}