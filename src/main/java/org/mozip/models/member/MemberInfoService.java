package org.mozip.models.member;

import lombok.RequiredArgsConstructor;
import org.mozip.entities.Members;
import org.mozip.repositories.MembersRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberInfoService implements UserDetailsService {

    private final MembersRepository repository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Members member = repository.findByEmail(username);
        if(member == null) {
            throw new UsernameNotFoundException(username);
        }

        List<GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority(member.getType().toString()));
        return MemberInfo.builder()
                .memberNo(member.getMemberNo())
                .email(member.getEmail())
                .memberPw(member.getMemberPw())
                .memberNick(member.getMemberNick())
                .mobile(member.getMobile())
                .authorities(authorities)
                .build();

    }
}
