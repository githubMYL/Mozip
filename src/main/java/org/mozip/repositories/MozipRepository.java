package org.mozip.repositories;


import org.mozip.entities.Mozip;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface MozipRepository extends JpaRepository<Mozip, Long>, QuerydslPredicateExecutor<Mozip> {

}
