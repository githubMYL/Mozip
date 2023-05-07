package org.mozip.models.mozip;

import lombok.RequiredArgsConstructor;
import org.mozip.controllers.entities.Mozip;
import org.mozip.controllers.mypage2.MozipSearch;
import org.mozip.repositories.MozipRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MozipListService {

    private final MozipRepository mozipRepository;

    public Page<Mozip> gets(MozipSearch search) {
        

        return null;
    }
}
