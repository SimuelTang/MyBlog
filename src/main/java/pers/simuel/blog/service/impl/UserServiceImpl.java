package pers.simuel.blog.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pers.simuel.blog.dao.UserRepository;
import pers.simuel.blog.entity.User;
import pers.simuel.blog.service.UserService;
import pers.simuel.blog.utils.MD5Util;

/**
 * @Author simuel_tang
 * @Date 2021/4/26
 * @Time 10:59
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User checkUser(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, MD5Util.encrypt(password));
    }
}
