package org.mozip.repositories;

import org.mozip.controllers.entities.BoardData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface BoardDataRepository  extends JpaRepository<BoardData,Long>, QuerydslPredicateExecutor {
}
