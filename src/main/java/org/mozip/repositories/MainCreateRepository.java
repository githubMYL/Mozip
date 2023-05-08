package org.mozip.repositories;

import org.mozip.controllers.entities.MainList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface MainCreateRepository extends JpaRepository<MainList, String>, QuerydslPredicateExecutor {

}
