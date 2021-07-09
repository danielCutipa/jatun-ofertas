package com.jatun.ofertas.caseuse;

import com.jatun.ofertas.entity.User;
import com.jatun.ofertas.service.UserService;

import java.util.List;

public class GetUserImplement implements GetUser {
    private UserService userService;

    public GetUserImplement(UserService userService) {
        this.userService = userService;
    }

    @Override
    public List<User> getAll() {
        return userService.getAllUsers();
    }
}
