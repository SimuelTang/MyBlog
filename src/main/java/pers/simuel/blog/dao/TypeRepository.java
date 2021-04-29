package pers.simuel.blog.dao;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pers.simuel.blog.entity.Type;

import java.util.List;

public interface TypeRepository extends JpaRepository<Type, Long> {

    Type findByName(String name);

    @Query("select t from Type t")
    List<Type> findTop(PageRequest pageRule);
}
