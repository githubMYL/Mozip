package org.mozip.models.file;

import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.mozip.entities.FileInfo;
import org.mozip.entities.QFileInfo;
import org.mozip.repositories.FileInfoRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.data.domain.Sort.Order.asc;

@Service
@RequiredArgsConstructor
public class FileListService {

    private final FileInfoRepository repository;
    private final FileInfoSaveService infoSaveService;

    public List<FileInfo> gets(String gid) {
        return gets(gid, null);
    }

    public List<FileInfo> gets(String gid, String location) {
        return gets(gid, location, false, true);
    }

    public List<FileInfo> getAll(String gid) {
        return getAll(gid, null);
    }

    public List<FileInfo> getAll(String gid, String location) {
        return gets(gid, location, true, false);
    }

    public List<FileInfo> gets(String gid, String location, boolean isAll, boolean success) {
        if (gid == null) {
            return null;
        }
        QFileInfo fileInfo = QFileInfo.fileInfo;
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(fileInfo.gid.eq(gid));

        if (location != null && !location.isBlank()) {
            builder.and(fileInfo.location.eq(location));
        }

        if (!isAll) { // 전체 조회가 아닌 경우 성공, 실패에 따른 목록 조회 조건
            builder.and(fileInfo.success.eq(success));
        }

        List<FileInfo> fileInfos = (List<FileInfo>)repository.findAll(builder, Sort.by(asc("regDt")));

        fileInfos = fileInfos.stream().map(o -> {
            Long fileNo = o.getFileNo();
            o.setFileURL(infoSaveService.getFileURL(fileNo));
            o.setFilePath(infoSaveService.getFilePath(fileNo));
            return o;

        }).toList();

        return fileInfos;
    }
}