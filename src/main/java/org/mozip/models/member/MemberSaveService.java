package org.mozip.models.member;

import lombok.RequiredArgsConstructor;
import org.mozip.controllers.entities.Members;
import org.mozip.controllers.members.JoinParam;
import org.mozip.repositories.MembersRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberSaveService {
    private final MembersRepository repository;
    private final PasswordEncoder passwordEncoder;

    public void save(JoinParam joinParam){
        Members member = JoinParam.of(joinParam);

        String hash = passwordEncoder.encode(joinParam.getMemberPw());
        member.setMemberPw(hash);

        repository.saveAndFlush(member);
    }
}
