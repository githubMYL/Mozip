package org.mozip.models.mozip;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.mozip.commons.MemberUtil;
import org.mozip.controllers.entities.Mozip;
import org.mozip.models.member.MemberInfo;
import org.mozip.repositories.MozipRepository;
import org.springframework.stereotype.Service;

/**
 * 모집 정보 조회
 *
 */
@Service
@RequiredArgsConstructor
public class MozipInfoService {
    private final MozipRepository repository;
    private final EntityManager em;
    private final MemberUtil memberUtil;

    public Mozip get(Long id) {
        return get(id, false);
    }

    public Mozip get(Long id, boolean isMine) {

        Mozip mozip = repository.findById(id).orElse(null);
        if (mozip == null) {
            throw new MozipValidationException("BadRequest");
        }

        // 본인이 생성한 모임인지 체크
        if (isMine) {
            MemberInfo memberInfo = memberUtil.getMember();
            if (!memberUtil.isLogin() || memberInfo.getMemberNo() != mozip.getMember().getMemberNo()) {
                throw new MozipValidationException("mozip.notYours");
            }
        }

        em.detach(mozip);
        return mozip;
    }
}
