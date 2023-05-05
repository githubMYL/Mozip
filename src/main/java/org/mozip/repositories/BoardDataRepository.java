package org.mozip.repositories;

import org.mozip.controllers.entities.BoardData;
import org.mozip.controllers.entities.QBoardData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface BoardDataRepository  extends JpaRepository<BoardData,Long>, QuerydslPredicateExecutor {

    default boolean exists(Long id) {
        QBoardData boardData = QBoardData.boardData;
        return exists(boardData.Id.eq(id));
    }
}
