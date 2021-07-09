package com.jatun.ofertas.configuration;

import com.jatun.ofertas.caseuse.GetUser;
import com.jatun.ofertas.caseuse.GetUserImplement;
import com.jatun.ofertas.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CaseUseConfiguration {

    @Bean
    GetUser getUser(UserService userService) {
        return new GetUserImplement(userService);
    }
}
