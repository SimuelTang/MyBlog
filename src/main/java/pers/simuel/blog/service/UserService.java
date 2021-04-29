package pers.simuel.blog.service;

import pers.simuel.blog.entity.User;

public interface UserService {
    User checkUser(String username, String password);
}
