package org.mozip.repositories;

import org.mozip.entities.BoardData;
import org.mozip.controllers.entities.QBoardData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface BoardDataRepository extends JpaRepository<BoardData,Long>, QuerydslPredicateExecutor {

    //레포지토리 -> 서비스에서 이용
    default boolean exists(Long id) {
        //등록 여부 체크
        
        QBoardData boardData = QBoardData.boardData;
        return exists(boardData.Id.eq(id));
    }
}
