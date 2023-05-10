package org.mozip.models.file;

import lombok.RequiredArgsConstructor;
import org.mozip.entities.FileInfo;
import org.mozip.repositories.FileInfoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FileUpdateSuccessService {

    private final FileInfoRepository repository;
    private final FileInfoService infoService;

    public void process(String gid) {
        List<FileInfo> files = repository.findByGidOrderByRegDtAsc(gid);

        if (files == null || files.size() == 0) {
            throw new FileNotFoundException();
        }

        files.stream().forEach(file -> file.setSuccess(true));

        repository.flush();

    }
}
