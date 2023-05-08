package org.mozip.models.member;

import lombok.RequiredArgsConstructor;
import org.mozip.controllers.members.JoinParam;
import org.mozip.entities.Members;
import org.mozip.repositories.MembersRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberSaveService {
    private final MembersRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final MemberJoinValidator validator;

    public void save(JoinParam joinParam){

        validator.validate(joinParam);

        Members member = JoinParam.of(joinParam);

        String hash = passwordEncoder.encode(joinParam.getMemberPw());
        System.out.println("hash :" + hash);
        member.setMemberPw(hash);

        repository.saveAndFlush(member);
    }

    public Members getData(JoinParam param){

        return repository.findByEmail(param.getEmail());
    }
}
