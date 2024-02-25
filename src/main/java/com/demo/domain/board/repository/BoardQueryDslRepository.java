package com.demo.domain.board.repository;

import com.demo.domain.board.dto.BoardPageRequest;
import com.demo.domain.board.dto.BoardRequest;
import com.demo.domain.board.entity.Board;
import com.demo.domain.board.entity.QBoard;
import com.demo.domain.board.model.BoardData;
import com.demo.domain.board.model.QBoardData;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

import static com.demo.domain.board.entity.QBoard.board;

@RequiredArgsConstructor
@Repository
public class BoardQueryDslRepository {

    private final JPAQueryFactory queryFactory;

    // 게시글 전체 조회
    public List<Board> findAllBoards() {
        return queryFactory
                .selectFrom(QBoard.board)
                .fetch();
    }


    // 게시글 번호별 조회(상세조회)
    public Board findBoardById(Long id) {

        QBoard board = QBoard.board;

        return queryFactory
                .selectFrom(board)
                .where(board.id.eq(id))
                .fetchOne();

    }

    // 게시판 페이징
    public Page<BoardData> boardDataPaging(BoardPageRequest request) {
        Pageable pageable = PageRequest.of(request.getPageNumber(), request.getPageSize());

        List<BoardData> boardData = queryFactory
                .select(new QBoardData(board.id, board.title, board.content, board.createdDt, board.modifiedDt))
                .from(board)
                .where(containSearch(request.getSearch()))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        Long countQuery = queryFactory
                .select(board.count())
                .from(board)
                .where(containSearch(request.getSearch()))
                .fetchFirst();

        return PageableExecutionUtils.getPage(boardData, pageable, () -> countQuery);
    }

    // 페이징 시 검색어 포함 조회
    private BooleanExpression containSearch(String search) {
        if (StringUtils.hasText(search)) {
            return board.id.stringValue().contains(search)
                    .or(board.title.contains(search))
                    .or(board.content.contains(search));
        }
        return Expressions.TRUE;
    }

    // 게시글 수정
    public long updateBoard(Long id, BoardRequest request) {
        return queryFactory
                .update(board)
                .set(board.title, request.getTitle())
                .set(board.content, request.getContent())
                .set(board.modifiedDt, Expressions.constant(LocalDateTime.now())) // 상수 값으로 현재 날짜 시간을 설정
                .where(board.id.eq(id))
                .execute();
    }

    // 게시글 삭제
    public void deleteBoard(Long id) {
        queryFactory
                .delete(board)
                .where(board.id.eq(id))
                .execute();
    }

    public void changeUseYnWhenDeleted(Long id) {
        queryFactory
                .update(board)
                .set(board.useYn, 9)  // Set useYn to 9
                .set(board.modifiedDt, Expressions.constant(LocalDateTime.now()))
                .where(board.id.eq(id))
                .execute();
    }
}