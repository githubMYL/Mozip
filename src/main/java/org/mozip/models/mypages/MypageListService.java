package org.mozip.models.mypages;

import lombok.RequiredArgsConstructor;
import org.mozip.controllers.entities.BoardData;
import org.mozip.controllers.entities.Members;
import org.mozip.repositories.BoardDataRepository;
import org.mozip.repositories.MembersRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MypageListService {

    private final BoardDataRepository repository;

    public List<BoardData> mylist(){

        return repository.findAll(Sort.by(Sort.Order.desc("regDt")));
    }
}
