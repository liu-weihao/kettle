package com.yoogurt.kettle.service.impl;

import com.yoogurt.kettle.beans.AuthorizedUser;
import com.yoogurt.kettle.repository.AuthorizedUserRepository;
import com.yoogurt.kettle.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private AuthorizedUserRepository repository;

    @Override
    public AuthorizedUser getUserInfo(String user, String token) {
        AuthorizedUser authorizedUser = new AuthorizedUser();
        authorizedUser.setUser(user);
        authorizedUser.setToken(token);
        Optional<AuthorizedUser> optional = repository.findOne(Example.of(authorizedUser));
        return optional.orElse(null);
    }

    @Override
    public AuthorizedUser getUserInfo(String token) {
        AuthorizedUser authorizedUser = new AuthorizedUser();
        authorizedUser.setToken(token);
        Optional<AuthorizedUser> optional = repository.findOne(Example.of(authorizedUser));
        return optional.orElse(null);
    }
}
