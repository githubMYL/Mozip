package org.mozip.repositories;


import org.mozip.entities.CateCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;


public interface CategoryRepository extends JpaRepository<CateCode, String>, QuerydslPredicateExecutor {

}
