package org.mozip.models.mypages;

import lombok.RequiredArgsConstructor;
import org.mozip.controllers.mypage.MypageBoardForm;
import org.mozip.repositories.BoardDataRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MypageSaveValidator {

    private final BoardDataRepository repository;

    public void check(MypageBoardForm mypageBoardForm){
        Long id = mypageBoardForm.getId();
        String remode = mypageBoardForm.getRemode();
        String subject = mypageBoardForm.getSubject();
        String content = mypageBoardForm.getContent();

        if(remode != null && remode.equals("update")){
            if(id == null){
                throw new RuntimeException("잘못된 접근방식 입니다.");
            }

            if(!repository.exists(id)){
                throw new RuntimeException("등록되지 않은 게시글 입니다.");
            }
        }

    }
}
