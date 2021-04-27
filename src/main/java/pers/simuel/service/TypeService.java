package pers.simuel.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pers.simuel.entity.Type;

import java.util.List;


/**
 * @Author simuel_tang
 * @Date 2021/4/26
 * @Time 11:30
 */
public interface TypeService {
    Page<Type> listType(Pageable pageable);

    Type getType(Long id);

    Type getTypeByName(String name);

    Type saveType(Type type);

    Type updateType(Long id, Type type);

    void deleteType(Long id);

    List<Type> listType();
}
