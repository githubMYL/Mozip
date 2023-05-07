package org.mozip.models.mozip;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.mozip.controllers.entities.Mozip;
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


    public Mozip get(Long id) {

        Mozip mozip = repository.findById(id).orElse(null);
        if (mozip == null) {
            throw new MozipValidationException("BadRequest");
        }

        em.detach(mozip);
        return mozip;
    }
}
