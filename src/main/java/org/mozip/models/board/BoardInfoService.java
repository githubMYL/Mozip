package org.mozip.models.board;

import lombok.RequiredArgsConstructor;
import org.mozip.entities.BoardData;
import org.mozip.repositories.BoardDataRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardInfoService {
    private final BoardDataRepository repository;

    public BoardData get(Long id) {
        BoardData boardData = repository.findById(id).orElseThrow(BoardDataNotExistException::new);

        return boardData;
    }
}
