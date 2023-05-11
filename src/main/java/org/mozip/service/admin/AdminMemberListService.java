package org.mozip.service.admin;

import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.mozip.commons.MemberUtil;
import org.mozip.controllers.admin.member.MemberSearch;
import org.mozip.entities.Members;
import org.mozip.entities.QMembers;
import org.mozip.models.member.MemberInfo;
import org.mozip.repositories.MembersRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import static org.springframework.data.domain.Sort.Order.*;

@Service
@RequiredArgsConstructor
public class AdminMemberListService {

    private final MemberUtil memberUtil;

    private final MembersRepository repository;

    public Page<Members> gets(MemberSearch search) { // 모든 목록
        return gets(search, false);
    }
    public Page<Members> gets(MemberSearch search, boolean isAdmin) {
        BooleanBuilder andBuilder = new BooleanBuilder();
        QMembers members = QMembers.members;

        if (isAdmin) {
            if (!memberUtil.isLogin()) {    // 미로그인 상태
                return null;
            }
            // 목록을 로그인 한 회원으로 한정
            MemberInfo memberInfo = memberUtil.getMember();
            andBuilder.and(members.memberNo.eq(memberInfo.getMemberNo()));
        }

        /** 검색 처리 S */
        String sopt = search.getSopt();
        String skey = search.getSkey();

        if (sopt != null && !sopt.isBlank() && skey != null && !skey.isBlank()){
            if (sopt.equals("all")) {
                BooleanBuilder orBuilder = new BooleanBuilder();

                orBuilder.or(members.email.contains(skey))
                        .or(members.memberNm.contains(skey))
                        .or(members.memberNick.contains(skey));
                andBuilder.and(orBuilder);

            } else if (sopt.equals("email")) {      // 이메일
                andBuilder.and(members.email.contains(skey));

            } else if (sopt.equals("memberNm")) {   // 회원 이름
                andBuilder.and(members.memberNm.contains(skey));

            } else if (sopt.equals("memberNick")) { // 회원 닉네임
                andBuilder.and(members.memberNick.contains(skey));

            } else if (sopt.equals("member")) {     // 회원 이름 + 이메일
                BooleanBuilder orBuilder = new BooleanBuilder();
                orBuilder.or(members.email.contains(skey))
                        .or(members.memberNm.contains(skey));
                andBuilder.and(orBuilder);
            }
        }
        /** 검색 처리 E */

        /** 페이징 + 정렬처리 S */
        int page = search.getPage();
        int limit = search.getLimit();

        page = page < 1 ? 1 : page;
        limit = limit <1 ? 20 : limit;

        String sort1 = search.getSort(); // regDt DESC, regDt ASC
        Sort sort2 = Sort.by(desc("regDt"));
        if (sort1 != null && !sort1.isBlank()) {
            String[] sorts = sort1.split("\\s+");
            if (sorts[1].toUpperCase().equals("DESC")) {    // 내림차순
                sort2 = Sort.by(desc(sorts[0]));
            } else {                                        // 오름차순
                sort2 = Sort.by(asc(sorts[0]));
            }
        }

        Pageable pageable = PageRequest.of(page -1, limit, sort2);

        Page<Members> data = repository.findAll(andBuilder, pageable);
        /** 페이징 + 정렬처리 E */

        return data;
    }

}
