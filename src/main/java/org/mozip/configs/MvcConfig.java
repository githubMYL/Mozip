package org.mozip.configs;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableJpaAuditing
@RequiredArgsConstructor
public class MvcConfig implements WebMvcConfigurer {

    @Bean
<<<<<<< HEAD
    public MessageSource messageSource(){
        // 메시지관련 메서드
=======
    public MessageSource messageSource() {
>>>>>>> dc145500e999787e62475910dc791f05b19a038c
        ResourceBundleMessageSource ms = new ResourceBundleMessageSource();
        ms.setDefaultEncoding("UTF-8");
        ms.addBasenames("messages.commons", "messages.errors", "messages.validations");
        return ms;
    }
<<<<<<< HEAD
}
=======

}
>>>>>>> dc145500e999787e62475910dc791f05b19a038c
