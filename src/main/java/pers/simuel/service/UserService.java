package pers.simuel.service;

import pers.simuel.entity.User;

public interface UserService {
    User checkUser(String username, String password);
}
