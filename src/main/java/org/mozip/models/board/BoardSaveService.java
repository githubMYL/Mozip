package org.mozip.models.board;

import lombok.RequiredArgsConstructor;
import org.mozip.commons.MemberUtil;
import org.mozip.controllers.board.BoardForm;
import org.mozip.entities.BoardData;
import org.mozip.repositories.BoardDataRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardSaveService {
    private final BoardDataRepository repository;
    private final MemberUtil memberUtil;
    public void save(BoardForm board) {

        Long id = board.getId();
        BoardData boardData = null;
        if (id != null) {
            boardData = repository.findById(id).orElse(null);
            // 조회된 데이터 -> 영속상태 boardData 엔티티 -> 수정
        }

        if (boardData == null) boardData = new BoardData(); // 비 영속 상태 -> 추가

        boardData.setSubject(board.getSubject());
        boardData.setContent(board.getContent());
        boardData.setMember(memberUtil.getEntity());
        repository.saveAndFlush(boardData);
    }
}
