package org.mozip.models.mypages;

import lombok.RequiredArgsConstructor;
import org.mozip.entities.BoardData;
import org.mozip.repositories.BoardDataRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MypageDeleteService {

    private final BoardDataRepository repository;

    public void delete(Long id){
        BoardData boardData = repository.findById(id).orElseThrow();
        repository.delete(boardData);
        repository.flush();
    }
}
