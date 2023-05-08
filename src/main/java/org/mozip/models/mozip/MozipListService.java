package org.mozip.models.mozip;

import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.mozip.controllers.entities.Mozip;
import org.mozip.controllers.mypage2.MozipSearch;
import org.mozip.repositories.MozipRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import static org.springframework.data.domain.Sort.Order.asc;
import static org.springframework.data.domain.Sort.Order.desc;

@Service
@RequiredArgsConstructor
public class MozipListService {

    private final MozipRepository mozipRepository;

    public Page<Mozip> gets(MozipSearch search) {
        BooleanBuilder andBuilder = new BooleanBuilder();

        /** 검색 처리 S */
        String sopt = search.getSopt();
        String skey = search.getSkey();
        if (sopt != null && !sopt.isBlank() && skey != null && !skey.isBlank()) {
            if (sopt.equals("all")) { // 통합 검색 - 제목 + 내용 + 회원이메일 + 회원명

            } else if (sopt.equals("title")) { // 제목

            } else if (sopt.equals("description")) { // 내용

            } else if (sopt.equals("title_description")) { // 제목 + 내용

            } else if (sopt.equals("email")) { // 회원 이메일
                
            } else if (sopt.equals("memberNm")) { // 회원명
                
            }

        }

        /** 검색 처리 E */

        /** 페이징 + 정렬 처리 S */
        int page = search.getPage();
        int limit = search.getLimit();
        page = page < 1 ? 1:page;
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

        Pageable pageable = PageRequest.of(page - 1, limit, sort2);

        Page<Mozip> data = mozipRepository.findAll(andBuilder, pageable);
        /** 페이징 + 정렬 처리 E */

        return data;
    }
}
