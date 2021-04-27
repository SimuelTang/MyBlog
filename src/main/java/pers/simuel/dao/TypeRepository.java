package pers.simuel.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import pers.simuel.entity.Type;

public interface TypeRepository extends JpaRepository<Type, Long> {

    Type findByName(String name);

}
