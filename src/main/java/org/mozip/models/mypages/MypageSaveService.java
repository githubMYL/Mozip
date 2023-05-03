package org.mozip.models.mypages;

import lombok.RequiredArgsConstructor;
import org.mozip.controllers.entities.BoardData;
import org.mozip.controllers.mypage.MypageBoardForm;
import org.mozip.repositories.BoardDataRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;

@Service
@RequiredArgsConstructor
public class MypageSaveService {
    
    //모임 저장 서비스
    
    private final BoardDataRepository repository;
    private final MypageSaveValidator saveValidator;

    public void save(MypageBoardForm mypageBoardForm){

        save(mypageBoardForm, null);
    }

    public void save(MypageBoardForm mypageBoardForm, Errors errors){
        if(errors != null && errors.hasErrors()){
            return;
        }

        saveValidator.check(mypageBoardForm);

        BoardData boardData = null;

        //수정
        String remode = mypageBoardForm.getRemode();
        Long id = mypageBoardForm.getId();
        if(remode != null && remode.equals("update") && id != null){
            boardData = repository.findById(id).orElse(null);
            boardData.setSubject(mypageBoardForm.getSubject());
            boardData.setContent(mypageBoardForm.getContent());
        }

        //추가
        if(boardData == null){
            boardData = mypageBoardForm.of(mypageBoardForm);
        }

        repository.saveAndFlush(boardData);
    }
}
