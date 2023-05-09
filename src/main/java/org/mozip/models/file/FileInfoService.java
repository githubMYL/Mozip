package org.mozip.models.file;

import lombok.RequiredArgsConstructor;
import org.mozip.entities.FileInfo;
import org.mozip.repositories.FileInfoRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FileInfoService {

    private final FileInfoRepository repository;

    public FileInfo get(Long fileNo) {

        FileInfo fileInfo = repository.findById(fileNo).orElseThrow(FileNotFoundException::new);


        return fileInfo;
    }

}
