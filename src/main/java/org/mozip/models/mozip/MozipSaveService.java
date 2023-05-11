package org.mozip.models.mozip;

import lombok.RequiredArgsConstructor;
import org.mozip.commons.MemberUtil;
import org.mozip.controllers.mypage2.MozipForm;
import org.mozip.entities.Mozip;
import org.mozip.models.file.FileUpdateSuccessService;
import org.mozip.repositories.MozipRepository;
import org.springframework.stereotype.Service;

/**
 * 모집 저장 처리 - 추가, 수정
 * 
 */
@Service
@RequiredArgsConstructor
public class MozipSaveService {
    private final MozipRepository repository;
    private final FileUpdateSuccessService updateSucessService;
    private final MemberUtil memberUtil;

    public void save(MozipForm mozipForm) {
        Mozip mozip = null;
        Long id = mozipForm.getId();
        if (id != null) {
            mozip = repository.findById(id).orElseGet(Mozip::new);
            mozip.setId(id);
        }

        if (mozip == null) {
            mozip = new Mozip();
        }

        mozip.setGid(mozipForm.getGid());
        mozip.setTitle(mozipForm.getTitle());
        mozip.setDescription(mozipForm.getDescription());
        mozip.setMember(memberUtil.getEntity());

        mozip = repository.saveAndFlush(mozip);
        mozipForm.setId(mozip.getId());

        /** 등록 또는 수정 완료시 파일 업로드 완료 처리 */
        updateSucessService.process(mozipForm.getGid());
    }
}
