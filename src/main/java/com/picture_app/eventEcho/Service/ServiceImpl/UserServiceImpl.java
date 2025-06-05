package com.picture_app.eventEcho.Service.ServiceImpl;

import com.picture_app.eventEcho.Entity.User;
import com.picture_app.eventEcho.Repository.UserRepo;
import com.picture_app.eventEcho.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

    @Service
    public class UserServiceImpl implements UserService {

        @Autowired
        private UserRepo userRepository;

        @Override
        public User saveUser(User user) {
            return userRepository.save(user);
        }

        @Override
        public User getUserByUsername(String username) {
            return userRepository.findByUsername(username).orElse(null);
        }
    }
