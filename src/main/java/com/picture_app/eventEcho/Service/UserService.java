package com.picture_app.eventEcho.Service;

import com.picture_app.eventEcho.Entity.User;

public interface UserService {
    User saveUser(User user);
    User getUserByUsername(String username);
}
