package org.mozip.models.mozip;

import org.mozip.controllers.entities.Mozip;
import org.mozip.controllers.mypage2.MozipSearch;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class MozipListService {
    public Page<Mozip> gets(MozipSearch search) {
        return null;
    }
}
