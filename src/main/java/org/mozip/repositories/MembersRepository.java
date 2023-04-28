package org.mozip.repositories;

import org.mozip.controllers.entities.Members;
import org.mozip.controllers.entities.QMembers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface MembersRepository extends JpaRepository<Members, Long>, QuerydslPredicateExecutor {

    Members findByEmail (String email);
    /** 중복 이메일체크 */
    default boolean memberExists(String email) {
        QMembers member = QMembers.members;

        boolean exists = exists(member.email.eq(email));

        return exists;
    }
}

