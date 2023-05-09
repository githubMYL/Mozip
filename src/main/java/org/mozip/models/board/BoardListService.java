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
        Pageable pageable = PageRequest.of(page, limit, sort2);
        Page<BoardData> data = boardDataRepository.findAll(andBuilder, pageable);

        return data;
    }
}
