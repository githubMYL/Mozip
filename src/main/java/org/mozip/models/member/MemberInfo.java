package org.mozip.models.member;

import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Data
@Builder
public class MemberInfo implements UserDetails {
    private Long memberNo;
    private String email;
    private String memberPw;
    private String memberNick;
    private String memberNm;
    private String mobile;
    private Collection<GrantedAuthority> authorities; // 이넘클래스 권한

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { // 권한부분
        return authorities;
    }

    @Override
    public String getPassword() {
        return memberPw;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
