package org.mozip.repositories;

import org.mozip.controllers.category.CateCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.ArrayList;
import java.util.List;

public interface CategoryRepository extends JpaRepository<CateCode, String>, QuerydslPredicateExecutor {

}
