package org.mozip.models.file;

import lombok.RequiredArgsConstructor;
import org.mozip.commons.MemberUtil;
import org.mozip.entities.FileInfo;
import org.mozip.repositories.FileInfoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FileDeleteService {

    private final FileInfoService infoService;
    private final MemberUtil memberUtill;

    private final FileInfoRepository repository;

    /**
     * 파일 삭제
     * @param fileNo
     *
     * 1. 파일 정보 조회 - 없으면 예외 발생
     * 2. 파일 소유자 체크 - 관리자는 OK, 회원 - 본인이 올린 파일
     * 3. 정보 삭제 -> 실제 파일 데이터 삭제
     */
    public void delete(Long fileNo) {

        FileInfo fileInfo = infoService.get(fileNo);

        String createdBy = fileInfo.getCreatedBy();

        // 회원이 올린 파일이고 관리자가 아니고, 본인이 올린 파일이 아닌 경우
        if (createdBy != null && !memberUtill.isAdmin() && !memberUtill.isMine(createdBy)) {
            throw new FileException("errors.file.notYours", HttpStatus.UNAUTHORIZED);
        }

        File filePath = new File(fileInfo.getFilePath());

        repository.delete(fileInfo);
        repository.flush();

        /** 업로드된 실제 파일 삭제 */
        if (filePath.exists()) {
            filePath.delete();
        }

    }

    /**
     * 그룹 ID로 삭제
     *
     * @param gid
     */
    public void delete(String gid) {
        List<FileInfo> files = repository.findByGidOrderByRegDtAsc(gid);
        if (files == null || files.size() == 0) {
            return;
        }

        files.stream().forEach(f -> delete(f.getFileNo()));
    }
}
