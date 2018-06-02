package com.yoogurt.kettle.service;

import com.yoogurt.kettle.beans.AuthorizedUser;

public interface UserService {

    AuthorizedUser getUserInfo(String user, String token);

    AuthorizedUser getUserInfo(String token);
}
