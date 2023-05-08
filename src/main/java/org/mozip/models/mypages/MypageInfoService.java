package org.mozip.models.mypages;


import lombok.RequiredArgsConstructor;
import org.mozip.entities.BoardData;
import org.mozip.repositories.BoardDataRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MypageInfoService {

    //번호로 조회
    private final BoardDataRepository repository;

    public BoardData get(Long id){
        BoardData data = repository.findById(id).orElseThrow();
        return data;
    }

}
