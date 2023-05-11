package org.mozip.models.member;

import lombok.RequiredArgsConstructor;
import org.mozip.entities.FileInfo;
import org.mozip.entities.Members;
import org.mozip.models.file.FileInfoService;
import org.mozip.models.file.FileListService;
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
    private final FileListService fileListService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Members member = repository.findByEmail(username);
        if(member == null) {
            throw new UsernameNotFoundException(username);
        }

        List<GrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority(member.getType().toString()));

        /** 프로필 이미지 S */
        List<FileInfo> files = fileListService.gets(member.getGid());
        FileInfo profileImage = files != null && files.size() > 0 ? files.get(0) : null;
        /** 프로필 이미지 E */

        return MemberInfo.builder()
                .memberNo(member.getMemberNo())
                .email(member.getEmail())
                .memberPw(member.getMemberPw())
                .memberNick(member.getMemberNick())
                .mobile(member.getMobile())
                .profileImage(profileImage)
                .authorities(authorities)
                .build();

    }
}
