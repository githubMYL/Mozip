package org.mozip.models.mypages;

import lombok.RequiredArgsConstructor;
import org.mozip.commons.validators.Validator;
import org.mozip.controllers.mypage.MypageBoardForm;
import org.mozip.repositories.BoardDataRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MypageSaveValidator implements Validator<MypageBoardForm>{

    private final BoardDataRepository repository;

    @Override
    public void check(MypageBoardForm mypageBoardForm) {
        Long id = mypageBoardForm.getId();
        String remode = mypageBoardForm.getRemode();
        String subject = mypageBoardForm.getSubject();
        String content = mypageBoardForm.getContent();

        // 게시글 수정인 경우
        if (remode != null && remode.equals("update")) {
            if (id == null) { // 게시글 번호 필수 체크
                nullCheck(id, new RuntimeException("잘못된 접근입니다."));
            }

            // 게시글 등록 여부 체크
            if (!repository.exists(id)) {
                throw new RuntimeException("등록되지 않은 모임입니다.");
            }
        }

        requiredCheck(subject, new RuntimeException("모임명을 입력하세요."));
        requiredCheck(content, new RuntimeException("모임설명란을 입력하세요."));
    }
}
