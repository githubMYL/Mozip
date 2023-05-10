package org.mozip.models.mozip;

import lombok.RequiredArgsConstructor;
import org.mozip.entities.Mozip;
import org.mozip.models.file.FileDeleteService;
import org.mozip.repositories.MozipRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MozipDeleteService {

    private final MozipRepository repository;

    private final MozipInfoService infoService;
    private final FileDeleteService deleteService;

    public void delete(Long id) {

        // 데이터 + 첨부 파일
        Mozip mozip = infoService.get(id, true);

        // 첨부 파일 삭제
        deleteService.delete(mozip.getId());

        repository.delete(mozip);
        repository.flush();

    }
}
