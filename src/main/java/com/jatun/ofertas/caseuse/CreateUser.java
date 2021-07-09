package com.jatun.ofertas.caseuse;

import com.jatun.ofertas.entity.User;
import com.jatun.ofertas.service.UserService;
import org.springframework.stereotype.Component;

@Component
public class CreateUser {
    private UserService userService;

    public CreateUser(UserService userService) {
        this.userService = userService;
    }

    public User save(User newUser) {
        return userService.save(newUser);
    }
}
