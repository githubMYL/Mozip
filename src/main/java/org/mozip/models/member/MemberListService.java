package org.mozip.models.member;

import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.mozip.commons.MemberUtil;
import org.mozip.controllers.admin.member.MemberSearch;
import org.mozip.entities.Members;
import org.mozip.entities.QMembers;
import org.mozip.repositories.MembersRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import static org.springframework.data.domain.Sort.Order.*;

@Service
@RequiredArgsConstructor
public class MemberListService {

    private final MembersRepository membersRepository;

    private final MemberUtil memberUtil;

    public Page<Members> gets(MemberSearch search) {
        BooleanBuilder andBuilder = new BooleanBuilder();

        QMembers members = QMembers.members;


        if (memberUtil.isLogin() && !memberUtil.isAdmin()) {
            MemberInfo memberInfo = memberUtil.getMember();
            andBuilder.and(members.memberNo.eq(memberInfo.getMemberNo()));
        }

        /** 검색 조건 처리 S */

        String sopt = search.getSopt();
        String skey = search.getSkey();
        if (sopt != null && !sopt.isBlank() && skey != null && !skey.isBlank()) {

            if (sopt.equals("all")) {        // 회원검색기능 -> 이메일 + 회원명 + 닉네임
                BooleanBuilder orBuilder = new BooleanBuilder();
                orBuilder.or(members.email.contains(skey))
                        .or(members.memberNm.contains(skey))
                        .or(members.memberNick.contains(skey));
                andBuilder.and(orBuilder);

            } else if (sopt.equals("email")) {          // 이메일
                andBuilder.and(members.email.contains(skey));

            } else if (sopt.equals("memberNm")) {       // 회원명
                andBuilder.and(members.memberNm.contains(skey));

            } else if (sopt.equals("memberNick")) {     // 닉네임
                andBuilder.and(members.memberNick.contains(skey));

            } else if (sopt.equals("email_memberNm")){  //이메일 + 회원명
                BooleanBuilder orBuilder = new BooleanBuilder();

                orBuilder.or(members.memberNm.contains(skey))
                        .or(members.email.contains(skey));
                andBuilder.and(orBuilder);
            }
        }
        /** 검색 조건 처리 E */

        /** 페이징 및 정렬 처리 S */

        int page = search.getPage();
        int limit = search.getLimit();

        page = page < 1 ? 1 : page;
        limit = limit < 1 ? 20 : limit;

        String sort = search.getSort();                     // regDt DESC, regDt ASC
        Sort sort2 = Sort.by(desc("regDt"));

        if (sort != null && !sort.isBlank()) {
            String[] sorts = sort.split("\\s+");
            if (sorts[1].toUpperCase().equals("DESC")) {    // 내림차순
                sort2 = Sort.by(desc(sorts[0]));
            } else {
                sort2 = Sort.by(asc(sorts[0]));
            }
        }
        /** 페이징 및 정렬 처리 E*/

        Pageable pageable = PageRequest.of(page, limit, sort2);
        Page<Members> member = membersRepository.findAll(andBuilder, pageable);

        return member;

    }
}
