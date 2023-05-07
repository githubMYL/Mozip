package org.mozip.models.mozip;

import lombok.RequiredArgsConstructor;
import org.mozip.commons.MemberUtil;
import org.mozip.controllers.entities.Mozip;
import org.mozip.controllers.mypage2.MozipForm;
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
    }
}
