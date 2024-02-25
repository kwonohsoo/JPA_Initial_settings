package com.demo.domain.board.model;

import com.demo.global.entity.common.CommonInfo;
import com.querydsl.core.annotations.QueryProjection;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Schema(description = "게시글 관리 정보")
public class BoardData extends CommonInfo {

    @Schema(description = "게시글 번호")
    private Long id;

    @Schema(description = "제목")
    private String title;

    @Schema(description = "내용")
    private String content;

    @Schema(description = "생성일", example = "YYYY-MM-dd HH:mm:ss")
    private LocalDateTime createdDt;

    @Schema(description = "수정일", example = "YYYY-MM-dd HH:mm:ss")
    private LocalDateTime modifiedDt;

    @QueryProjection
    public BoardData(Long id, String title, String content, LocalDateTime createdDt, LocalDateTime modifiedDt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createdDt = createdDt;
        this.modifiedDt = modifiedDt;
    }
}
