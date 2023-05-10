package org.mozip.models.board;

import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.mozip.commons.MemberUtil;
import org.mozip.controllers.board.BoardSearch;
import org.mozip.entities.BoardData;
import org.mozip.entities.QBoardData;
import org.mozip.models.member.MemberInfo;
import org.mozip.repositories.BoardDataRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import static org.springframework.data.domain.Sort.Order.asc;
import static org.springframework.data.domain.Sort.Order.desc;

@Service
@RequiredArgsConstructor
public class BoardListService {
    private final BoardDataRepository boardDataRepository;
    private final MemberUtil memberUtil;

    public Page<BoardData> gets(BoardSearch search) {
        BooleanBuilder andBuilder = new BooleanBuilder();

        QBoardData boardData = QBoardData.boardData;

        if (memberUtil.isLogin() && !memberUtil.isAdmin()) {
            MemberInfo memberInfo = memberUtil.getMember();
            andBuilder.and(boardData.member.memberNo.eq(memberInfo.getMemberNo()));
        }

        /** 검색 조건 처리 S */
        String sopt = search.getSopt();
        String skey = search.getSkey();
        if (sopt != null && !sopt.isBlank() && skey != null && !skey.isBlank()) {
            if (sopt.equals("all")) { // 통합 검색 - 제목 + 내용 + 회원이메일 + 회원명
                BooleanBuilder orBuilder = new BooleanBuilder();
                orBuilder.or(boardData.subject.contains(skey))
                        .or(boardData.content.contains(skey))
                        .or(boardData.member.email.contains(skey))
                        .or(boardData.member.memberNm.contains(skey));
                andBuilder.and(orBuilder);

            } else if (sopt.equals("subject")) { // 제목
                andBuilder.and(boardData.subject.contains(skey));

            } else if (sopt.equals("content")) { // 내용
                andBuilder.and(boardData.content.contains(skey));

            } else if (sopt.equals("subject_content")) { // 제목 + 내용
                BooleanBuilder orBuilder = new BooleanBuilder();
                orBuilder.or(boardData.subject.contains(skey))
                        .or(boardData.content.contains(skey));
                andBuilder.and(orBuilder);

            } else if (sopt.equals("email")) { // 회원 이메일
                andBuilder.and(boardData.member.email.eq(skey));

            } else if (sopt.equals("member")) { // 회원명 + 이메일
                BooleanBuilder orBuilder = new BooleanBuilder();
                orBuilder.or(boardData.member.email.contains(skey))
                        .or(boardData.member.memberNm.contains(skey));
                andBuilder.and(orBuilder);
            }
        }
        /** 검색 조건 처리 E */

        /** 페이징 및 정렬 처리 S */
        int page = search.getPage();
        int limit = search.getLimit();
        page = page < 1 ? 1 : page;
        limit = limit < 1 ? 20 : limit;

        String sort = search.getSort(); // regDt DESC, regDt ASC
        Sort sort2 = Sort.by(desc("regDt"));
        if (sort != null && !sort.isBlank()) {
            String[] sorts = sort.split("\\s+");
            if (sorts[1].toUpperCase().equals("DESC")) { // 내림차순
                sort2 = Sort.by(desc(sorts[0]));
            } else { // 오름차순
                sort2 = Sort.by(asc(sorts[0]));
            }
        }
        /** 페이징 및 정렬 처리 E */

        Pageable pageable = PageRequest.of(page, limit, sort2);
        Page<BoardData> data = boardDataRepository.findAll(andBuilder, pageable);

        return data;
    }
}
