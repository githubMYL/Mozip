package org.mozip.configs;

import lombok.RequiredArgsConstructor;
import org.mozip.commons.MemberUtil;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AuditorAwareImpl implements AuditorAware<String> {

    private final MemberUtil memberUtil;

    @Override
    public Optional<String> getCurrentAuditor() {

        String email = null;
        if (memberUtil.isLogin()) {
            email = memberUtil.getMember().getEmail();
        }

        return Optional.ofNullable(email);
    }
}
