package org.mozip.models.mozip;

import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.mozip.commons.MemberUtil;
import org.mozip.controllers.mypage2.MozipSearch;
import org.mozip.entities.FileInfo;
import org.mozip.entities.Mozip;
import org.mozip.entities.QMozip;
import org.mozip.models.file.FileInfoService;
import org.mozip.models.file.FileListService;
import org.mozip.models.member.MemberInfo;
import org.mozip.repositories.MozipRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.data.domain.Sort.Order.asc;
import static org.springframework.data.domain.Sort.Order.desc;

@Service
@RequiredArgsConstructor
public class MozipListService {

    private final MemberUtil memberUtil;

    private final MozipRepository mozipRepository;
    private final FileListService listService;


    public Page<Mozip> gets(MozipSearch search) { // 모든 목록
        return gets(search, false);
    }

    public Page<Mozip> gets(MozipSearch search, boolean isMine) { // 로그인한 회원에 한정

        BooleanBuilder andBuilder = new BooleanBuilder();
        QMozip mozip = QMozip.mozip;

        if (isMine) {
            if (!memberUtil.isLogin()) { // 미로그인 상태
                return null;
            }
            // 목록을 로그인한 회원으로 한정 
            MemberInfo memberInfo = memberUtil.getMember();
            andBuilder.and(mozip.member.memberNo.eq(memberInfo.getMemberNo()));
        }


        /** 검색 처리 S */
        String sopt = search.getSopt();
        String skey = search.getSkey();
        if (sopt != null && !sopt.isBlank() && skey != null && !skey.isBlank()) {
            if (sopt.equals("all")) { // 통합 검색 - 제목 + 내용 + 회원이메일 + 회원명
                BooleanBuilder orBuilder = new BooleanBuilder();
                orBuilder.or(mozip.title.contains(skey))
                        .or(mozip.description.contains(skey))
                        .or(mozip.member.email.contains(skey))
                        .or(mozip.member.memberNm.contains(skey));
                andBuilder.and(orBuilder);

            } else if (sopt.equals("title")) { // 제목
                andBuilder.and(mozip.title.contains(skey));

            } else if (sopt.equals("description")) { // 내용
                andBuilder.and(mozip.description.contains(skey));

            } else if (sopt.equals("title_description")) { // 제목 + 내용
                BooleanBuilder orBuilder = new BooleanBuilder();
                orBuilder.or(mozip.title.contains(skey))
                        .or(mozip.description.contains(skey));
                andBuilder.and(orBuilder);

            } else if (sopt.equals("email")) { // 회원 이메일
                andBuilder.and(mozip.member.email.eq(skey));

            } else if (sopt.equals("member")) { // 회원명 + 이메일
                BooleanBuilder orBuilder = new BooleanBuilder();
                orBuilder.or(mozip.member.email.contains(skey))
                        .or(mozip.member.memberNm.contains(skey));
                andBuilder.and(orBuilder);
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
        List<Mozip> items = data.getContent();
        items.stream().peek(this::insertPhotos)
                .forEach(this::stripTag);
        /** 페이징 + 정렬 처리 E */

        return data;
    }

    private void insertPhotos(Mozip mozip) {
        String gid = mozip.getGid();
        List<FileInfo> mainPhotos = listService.gets(gid, "main_photo");
        List<FileInfo> editorPhotos = listService.gets(gid, "editor");

        mozip.setMainPhotos(mainPhotos);
        mozip.setEditorPhotos(editorPhotos);
    }

    public void stripTag(Mozip mozip) {
        String pattern = "<[^>]*>?";

        String description = mozip.getDescription();
        description = description.replaceAll(pattern, "").trim();

        mozip.setDescription(description);

    }

}
