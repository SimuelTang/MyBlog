package pers.simuel.blog.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pers.simuel.blog.entity.User;

/**
 * @Author simuel_tang
 * @Date 2021/4/26
 * @Time 10:57
 */
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsernameAndPassword(String username, String password);
}
